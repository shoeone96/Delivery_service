package com.example.demo.src.owner;

import com.example.demo.config.BaseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class OwnerServiceTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @DisplayName("정상적인 사장등록 테스트")
    @Test
    public void 사장등록_테스트() throws Exception {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        OwnerRegisterRequestDto requestDto = new OwnerRegisterRequestDto(username, password, name);

        // when
        ownerService.registerOwner(requestDto);

        // then
        Owner owner = ownerRepository.findByUsername(username).orElseThrow(() -> new Exception("이 아이디는 없는 사람입니다."));
        assertThat(owner.getUsername()).isEqualTo(username);

    }

    @DisplayName("동일 username 사장등록 테스트")
    @Test
    public void 사장등록_예외_테스트() throws Exception {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        OwnerRegisterRequestDto requestDto = new OwnerRegisterRequestDto(username, password, name);
        OwnerRegisterRequestDto sameUsername = new OwnerRegisterRequestDto(username, password + 1 , name + 1);

        // when
        ownerService.registerOwner(requestDto);
        assertThatThrownBy(() ->ownerService.registerOwner(sameUsername))
                .isInstanceOf(BaseException.class);

        // then
        Owner owner = ownerRepository.findByUsername(username).orElseThrow(() -> new Exception("이 아이디는 없는 사람입니다."));
        assertThat(owner.getUsername()).isEqualTo(username);
    }

}