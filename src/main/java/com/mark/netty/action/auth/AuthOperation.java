package com.mark.netty.action.auth;


import com.mark.netty.action.common.Operation;
import com.mark.netty.action.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Data
@Log
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
