package com.dl.demo.web.config;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.service.UserService;
import com.dl.demo.web.controller.feign.AuthClient;
import com.example.common.api.model.token.TokenResponse;
import com.example.common.api.model.token.TokenRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthClient authClient;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && authClient.validateAuthToken(jwt)) {
                TokenResponse token = authClient.getToken(jwt).getBody();
                putAuthenticationTokenIntoContext(request, token);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private void putAuthenticationTokenIntoContext(HttpServletRequest request, TokenResponse token) {
        UserDetails userDetails;

        if (TokenRole.SERVICE.equals(token.getRole())) {
            userDetails = new org.springframework.security.core.userdetails.User(TokenRole.SERVICE.toString(), "admin", Collections.singletonList(new SimpleGrantedAuthority(TokenRole.SERVICE.role())));
        } else {
            User user = userService.findById(token.getUserId());
            userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(TokenRole.USER.role())));
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
