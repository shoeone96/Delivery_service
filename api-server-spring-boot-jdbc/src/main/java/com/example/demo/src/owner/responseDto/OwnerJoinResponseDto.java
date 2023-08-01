package com.example.demo.src.owner.responseDto;

import com.example.demo.src.owner.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OwnerJoinResponseDto {
    private String username;

    public static OwnerJoinResponseDto of(Owner owner) {
        return new OwnerJoinResponseDto(owner.getUsername());
    }
}
