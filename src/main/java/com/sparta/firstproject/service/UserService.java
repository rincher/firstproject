package com.sparta.firstproject.service;

import com.sparta.firstproject.dto.SignupRequestDto;
import com.sparta.firstproject.model.User;
import com.sparta.firstproject.model.UserRole;
import com.sparta.firstproject.repository.UserRepository;
import com.sparta.firstproject.security.UserDetailsImpl;
import com.sparta.firstproject.security.kakao.KakaoOAuth2;
import com.sparta.firstproject.security.kakao.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
        this.authenticationManager = authenticationManager;
    }

    public User registerUser(SignupRequestDto requestDto) {
        //알파벳과 숫자 3자리 이상으로만 이루어져 있는 문자열
        String regex = "^[A-Za-z]\\w{2,}$";
        // 문자열을 정규표현식으로 만들어서
        Pattern p = Pattern.compile(regex);
        //받아온 유저 이름과 맞는지 확인하고
        String username = requestDto.getUsername();
        Matcher m = p.matcher(username);
        // 일치하지 않음면 오류 뿌리기
        if (!m.matches()){
            throw new IllegalArgumentException("잘못된 아이디");
        }

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복");
        }

        String plain_password = requestDto.getPassword();
        String password_check = requestDto.getPassword_checker();
        //비밀번호 4자리 이상 유저 이름 없이
        if (plain_password.length() < 4 | plain_password.contains(username)){
            throw new IllegalArgumentException("잘못된 비밀번호");
        }
        //비밀번호와 비밀번호 재확인이 일치하는지
        if (!plain_password.equals(password_check)){
            throw new IllegalArgumentException("불일치");
       }
        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());
        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        return user;
    }

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            UserRole role = UserRole.USER;

            kakaoUser = new User(nickname, encodedPassword, role, kakaoId);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}