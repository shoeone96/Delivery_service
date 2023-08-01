package com.example.demo.src.owner;

import com.example.demo.config.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
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
    @Rollback(true) // 롤백 설정 추가
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


}
