package com.finnect.user.adapter.in.web;

import com.finnect.common.ApiUtils;
import com.finnect.common.ApiUtils.ApiResult;
import com.finnect.user.adapter.in.web.request.EmailAuthPostRequest;
import com.finnect.user.adapter.in.web.request.EmailAuthVerifyRequest;
import com.finnect.user.adapter.in.web.request.PasswordRequest;
import com.finnect.user.adapter.in.web.request.SignupRequest;
import com.finnect.user.application.port.in.*;
import com.finnect.user.application.port.in.command.*;
import com.finnect.user.state.AccessTokenState;
import com.finnect.user.vo.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final SignupUseCase signupUseCase;
    private final ReissueUseCase reissueUseCase;
    private final SendEmailCodeUseCase sendEmailCodeUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @Autowired
    public UserController(
            SignupUseCase signupUseCase,
            ReissueUseCase reissueUseCase,
            SendEmailCodeUseCase sendEmailCodeUseCase,
            ChangePasswordUseCase changePasswordUseCase
    ) {
        this.signupUseCase = signupUseCase;
        this.reissueUseCase = reissueUseCase;
        this.sendEmailCodeUseCase = sendEmailCodeUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<Object>> signup(@RequestBody SignupRequest request) {
        SignupCommand command = SignupCommand.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        signupUseCase.signup(command);

        return ResponseEntity.ok(ApiUtils.success(
                HttpStatus.CREATED,
                null
        ));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/reissue")
    public ResponseEntity<ApiResult<String>> reissue(@CookieValue("Refresh") String refreshToken) {
        ReissueCommand command = ReissueCommand.builder()
                .refreshToken(refreshToken)
                .build();

        AccessTokenState accessToken = reissueUseCase.reissue(command);

        return ResponseEntity.ok(ApiUtils.success(
                HttpStatus.CREATED,
                accessToken.toBearerString()
        ));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/email-auth")
    public ResponseEntity<ApiResult<Object>> emailAuthPost(@RequestBody EmailAuthPostRequest request) {
        SendEmailCodeCommand command = SendEmailCodeCommand.builder()
                .email(request.email())
                .build();

        sendEmailCodeUseCase.sendEmailCode(command);

        return ResponseEntity.ok(ApiUtils.success(
                HttpStatus.OK,
                null
        ));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/email-auth/signup")
    public ResponseEntity<ApiResult<Object>> emailAuthVerify(@RequestBody EmailAuthVerifyRequest request) {
        VerifyEmailCodeCommand command = VerifyEmailCodeCommand.builder()
                .email(request.email())
                .codeNumber(request.codeNumber())
                .build();

        signupUseCase.verifyEmailCode(command);

        return ResponseEntity.ok(ApiUtils.success(
                HttpStatus.OK,
                null
        ));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/password")
    public ResponseEntity<ApiResult<String>> password(@RequestBody PasswordRequest request) {
        UserId userId = UserId.parseOrNull(
                SecurityContextHolder.getContext().getAuthentication().getDetails().toString()
        );
        assert userId != null;

        ChangePasswordCommand command = ChangePasswordCommand.builder()
                .userId(userId)
                .password(request.password())
                .build();

        changePasswordUseCase.changePassword(command);

        return ResponseEntity.ok(ApiUtils.success(
                HttpStatus.OK,
                null
        ));
    }
}
