package com.finnect.workspace.application;

import com.finnect.workspace.domain.state.WorkspaceState;
import com.finnect.workspace.application.port.in.GetWorkspacesQuery;
import com.finnect.workspace.application.port.out.GetWorkspacesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetWorkspacesService implements GetWorkspacesQuery {

    private final GetWorkspacesPort getWorkspacesPort;

    @Override
    public List<WorkspaceState> getWorkspaces(Long userId) {
        return getWorkspacesPort.getWorkspacesByUserId(userId);
    }
}