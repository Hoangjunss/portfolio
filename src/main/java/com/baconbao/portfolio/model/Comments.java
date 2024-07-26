package com.baconbao.portfolio.model;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    private Integer id;
    private String content;
    private LocalDateTime createAt;
}
