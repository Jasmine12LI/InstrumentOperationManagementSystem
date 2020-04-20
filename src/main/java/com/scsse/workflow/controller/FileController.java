package com.scsse.workflow.controller;


import com.scsse.workflow.entity.Data;
import com.scsse.workflow.entity.HandleBigDecimal;
import com.scsse.workflow.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {
    private int length=30;
    private static final Logger log= LoggerFactory.getLogger(FileController.class);
    private static final  HandleBigDecimal handleBigDecimal = new HandleBigDecimal();
    private List<Data> sampleList = new ArrayList<Data>(); //奇数行
    private List<Data> sampleList2 = new ArrayList<Data>(); //偶数行
    private boolean isTwoFile=false;
    private Integer firstLine=1;
    private String fileName="";//原始文件路径包含其文件名
    private String path="";
    private String trueName ="";
    private String encoding;

    public void qingkong(){
        sampleList = new ArrayList<Data>();
         firstLine=1;
          fileName="";//原始文件路径包含其文件名
          path="";
         trueName ="";
        encoding="";
        isTwoFile=false;
        sampleList2=new ArrayList<Data>();
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
       if(file.isEmpty())
           return "file is empty";
         fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        trueName = fileName.substring(fileName.lastIndexOf("\\")+1,fileName.lastIndexOf("."));
        path=fileName.substring(0,fileName.lastIndexOf("\\")+1);
        System.out.println(path);


        //读取文件
        FileReader fileReader = new FileReader(fileName);
         encoding = handleBigDecimal.codeString(fileName); //获取编码方式
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encoding));
        String line = bufferedReader.readLine();
        while(line !=null)
        {
            System.out.println(line);
            String ss[]=line.split("\\s+");
            if(ss.length>=2&&ss[0].contains("ID"))
            {
                line = bufferedReader.readLine();
                break;
            }
            line = bufferedReader.readLine();
            firstLine++;
        }
        System.out.println("样品第一行为："+firstLine);

        while(line !=null)
        {
            String ss[]=line.split("\\s+");
            if(ss.length>=2&& !ss[0].equals("未命名")&& !ss[0].toLowerCase().equals("wash"))
            {
                Double doubleString = Double.parseDouble(ss[1]);
                Data sample = new Data(ss[0],doubleString);
                sampleList.add(sample);
            }
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        fileReader.close();
        beSingle();
        if(isTwoFile){

            createBeSingleFile(trueName+"-1",sampleList);
           Double slope = handleBigDecimal.xishu(sampleList);
            System.out.println("系数为："+slope);
            afterHandle(trueName+"-1",sampleList,slope);

            createBeSingleFile(trueName+"-2",sampleList2);
            Double slope2 = handleBigDecimal.xishu(sampleList2);
            System.out.println("系数为："+slope2);
            afterHandle(trueName+"-2",sampleList2,slope2);
        }
        else{
            createBeSingleFile(trueName,sampleList);
            Double slope = handleBigDecimal.xishu(sampleList);
            System.out.println("系数为："+slope);
            afterHandle(trueName,sampleList,slope);
        }

        qingkong();
        return "Success!";
    }


//去重
    public void beSingle(){
        List<Data> list2 = new ArrayList<Data>();
        for(Data in : sampleList){
            if(!list2.contains(in))
                list2.add(in);
        }
       sampleList = list2;
        System.out.println(sampleList);
        if(sampleList.get(0).getId().equals(sampleList.get(1).getId()) && sampleList.get(0).getMeanMrea()!=sampleList.get(1).getMeanMrea()){
            isTwoFile = true;
            List<Data> list0 = new ArrayList<Data>();
            List<Data> list1 = new ArrayList<Data>();
            for(int i=0;i<sampleList.size();i++){
                if(i%2==0)
                    list0.add(sampleList.get(i));
                else
                    list1.add(sampleList.get(i));
            }
            sampleList = list0;
            sampleList2 = list1;
        }

    }

    //看是否有两组数据
    public void checkIsTwoFile(){

    }

    public void createBeSingleFile(String name,List<Data> dataList) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encoding));
        File singleFile = new File(path+name+"-去重"+".txt");
        String line;
        FileWriter writer = null;
        try{
            if(!singleFile.exists()){
                singleFile.createNewFile();
            }
            writer = new FileWriter(singleFile,false);
            for(int i=1;i<=firstLine;i++){
                line = bufferedReader.readLine();
                writer.append(line);
                writer.append(System.getProperty("line.separator"));
            }
            for(Data s : dataList){

                writer.append(String.format("%-20s",s.getId())+s.getMeanMrea().toString());
                writer.append(System.getProperty("line.separator"));
            }
            writer.flush();


        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(null != writer)
                writer.close();
        }

    }

    //输出处理后的文件
    public void afterHandle(String name,List<Data> dataList,Double slope1) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encoding));
        File singleFile = new File(path+name+"-解析后"+".txt");
        String line;
        FileWriter writer = null;
        try{
            if(!singleFile.exists()){
                singleFile.createNewFile();
            }
            writer = new FileWriter(singleFile,false);
            for(int i=1;i<firstLine;i++){
                line = bufferedReader.readLine();
                writer.append(line);
                writer.append(System.getProperty("line.separator"));
            }

            writer.append(handleBigDecimal.appentStr4Length("样品ID",length)
                          +handleBigDecimal.appentStr4Length("平均面积",length)
                          +handleBigDecimal.appentStr4Length("斜率",length)
                          +handleBigDecimal.appentStr4Length("浓度",length));
            writer.append(System.getProperty("line.separator"));

            for(Data s : dataList){
                Double concent = s.getMeanMrea()/slope1;
                concent=(double) Math.round(concent*1000) / 1000;

                writer.append(handleBigDecimal.appentStr4Length(s.getId(),length)
                +handleBigDecimal.appentStr4Length(s.getMeanMrea().toString(),length)
                +handleBigDecimal.appentStr4Length(slope1.toString(),length)
                +handleBigDecimal.appentStr4Length(concent.toString(),length));
                writer.append(System.getProperty("line.separator"));
            }
            writer.flush();


        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(null != writer)
                writer.close();
        }

    }



}
