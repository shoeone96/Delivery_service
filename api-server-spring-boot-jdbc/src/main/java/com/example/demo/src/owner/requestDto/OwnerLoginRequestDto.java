package com.example.demo.src.owner.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerLoginRequestDto {
    private String username;
    private String password;

}
