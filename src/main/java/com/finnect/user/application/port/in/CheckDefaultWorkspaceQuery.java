package com.finnect.user.application.port.in;

import com.finnect.user.application.port.out.error.UserNotFoundException;
import com.finnect.user.vo.UserId;
import com.finnect.user.vo.WorkspaceId;

public interface CheckDefaultWorkspaceQuery {

    boolean checkDefaultWorkspace(UserId userId) throws UserNotFoundException;
}
