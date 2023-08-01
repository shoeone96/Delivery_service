package com.example.demo;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@AllArgsConstructor
public class globalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public BaseResponse<HttpStatus> handleMyException(BaseException ex){;
        return BaseResponse.error(ex.getStatus());
    }
}
