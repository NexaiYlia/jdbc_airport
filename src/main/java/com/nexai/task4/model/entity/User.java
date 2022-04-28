package com.nexai.task4.model.entity;

import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String passport;
    private Role role;

}
