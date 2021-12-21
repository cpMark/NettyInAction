package com.mark.netty.action.keepalive;

import com.mark.netty.action.common.Operation;
import com.mark.netty.action.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Data
@Log
public class KeepaliveOperation extends Operation {

    private long time;

    public KeepaliveOperation(){
        this.time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        KeepaliveOperationResult response = new KeepaliveOperationResult(time);
        return response;
    }
}
