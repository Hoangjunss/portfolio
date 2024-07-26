package com.baconbao.portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeProfile {
    @Id
    private Integer id;
    private String name;
}
