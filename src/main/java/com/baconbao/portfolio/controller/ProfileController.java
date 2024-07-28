package com.baconbao.portfolio.controller;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.TypeProfile;
import com.baconbao.portfolio.services.service.ProfileService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/info")
    public ResponseEntity<ProfileDTO> findProfile(@RequestBody ProfileDTO id){
        return ResponseEntity.ok(profileService.findById(id.getId()));
    }



    @GetMapping("/findProfileByType")
    public ResponseEntity<List<ProfileDTO>> findProfilesByType(@RequestParam String typeProfile){
        return ResponseEntity.ok(profileService.findProfilesByType(TypeProfile.valueOf(typeProfile)));
    }
    @PostMapping("/save")
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(profileService.saveProfile(profileDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(profileService.updateProfile(profileDTO));
    }
    @GetMapping("/findProfileByName")
    public ResponseEntity<ProfileDTO> findProfileByName(@RequestParam String name){
        return ResponseEntity.ok(profileService.findProfileByName(name));
    }
    @GetMapping("/findProfileByUser")
    public ResponseEntity<ProfileDTO> findProfileByName(@RequestParam Integer id){
        return ResponseEntity.ok(profileService.getProfileByUser(id));
    }

}
