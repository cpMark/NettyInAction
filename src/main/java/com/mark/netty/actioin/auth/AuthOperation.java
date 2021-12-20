package com.mark.netty.actioin.auth;


import com.mark.netty.actioin.common.Operation;
import com.mark.netty.actioin.common.OperationResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    @Override
    public OperationResult execute() {
        if("main".equalsIgnoreCase(this.userName)){
            AuthOperationResult response = new AuthOperationResult(true);
            return response;
        }
        return new AuthOperationResult(false);
    }
}
