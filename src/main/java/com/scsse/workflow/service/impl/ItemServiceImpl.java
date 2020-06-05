package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.dto.ItemDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Item;
import com.scsse.workflow.repository.ItemRepository;
import com.scsse.workflow.service.ItemService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ModelMapper modelMapper;
    private final DtoTransferHelper dtoTransferHelper;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ModelMapper modelMapper,DtoTransferHelper dtoTransferHelper,
                           ItemRepository itemRepository) {
        this.modelMapper = modelMapper;
        this.dtoTransferHelper = dtoTransferHelper;
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(Item item) {
        return  itemRepository.save(item);
    }

    @Override
    public ItemDto setAccount(Item item, Account account) {
        Integer id = item.getId();
        Item oldItem = itemRepository.findOne(id);
        item.setAccount(account);
        modelMapper.map(item,oldItem);
        return dtoTransferHelper.transferToItemDto(itemRepository.save(oldItem));
    }

}
