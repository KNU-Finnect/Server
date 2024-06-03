package com.finnect.user.adapter.in.security;

import com.finnect.user.application.port.in.AuthorizeUseCase;
import com.finnect.user.application.port.in.command.AuthorizeCommand;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthorizeUseCase authorizeUseCase;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        AuthorizeCommand command = AuthorizeCommand.builder()
                .bearerToken(request.getHeader(HttpHeaders.AUTHORIZATION))
                .build();

        authorizeUseCase.authorize(command);
        filterChain.doFilter(request, response);
    }
}
