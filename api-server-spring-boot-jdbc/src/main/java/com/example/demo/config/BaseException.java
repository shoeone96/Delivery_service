package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private BaseResponseStatus status;
    private HttpStatus httpStatus;

    public BaseException(BaseResponseStatus status){
        this.status = status;
        this.httpStatus = status.getStatus();
    }
}
