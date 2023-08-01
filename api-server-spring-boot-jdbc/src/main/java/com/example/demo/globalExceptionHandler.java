package com.example.demo;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@AllArgsConstructor
public class globalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<Void>> handleMyException(BaseException ex){
        BaseResponseStatus status = ex.getStatus();
        HttpStatus httpStatus = ex.getStatus().getStatus();
        return ResponseEntity.status(httpStatus).body(BaseResponse.error(status));
    }
}
