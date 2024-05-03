package com.finnect.workspace.adaptor;

import com.finnect.common.Response;
import com.finnect.workspace.MemberState;
import com.finnect.workspace.WorkspaceState;
import com.finnect.workspace.adaptor.in.web.req.CreateMemberRequest;
import com.finnect.workspace.adaptor.in.web.req.CreateWorkspaceRequest;
import com.finnect.workspace.adaptor.in.web.res.CreateMemberResponse;
import com.finnect.workspace.adaptor.in.web.res.CreateWorkspaceResponse;
import com.finnect.workspace.adaptor.in.web.res.dto.MemberDto;
import com.finnect.workspace.adaptor.in.web.res.dto.WorkspaceDto;
import com.finnect.workspace.application.port.in.CreateMemberCommand;
import com.finnect.workspace.application.port.in.CreateMemberUsecase;
import com.finnect.workspace.application.port.in.CreateWorkspaceCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final CreateMemberUsecase createMemberUsecase;

    @PostMapping("/workspaces/members")
    public ResponseEntity<Response<CreateMemberResponse>> createWorkspace(@RequestBody CreateMemberRequest request) {
        CreateMemberCommand memberCommand = CreateMemberCommand.builder()
                .userId(1L)
                .workspaceId(1L)
                .nickname(request.getNickname())
                .role(request.getRole())
                .phone(request.getPhone())
                .build();

        MemberState state = createMemberUsecase.createMember(memberCommand);

        MemberDto memberDto = new MemberDto(
                state.getUserId(),
                state.getNickname(),
                state.getRole(),
                state.getPhone()
        );
        CreateMemberResponse createMemberResponse = new CreateMemberResponse(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(HttpStatus.OK.value(), createMemberResponse));
    }
}