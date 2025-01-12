package com.microservice.serviceA.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginModel {
    private String email;
    private String password;
}
