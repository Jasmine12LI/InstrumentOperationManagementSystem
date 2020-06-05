package com.scsse.workflow.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.scsse.workflow.entity.dto.AccountDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Device;
import com.scsse.workflow.entity.model.Item;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.AccountRepository;
import com.scsse.workflow.repository.DeviceRepository;
import com.scsse.workflow.repository.ItemRepository;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.AccountService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import org.apache.shiro.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

private final ModelMapper modelMapper;
private final DtoTransferHelper dtoTransferHelper;
private final AccountRepository accountRepository;
private final DeviceRepository deviceRepository;
private final UserRepository  userRepository;
private final ItemRepository itemRepository;

@Autowired
public AccountServiceImpl(ModelMapper modelMapper,DtoTransferHelper dtoTransferHelper,
                          AccountRepository accountRepository,DeviceRepository deviceRepository,
                          UserRepository userRepository,ItemRepository itemRepository){
    this.modelMapper = modelMapper;
    this.dtoTransferHelper =  dtoTransferHelper;
    this.accountRepository = accountRepository;
    this.deviceRepository = deviceRepository;
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
}
    @Override
    public AccountDto getOneAccount(Integer id) {
        return dtoTransferHelper.transferToAccountDto(accountRepository.findOne(id));
    }

    @Override
    public List<AccountDto> findAllAccount() {
        return dtoTransferHelper.transferToListDto(accountRepository.findAll(),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public AccountDto createAccount(Account account) {
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto updateAccount(Account account) {
        Integer accountId = account.getId();
        Account oldAccount = accountRepository.findOne(accountId);
        modelMapper.map(account,oldAccount);
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(oldAccount));
    }

    @Override
    public Boolean deleteAccount(Integer id) {
        Account oldAccount = accountRepository.findOne(id);
        if(oldAccount.getStatus()!=1)
        {
            for(Item item : oldAccount.getItems()){
                item.setAccount(null);
                itemRepository.delete(item);
            }
            accountRepository.deleteAccountById(id);
            return true;
        }


        return false;
    }

    @Override
    public List<AccountDto> findAccountByDeviceId(Integer deviceId) {
        return dtoTransferHelper.transferToListDto(accountRepository.findAccountByDeviceId(deviceId),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public List<AccountDto> findAccountByCheckUser(Integer checkUserId) {
        User user= userRepository.findOne(checkUserId);
        Set<Account> accounts=  user.getCheckAccounts();
        return dtoTransferHelper.transferToListDto(accounts,
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }
//查找登录用户已提交的账单
    @Override
    public List<AccountDto> findAccountBySubmit(Integer status) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println("查找登录人提交的账单");
        List<Account> accounts= accountRepository.findAccountsByStatus(status);
        System.out.println(accounts);
        Set<Account> newAccount=new HashSet<>();
        for(Account account:accounts){
            if(account.getSubmitUser().getId() ==user.getId())
                 newAccount.add(account);
        }
        return dtoTransferHelper.transferToListDto(newAccount,
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public List<AccountDto> findAccountByStatus(Integer status) {
        return dtoTransferHelper.transferToListDto(accountRepository.findAccountsByStatus(status),
                eachItem -> dtoTransferHelper.transferToAccountDto((Account) eachItem));
    }

    @Override
    public AccountDto checkAccountAndPass(Integer accountId,String des) {
        Account oldAccount = accountRepository.findOne(accountId);
        Device device= oldAccount.getDevice();
        Double price = oldAccount.getPrice(),oldExpense = device.getExpense();

            oldExpense += price;
            device.setExpense(oldExpense);

         deviceRepository.save(device);
        oldAccount.setStatus(1);
        oldAccount.setCheckTime(new Date());
        oldAccount.setDes(des);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        oldAccount.setCheckUser(user);
        user.getCheckAccounts().add(oldAccount);
        userRepository.save(user);
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(oldAccount));
    }

    @Override
    public AccountDto checkAccountAndNoPass(Integer accountId,String des) {
        Account oldAccount = accountRepository.findOne(accountId);
        oldAccount.setStatus(2);
        oldAccount.setCheckTime(new Date());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        oldAccount.setCheckUser(user);
        oldAccount.setDes(des);
        user.getCheckAccounts().add(oldAccount);
        userRepository.save(user);
        return dtoTransferHelper.transferToAccountDto(accountRepository.save(oldAccount));
    }

    @Override
    public File pringFile(Integer accountId) throws IOException {
        Account account = accountRepository.findOne(accountId);

        //模板路径
        String templatePath="src/main/resources/static/账单模板.pdf";

        //生成新文件路径
        Date date = new Date();
        String newPath="D:\\file\\账单"+date.getTime()+".pdf";
        System.out.println(newPath);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        System.out.println(dateString);

        File file = new File(newPath);
        if (!file.exists()) {
            file.getParentFile().mkdir();
            try{
                file.createNewFile();
            }catch (IOException e){
                System.out.println("创建新文件时出现了错误。。。");
                e.printStackTrace();

            }
        }



        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try{
            out = new FileOutputStream(newPath);
            reader = new PdfReader(templatePath);
            bos= new ByteArrayOutputStream();
            stamper = new PdfStamper(reader,bos);
            AcroFields form = stamper.getAcroFields();
            //设置显示中文
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            Font font = new Font(bf,15);
            fontList.add(bf);
            form.setSubstitutionFonts(fontList);
            form.setField("fill_25",account.getSubmitUser().getName());
            form.setField("fill_26",account.getSubmitUser().getPhone());
            form.setField("fill_27",account.getDevice().getName());
            form.setField("fill_29",account.getCheckUser().getName());
            form.setField("fill_30",account.getCheckUser().getPhone());
            form.setField("fill_31",account.getSummary());
            form.setField("fill_32",account.getPrice().toString());
            form.setField("fill_28","1");
            form.setField("time",dateString);
            Set<Item> items = account.getItems();
            Integer index=1;
            for(Item i :items){
                form.setField("fill_"+index,i.getName());
                index++;
                form.setField("fill_"+index,i.getPrice().toString());
                index++;
                form.setField("fill_"+index,i.getDes());
                index++;
            }
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc,out);
            doc.open();
            PdfImportedPage importedPage = copy.getImportedPage(new PdfReader(bos.toByteArray()),1);
            copy.addPage(importedPage);
            doc.close();
            System.out.println("成功");
            return new File(newPath);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            return null;
        }
    }
}
