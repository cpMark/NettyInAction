package com.mark.netty.actioin.server.handler;

import com.mark.netty.actioin.common.Operation;
import com.mark.netty.actioin.common.OperationResult;
import com.mark.netty.actioin.common.RequestMessage;
import com.mark.netty.actioin.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        channelHandlerContext.writeAndFlush(responseMessage);
    }
}
