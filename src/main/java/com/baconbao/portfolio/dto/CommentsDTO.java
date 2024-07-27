package com.baconbao.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
    private Integer id;
    private String content;
    private LocalDateTime createAt;
    private Integer idProfile;
}
