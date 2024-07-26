package com.baconbao.portfolio.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ProjectDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
}
