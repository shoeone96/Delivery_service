package com.example.demo.src.owner;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.owner.requestDto.OwnerLoginRequestDto;
import com.example.demo.src.owner.requestDto.OwnerRegisterRequestDto;
import com.example.demo.src.owner.responseDto.OwnerJoinResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/api/v1/owners")
    public BaseResponse<OwnerJoinResponseDto> ownerJoin(@RequestBody OwnerRegisterRequestDto requestDto){
        return BaseResponse.success(ownerService.registerOwner(requestDto));
    }

    @PostMapping("/api/v1/owners/login")
    public BaseResponse<String> ownerLogin(@RequestBody OwnerLoginRequestDto requestDto){
        return BaseResponse.success(ownerService.login(requestDto));
    }
}
