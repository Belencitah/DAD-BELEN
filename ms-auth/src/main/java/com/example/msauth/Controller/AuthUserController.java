package com.example.msauth.Controller;

import com.example.msauth.dto.AuthUserDto;
import com.example.msauth.entity.AuthUser;
import com.example.msauth.entity.TokenDto;
import com.example.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto) {
        System.out.println("Request recibido: " + authUserDto);
        TokenDto tokenDto = authUserService.login(authUserDto);
        if (tokenDto == null) {
            System.out.println("🔴 Login fallido: credenciales incorrectas o usuario no encontrado");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }


    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> save(@RequestBody AuthUserDto authUserDto) {
        AuthUser authUser = authUserService.save(authUserDto);
        if (authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }

}
