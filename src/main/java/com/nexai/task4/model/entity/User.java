package com.nexai.task4.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String passport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Role role;

}
