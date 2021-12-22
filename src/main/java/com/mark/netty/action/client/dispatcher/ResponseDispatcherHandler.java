package com.mark.netty.action.client.dispatcher;

import com.mark.netty.action.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ResponseDispatcherHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    private PendingResultCenter pendingResultCenter;

    public ResponseDispatcherHandler(PendingResultCenter pendingResultCenter) {
        this.pendingResultCenter = pendingResultCenter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage) throws Exception {
        pendingResultCenter.set(responseMessage.getMessageHeader().getStreamId(), responseMessage.getMessageBody());
    }
}
