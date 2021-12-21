package com.mark.netty.action.client.codec;

import com.mark.netty.action.common.RequestMessage;
import com.mark.netty.action.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        requestMessage.encode(byteBuf);

        list.add(byteBuf);
    }
}
