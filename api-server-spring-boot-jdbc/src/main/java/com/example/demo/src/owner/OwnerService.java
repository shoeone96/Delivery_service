package com.example.demo.src.owner;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
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
                    throw new BaseException(BaseResponseStatus.OWNER_EXIST_USERNAME);
                });
        Owner owner = Owner.of(requestDto);
        ownerRepository.save(owner);
        return OwnerJoinResponseDto.of(owner);
    }
}
