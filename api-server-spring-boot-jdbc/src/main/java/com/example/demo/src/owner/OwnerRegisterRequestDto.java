package com.example.demo.src.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRegisterRequestDto {
    private String username;
    private String password;
    private String name;

}
