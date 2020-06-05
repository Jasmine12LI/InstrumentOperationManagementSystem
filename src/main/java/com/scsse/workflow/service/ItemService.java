package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.ItemDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Item;

import javax.transaction.Transactional;

public interface ItemService {

    @Transactional
    Item createItem(Item item);
    ItemDto setAccount(Item item,Account account);
}
