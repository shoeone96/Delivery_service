package com.example.demo.src.owner;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.owner.requestDto.OwnerLoginRequestDto;
import com.example.demo.src.owner.requestDto.OwnerRegisterRequestDto;
import com.example.demo.src.owner.responseDto.OwnerJoinResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class OwnerService {

    private final OwnerRepository ownerRepository;
    public OwnerJoinResponseDto registerOwner(OwnerRegisterRequestDto requestDto) {
        ownerRepository.findByUsername(requestDto.getUsername())
                .ifPresent(name -> {
                    throw new BaseException(BaseResponseStatus.OWNER_EXIST_USERNAME, "이미 존재하는 아이디입니다.");
                });
        // TODO: 비밀번호 해싱 도입 예정
        Owner owner = Owner.of(requestDto);
        ownerRepository.save(owner);
        return OwnerJoinResponseDto.of(owner);
    }

    // 추후 jwt 토큰 반환 예정
    public String login(OwnerLoginRequestDto requestDto) {
        // id 검증
        Owner owner = ownerRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.OWNER_NOT_EXIST, "유저가 존재하지 않습니다."));
        // password 대조
        // TODO: 비밀번호 해싱 도입 예정
        if(!owner.getPassword().equals(requestDto.getPassword())) throw new BaseException(BaseResponseStatus.INVALID_PASSWORD, "비밀번호가 유효하지 않습니다.");

        // 로그인 성공
        // TODO: 추후 JWT 도입 예정
        return "로그인에 성공";
    }
}
