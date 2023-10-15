package com.mno.shop.auth;

import com.mno.shop.config.JwtService;
import com.mno.shop.entity.Role;
import com.mno.shop.entity.Token;
import com.mno.shop.entity.TokenType;
import com.mno.shop.entity.User;
import com.mno.shop.otb.OtpRepo;
import com.mno.shop.otb.OtpService;
import com.mno.shop.repo.TokenRepo;
import com.mno.shop.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.name)
                .gmail(request.gmail)
                .address(request.address)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();


        var savedUser = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);

        System.out.printf(jwtToken);

                saveUserToken(savedUser, jwtToken);
        otpService.sendOtp(request.getGmail(),jwtToken);
        return AuthenticationResponse.builder()
                .user(savedUser)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getGmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByGmail(request.getGmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        otpService.sendOtp(request.getGmail(),jwtToken);

        return AuthenticationResponse.builder()
                .user(user)
                .build();
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

