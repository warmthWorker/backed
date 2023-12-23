package org.java.controller;

import lombok.extern.slf4j.Slf4j;
import org.java.entity.SecurityUser;
import org.java.entity.pojo.User;
import org.java.utils.JwtUtil;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> map) {

        log.info("登录：{}",map);


        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(map.get("username"), map.get("password"));

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        User user = securityUser.getUser();

        log.info("登录成功");

        return JwtUtil.crateToken(Map.ofEntries(
                Map.entry("username", user.getUsername())
        ));
    }

    @GetMapping("/getInfo")
    public Result<User> getInfo() {

        log.info("获取登录信息");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        User user = securityUser.getUser();

        user.setPassword("");

        log.info("获取登录信息成功，用户信息：{}", user);

        return Result.success(user);
    }
}
