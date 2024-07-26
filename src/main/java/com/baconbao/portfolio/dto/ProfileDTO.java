package com.baconbao.portfolio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDTO {
    private Integer id;
    private String objective;
    private String education;
    private String workExperience;
    private String skills;
    private String address;
    private String contactInfo;
}
