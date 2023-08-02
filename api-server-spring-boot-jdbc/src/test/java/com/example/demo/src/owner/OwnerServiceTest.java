package com.example.demo.src.owner;

import com.example.demo.config.BaseException;
import com.example.demo.src.owner.requestDto.OwnerLoginRequestDto;
import com.example.demo.src.owner.requestDto.OwnerRegisterRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class OwnerServiceTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @DisplayName("정상적인 사장등록 테스트")
    @Test
    @Transactional
    @Rollback
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
    @Transactional
    @Rollback
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

    @DisplayName("사장 로그인 테스트")
    @Test
    @Rollback
    @Transactional
    public void OwnerLoginTest() throws Exception {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        OwnerRegisterRequestDto requestDto = new OwnerRegisterRequestDto(username, password, name);
        log.info("before join password={}", requestDto.getPassword());
        ownerService.registerOwner(requestDto);
        log.info("after join password={}", requestDto.getPassword());

        // when
        OwnerLoginRequestDto dto = new OwnerLoginRequestDto(username, password);
        log.info("before login password={}", requestDto.getPassword());
        String result = ownerService.login(dto);
        log.info("after login password={}", dto.getPassword());

        // then 토큰으로 반환
//        assertThat(result).isEqualTo("로그인에 성공");

    }

    @DisplayName("사장 로그인 실패 테스트 - 유저 이름 잘못 기입")
    @Test
    @Transactional
    @Rollback
    public void OwnerLoginWrongUserNameTest() throws Exception {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        OwnerRegisterRequestDto requestDto = new OwnerRegisterRequestDto(username, password, name);

        ownerService.registerOwner(requestDto);

        // when & then

        assertThatThrownBy(() ->ownerService.login(
                new OwnerLoginRequestDto(username +1 , password)))
                .isInstanceOf(BaseException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }

    @DisplayName("사장 로그인 실패 테스트 - 비밀번호 잘못 기입")
    @Test
    @Transactional
    @Rollback
    public void OwnerLoginWrongPasswordTest() throws Exception {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        OwnerRegisterRequestDto requestDto = new OwnerRegisterRequestDto(username, password, name);

        ownerService.registerOwner(requestDto);

        // when & then

        assertThatThrownBy(() ->ownerService.login(
                new OwnerLoginRequestDto(username , password +1)))
                .isInstanceOf(BaseException.class)
                .hasMessage("비밀번호가 유효하지 않습니다.");
    }



}