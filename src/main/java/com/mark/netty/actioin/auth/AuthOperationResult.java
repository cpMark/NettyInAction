package com.mark.netty.actioin.auth;

import com.mark.netty.actioin.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;
}
