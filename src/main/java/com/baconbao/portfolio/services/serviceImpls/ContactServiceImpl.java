package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ContactDTO;
import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.model.Contact;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.repository.ContactRepository;
import com.baconbao.portfolio.services.service.ContactService;
import com.baconbao.portfolio.services.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ProfileService profileService;
    @Override
    public ContactDTO saveContact(ContactDTO contactDTO) {
        try{
            log.info("Save contact");
            Contact contact = save(contactDTO);
            profileService.updateContactByProfile(contact,contactDTO.getProfileId());
            return convertToDTO(contact);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.CONTACT_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {
        try{
            log.info("Updating contact");
            return convertToDTO(contactRepository.save(convertToModel(contactDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.CONTACT_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ContactDTO getContactByProfile(Integer id) {
        try {
            log.info("Getting contact by profile id: {}", id);
            Profile profile=profileService.convertToModel(profileService.findById(id));
            return convertToDTO(contactRepository.findByProfile(profile));
        } catch (InvalidDataAccessResourceUsageException e){
            log.error("Getting contact by profile id failed: {}", e.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }

    private Contact save(ContactDTO contactDTO){
        try {
            log.info("Saving contact");
            Contact contact=Contact.builder()
                    .id(getGenerationId())
                    .email(contactDTO.getEmail())
                    .phone(contactDTO.getPhone())
                    .address(contactDTO.getAddress())
                    .build();
            return contactRepository.save(contact);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.CONTACT_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
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
