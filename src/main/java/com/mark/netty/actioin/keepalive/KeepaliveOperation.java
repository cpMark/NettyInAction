package com.mark.netty.actioin.keepalive;

import com.mark.netty.actioin.common.Operation;
import com.mark.netty.actioin.common.OperationResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
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
