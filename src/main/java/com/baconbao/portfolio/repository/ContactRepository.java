package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Contact;
import com.baconbao.portfolio.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
       Contact findByProfile(Profile profile);
}
