package com.example.recipeapp.global.security.jwt;

import com.example.recipeapp.domain.auth.domain.model.AuthUser;
import com.example.recipeapp.domain.user.domain.model.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            @Qualifier("redisStringTemplate") RedisTemplate<String, String> redisTemplate
    ) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = jwtUtil.substringToken(bearerToken);
            try {
                if (Boolean.TRUE.equals(redisTemplate.hasKey("BL_" + token))) {
                    throw new BadCredentialsException("블랙리스트에 등록된 토큰입니다.");
                }

                Claims claims = jwtUtil.extractClaims(token);
                Long userId = Long.parseLong(claims.getSubject());
                String nickname = claims.get("nickname", String.class);
                String username = claims.get("username", String.class);
                String email = claims.get("email", String.class);
                UserRoleEnum role = UserRoleEnum.valueOf(claims.get("userRole", String.class));

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                new AuthUser(userId, nickname, username, email, role),
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException | IllegalArgumentException e) {
                log.warn("유효하지 않은 JWT: {}", e.getMessage());
                throw new AuthenticationCredentialsNotFoundException("유효하지 않은 토큰입니다.", e);
            } catch (BadCredentialsException e) {
                log.warn("인증 실패: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.warn("알 수 없는 인증 오류 발생: {}", e.getMessage());
                throw new AuthenticationCredentialsNotFoundException("알 수 없는 인증 오류가 발생했습니다.", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}