package com.daesoo.dmotools.common.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.daesoo.dmotools.user.UserService;
import com.daesoo.dmotools.user.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        String token = jwtUtil.resolveToken(request);
        if (token != null) {
            try {
                if (jwtUtil.validateToken(token)) {
                    Claims info = jwtUtil.getUserInfoFromToken(token);
                    setAuthentication(info.getSubject());
                }
            } catch (ExpiredJwtException e) {
                // Access Token이 만료된 경우
                String refreshToken = request.getHeader(JwtUtil.REFRESH_HEADER);
                if (refreshToken != null) {
                    try {
                        // 새로운 Access Token 발급 시도
                        TokenDto tokenDto = userService.reissueAccessToken(refreshToken);
                        
                        // 새로운 Access Token을 응답 헤더에 추가
                        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
                        System.out.println("재발급");
                        // 새로운 토큰으로 인증 처리
                        Claims info = jwtUtil.getUserInfoFromToken(tokenDto.getAccessToken().substring(7));
                        System.out.println(tokenDto.getRefreshToken());
                        setAuthentication(info.getSubject());
                    } catch (Exception re) {
                        log.error("Failed to reissue access token: {}", re.getMessage());
                        // 여기서는 예외를 던지지 않고 다음 필터로 진행
                        // 클라이언트는 401 응답을 받고 로그인 페이지로 리다이렉트하거나 적절한 처리를 해야 함
                    }
                }
            } catch (Exception e) {
                log.error("JWT processing error: {}", e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String userId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userId);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
