package com.example.demo.src.owner.requestDto;

import com.example.demo.utils.SHA256;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerLoginRequestDto {
    private String username;
    private String password;

    public void hashing() {
        this.password = SHA256.encrypt(this.password);
    }
}
