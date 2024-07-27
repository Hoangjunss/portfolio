package com.baconbao.portfolio.services;

import com.baconbao.portfolio.dto.AuthenticationRequest;
import com.baconbao.portfolio.dto.AuthenticationResponse;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.model.Role;
import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.repository.UserRepository;
import com.baconbao.portfolio.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
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
            try {
                log.info("Sign up Auth Service");
                User users = User.builder()
                        .id(getGenerationId())
                        .name(registrationRequest.getName())
                        .email(registrationRequest.getEmail())
                        .password(passwordEncoder.encode(registrationRequest.getPassword()))
                        .role(Role.valueOf(registrationRequest.getRole()))
                        .profile(null)
                        .build();
                User userResult = userRepository.save(users);
                return AuthenticationResponse.builder()
                        .user(userResult)
                        .message("User Saved Successfully")
                        .statusCode(200)
                        .build();
            } catch (Exception e){
                return  AuthenticationResponse.builder()
                        .statusCode(Error.AUTHENTICATION_RESPONSE_FAILED.getCode())
                        .error(Error.AUTHENTICATION_RESPONSE_FAILED.getMessage())
                        .build();
            }
    }

    public AuthenticationResponse signIn(AuthenticationRequest signinRequest){
            try {
                log.info("Sign in Auth Service");
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
            } catch (Exception e){
                return  AuthenticationResponse.builder()
                       .statusCode(Error.AUTHENTICATION_REQUEST_INVALID.getCode())
                       .error(Error.AUTHENTICATION_REQUEST_INVALID.getMessage())
                       .build();
            }
    }

    public AuthenticationResponse refreshToken(AuthenticationRequest refreshTokenRequest){
        try {
            log.info("Refresh Token Auth Service");
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
                response.statusCode(Error.AUTHENTICATION_TOKEN_EXPIRED.getCode())
                        .error(Error.AUTHENTICATION_TOKEN_EXPIRED.getMessage());
            }
            return response.build();
        } catch (Exception e){
            return AuthenticationResponse.builder()
                    .statusCode(Error.AUTHENTICATION_REQUEST_INVALID.getCode())
                    .error(Error.AUTHENTICATION_REQUEST_INVALID.getMessage())
                    .build();
        }
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
