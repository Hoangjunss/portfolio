package com.baconbao.portfolio.services.service;


import com.baconbao.portfolio.dto.ContactDTO;
import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Profile;

public interface ContactService {
    ContactDTO saveContact(ContactDTO contactDTO);
    ContactDTO updateContact(ContactDTO contactDTO);
    ContactDTO getContactByProfile(Integer id);
}
