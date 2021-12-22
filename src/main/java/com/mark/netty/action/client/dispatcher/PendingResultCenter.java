package com.mark.netty.action.client.dispatcher;

import com.mark.netty.action.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PendingResultCenter {

    private static final Map<Long, OperationResultFuture> MAP = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future) {
        MAP.put(streamId, future);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = MAP.get(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
            MAP.remove(streamId);
        }
    }

}
