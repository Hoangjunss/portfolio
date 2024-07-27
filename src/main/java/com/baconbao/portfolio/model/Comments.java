package com.baconbao.portfolio.model;

import jakarta.persistence.*;
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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_send_id", referencedColumnName = "id")
    private User userSend;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
