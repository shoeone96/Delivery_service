package com.example.demo.src.owner;

import com.example.demo.config.BaseException;
import com.example.demo.src.owner.requestDto.OwnerLoginRequestDto;
import com.example.demo.src.owner.requestDto.OwnerRegisterRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerService ownerService;

    @PersistenceContext
    EntityManager em;
    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    public void teardown() {
        ownerRepository.deleteAll();
    }
    @Test
    @DisplayName("사장등록 컨트롤러 테스트")
    @WithMockUser
    void ownerRegisterTest() throws Exception {

        String username = "username";
        String password = "password";
        String name = "name";

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(
                                new OwnerRegisterRequestDto(
                                        username,
                                        password,
                                        name)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("동일 아이디 사장 등록 테스트")
    @Transactional
    void ownerExistNameRegisterTest() throws Exception {

        String username = "username";
        String password = "password";
        String name = "name";

        ownerService.registerOwner(new OwnerRegisterRequestDto(username, password, name));
        em.flush();

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(
                                new OwnerRegisterRequestDto(
                                        username,
                                        password,
                                        name)))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException));
    }

    @Test
    @DisplayName("정상 사장 로그인 테스트")
    @Transactional
    void ownerLoginTest() throws Exception {

        String username = "username";
        String password = "password";
        String name = "name";

        ownerService.registerOwner(new OwnerRegisterRequestDto(username, password, name));
        OwnerLoginRequestDto requestDto = new OwnerLoginRequestDto(username, password);

        mockMvc.perform(post("/api/v1/owners/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사장 로그인 api 실패 테스트 - 유저 이름 잘못 기입")
    @Transactional
    void ownerLoginFailWrongUsernameTest() throws Exception {

        String username = "username";
        String password = "password";
        String name = "name";

        ownerService.registerOwner(new OwnerRegisterRequestDto(username, password, name));
        OwnerLoginRequestDto requestDto = new OwnerLoginRequestDto(username+1, password);

        mockMvc.perform(post("/api/v1/owners/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException))
                .andExpect(result -> assertEquals("유저가 존재하지 않습니다.", result.getResolvedException().getMessage()));;
    }


    @Test
    @DisplayName("사장 로그인 api 실패 테스트 - 비밀번호 잘못 기입")
    @Transactional
    void ownerLoginFailWrongPasswordTest() throws Exception {

        String username = "username";
        String password = "password";
        String name = "name";

        ownerService.registerOwner(new OwnerRegisterRequestDto(username, password, name));
        OwnerLoginRequestDto requestDto = new OwnerLoginRequestDto(username, password +1);

        mockMvc.perform(post("/api/v1/owners/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException))
                .andExpect(result -> assertEquals("비밀번호가 유효하지 않습니다.", result.getResolvedException().getMessage()));
        ;
    }


}
