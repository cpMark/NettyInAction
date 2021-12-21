package com.mark.netty.action.keepalive;

import com.mark.netty.action.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
