package me.jinmin.security.controller;

import me.jinmin.security.entity.AuthRequest;
import me.jinmin.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1) "/" : (설정(Config)에서 /authenticate를 제외한 모든 경로는 인증을 거쳐야 허용된다.)
 * 인증 시도, 하지만 `jwtUtil`에서 token에 대한 정보가 없어서 인증 정보를 생성하지 못한다.
 *
 * 2) "/authenticate" : (설정(Config)에서 /authenticate는 .permitAll)
 * 인증 정보가 없이도 접근 가능, request로부터 넘어온 인증정보를 확인한 후 jwt를 스트링으로 발급
 *
 * 3) token(jwt) 받고 & "/" : 발급받은 token을 헤더에 넣고 요청을 하면 jwtFilter에서 해석한 후 인증 정보 생성
 * 성공적으로 인증을 하고 웹페이지에 "Welcome to javatechie"가 뜬다.
 */
@RestController
public class BasicController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to javatechie";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserName())
            );
        } catch (Exception e) {
            throw new Exception("invalid username & password");
        }

        return jwtUtil.generateToken(request.getUserName());
    }
}
