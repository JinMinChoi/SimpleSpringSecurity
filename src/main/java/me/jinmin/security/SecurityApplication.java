package me.jinmin.security;

import me.jinmin.security.entity.User;
import me.jinmin.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SecurityApplication {

    /**
     * 예제를 위해 인위적인 DATA를 애플리케이션 실행 전에 repository에 넣었다.
     */

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        List<User> users = Stream.of(
                new User(101L, "javatechie", "password", "aaa@gmail.com"),
                new User(102L, "user1", "pwd1", "user1@gmail.com"),
                new User(103L, "user2", "pwd2", "user2@gmail.com"),
                new User(104L, "user3", "pwd3", "user3@gmail.com")
        ).collect(Collectors.toList());

        userRepository.saveAll(users);
    }
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
