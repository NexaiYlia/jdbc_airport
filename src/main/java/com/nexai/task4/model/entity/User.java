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
    private int roleId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", passport='").append(passport).append('\'');
        sb.append(", roleId=").append(roleId);
        sb.append('}');
        return sb.toString();
    }
}
