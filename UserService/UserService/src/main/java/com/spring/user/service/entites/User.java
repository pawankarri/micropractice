package com.spring.user.service.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="micro_users")
@ToString
public class User {
    @Id

    private String userId;
    private String name;
    private String email;
    private String about;
@Transient
    private List<Rating> ratings=new ArrayList<>();
}
