package com.mno.shop.otb;

import com.mno.shop.config.JwtService;
import com.mno.shop.entity.Token;
import com.mno.shop.entity.User;
import com.mno.shop.repo.TokenRepo;
import com.mno.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepo otpRepo;

    private final UserService userService;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;

    private Otp findByOtp(int otp){
        return otpRepo.findByOtp(otp).orElse(null);
    }

    public String sendOtp(String email,String token){
        Random random = new Random();
        int otpCode = random.nextInt(100000,999999);
        System.out.println(otpCode);
        Otp createOtp = Otp.builder()
                .email(email)
                .token(token)
                .otp(otpCode)
                .build();
        otpRepo.save(createOtp);

        return email;
    }

    public OtpDtoResponse authenticateByotp(OtpDtoRequest otpDtoRequest){
        Otp otp= findByOtp(otpDtoRequest.getOtp());
        System.out.println(otp.getEmail());
        System.out.println(otpDtoRequest.getGmail());
        otpRepo.delete(otp);
        if (otpDtoRequest.getGmail().equals(otp.getEmail())){
            String email = otp.getEmail();
            User user = userService.userfindByGmail(email);

            OtpDtoResponse otpDtoResponse;
            if (user == null){
                otpDtoResponse = OtpDtoResponse.builder()
                        .email(email)
                        .checkotp(true)
                        .logined(false)
                        .massage("User is not register")
                        .build();
            }else {
                otpDtoResponse = OtpDtoResponse.builder()
                        .email(email)
                        .user(user)
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();

            }
            return otpDtoResponse;
        }else {
            return OtpDtoResponse.builder()
                    .email(otpDtoRequest.getGmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("Not same Otp Try again")
                    .build();
        }
    }

    public OtpDtoResponse registerByotp(OtpDtoRequest otpDtoRequest) {
        Otp otp= findByOtp(otpDtoRequest.getOtp());
        System.out.println(otp.getEmail());
        System.out.println(otpDtoRequest.getGmail());
        otpRepo.delete(otp);
        OtpDtoResponse otpDtoResponse;
        if (otpDtoRequest.getGmail().equals(otp.getEmail())){
            String email = otp.getEmail();
            User user = userService.userfindByGmail(email);


                otpDtoResponse = OtpDtoResponse.builder()
                        .email(email)
                        .user(user)
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();
            return otpDtoResponse;
        }else {
            return OtpDtoResponse.builder()
                    .email(otpDtoRequest.getGmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("email or otp is fail")
                    .build();
        }
    }
}
