package com.baconbao.portfolio.controller;

import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.services.service.ProjectService;
import com.baconbao.portfolio.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @PostMapping ("/save")
    public ResponseEntity<ProjectDTO> save(@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.saveProject(projectDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.updateProject(projectDTO));
    }
    @GetMapping("/getByProfile")
    public ResponseEntity<List<ProjectDTO>> getByProfile(@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.getAllProjectDTOByProfile(projectDTO.getIdProfile()));
    }
}
