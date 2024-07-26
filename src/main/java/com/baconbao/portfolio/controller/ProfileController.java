package com.baconbao.portfolio.controller;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.services.service.ProfileService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/info")
    public ResponseEntity<ProfileDTO> findProfile(@RequestBody ProfileDTO id){
        return ResponseEntity.ok(profileService.findById(id.getId()));
    }

    @PostMapping("/searchbyname")
    public ResponseEntity<ProfileDTO> findByName(@RequestBody ProfileDTO name){
        return ResponseEntity.ok(profileService.findByName(name.getName()));
    }
}
