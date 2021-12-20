package com.mark.netty.actioin.common;

public class RequestMessage extends Message<Operation>{
    @Override
    public Class<Operation> getMessageBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode);
    }

    public RequestMessage(){

    }

    public RequestMessage(Long streamId,Operation operation){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        messageHeader.setOpCode(OperationType.fromOperation(operation).getOpCode());

        this.setMessageHeader(messageHeader);
        this.setMessageBody(operation);
    }
}
