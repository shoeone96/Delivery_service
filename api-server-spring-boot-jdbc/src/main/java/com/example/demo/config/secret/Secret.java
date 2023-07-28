package com.example.demo.config.secret;

import org.springframework.beans.factory.annotation.Value;

// TODO: 해당 KEY 값들을 꼭 바꿔서 사용해주세요!
// TODO: .gitignore에 추가하는거 앚지 마세요!
public class Secret {

    @Value("jwt.key")
    public static String JWT_SECRET_KEY;

}
