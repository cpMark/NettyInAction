package com.mark.netty.actioin.common;

public class ResponseMessage extends Message<OperationResult>{
    @Override
    public Class<OperationResult> getMessageBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode).getOperationResultClazz();
    }
}
