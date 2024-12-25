package com.microservice.serviceA.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileModel {
    private UUID uuid;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
}
