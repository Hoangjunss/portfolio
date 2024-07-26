package com.baconbao.portfolio.services;

import com.baconbao.portfolio.dto.AuthenticationRequest;
import com.baconbao.portfolio.dto.AuthenticationResponse;
import com.baconbao.portfolio.model.Role;
import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.repository.UserRepository;
import com.baconbao.portfolio.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(AuthenticationRequest registrationRequest){
            User users = User.builder()
                    .id(getGenerationId())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(Role.valueOf(registrationRequest.getRole()))
                    .build();
            User userResult = userRepository.save(users);
            return AuthenticationResponse.builder()
                    .user(userResult)
                    .message("User Saved Successfully")
                    .statusCode(200)
                    .build();

    }

    public AuthenticationResponse signIn(AuthenticationRequest signinRequest){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: " + user);
            var jwt = jwtTokenUtil.generateToken(user);
            var refreshToken = jwtTokenUtil.generateRefreshToken(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .token(jwt)
                .refreshToken(refreshToken)
                .expirationTime("24Hr")
                .message("Successfully Signed In")
                .build();
    }

    public AuthenticationResponse refreshToken(AuthenticationRequest refreshTokenRequest){
        AuthenticationResponse.AuthenticationResponseBuilder response = AuthenticationResponse.builder();
        String ourEmail = jwtTokenUtil.extractUsername(refreshTokenRequest.getToken());
        User users = userRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtTokenUtil.isTokenValid(refreshTokenRequest.getToken(), users)) {
            var jwt = jwtTokenUtil.generateToken(users);
            response.statusCode(200)
                    .token(jwt)
                    .refreshToken(refreshTokenRequest.getToken())
                    .expirationTime("24Hr")
                    .message("Successfully Refreshed Token");

        } else {
            response.statusCode(500)
                    .error("Invalid Token");
        }
        return response.build();
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
