package com.baconbao.portfolio.services.service;


import com.baconbao.portfolio.dto.ContactDTO;

public interface ContactService {
    ContactDTO saveContact(ContactDTO contactDTO);
    ContactDTO updateContact(ContactDTO contactDTO);
}
