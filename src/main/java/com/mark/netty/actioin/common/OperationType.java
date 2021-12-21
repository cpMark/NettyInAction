package com.mark.netty.actioin.common;

import com.mark.netty.actioin.auth.AuthOperation;
import com.mark.netty.actioin.auth.AuthOperationResult;
import com.mark.netty.actioin.keepalive.KeepaliveOperation;
import com.mark.netty.actioin.keepalive.KeepaliveOperationResult;
import com.mark.netty.actioin.order.OrderOperation;
import com.mark.netty.actioin.order.OrderOperationResult;

public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class),
    ;

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> operationResultClazz) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }

    public static OperationType fromOpCode(int opCode) {
        for (OperationType value : values()) {
            if(value.getOpCode() == opCode){
                return value;
            }
        }
        return null;
    }

    public static OperationType fromOperation(Operation operation) {
        return null;
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }
}
