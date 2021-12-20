package com.mark.netty.actioin.keepalive;

import com.mark.netty.actioin.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
