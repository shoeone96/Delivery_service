package com.example.demo.src.owner.requestDto;

import com.example.demo.utils.SHA256;
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

    public void hashing() {
        this.password = SHA256.encrypt(this.password);
    }
}
