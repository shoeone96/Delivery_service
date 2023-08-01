package com.example.demo.src.owner;

import com.example.demo.config.BaseResponse;
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
}
