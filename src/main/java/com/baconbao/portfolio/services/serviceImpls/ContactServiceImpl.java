package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ContactDTO;
import com.baconbao.portfolio.model.Contact;
import com.baconbao.portfolio.repository.ContactRepository;
import com.baconbao.portfolio.services.service.ContactService;
import com.baconbao.portfolio.services.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ContactServiceImpl implements ContactService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ProfileService profileService;
    @Override
    public ContactDTO saveContact(ContactDTO contactDTO) {
        Contact contact=save(contactDTO);
        profileService.updateContactByProfile(contact,contactDTO.getProfileId());
        return convertToDTO(contact);
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {

        return convertToDTO(contactRepository.save(convertToModel(contactDTO)));
    }
    private Contact save(ContactDTO contactDTO){
        Contact contact=Contact.builder()
                .id(getGenerationId())
                .email(contactDTO.getEmail())
                .phone(contactDTO.getPhone())
                .address(contactDTO.getAddress())
                .build();
        return contactRepository.save(contact);
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
    public ContactDTO convertToDTO(Contact contact) {
        return modelMapper.map(contact, ContactDTO.class);
    }

    public Contact convertToModel(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }

}
