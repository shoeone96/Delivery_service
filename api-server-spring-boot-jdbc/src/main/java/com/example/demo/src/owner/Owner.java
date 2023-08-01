package com.example.demo.src.owner;

import com.example.demo.src.owner.requestDto.OwnerRegisterRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    private String username;
    private String password;
    private String name;

    public Owner(OwnerRegisterRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
    }

    public static Owner of(OwnerRegisterRequestDto requestDto) {
        return new Owner(requestDto);
    }
}
