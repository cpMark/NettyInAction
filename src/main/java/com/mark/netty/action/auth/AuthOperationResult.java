package com.mark.netty.action.auth;

import com.mark.netty.action.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;
}
