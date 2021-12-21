package com.mark.netty.action.client.codec;

import com.mark.netty.action.common.Operation;
import com.mark.netty.action.common.RequestMessage;
import com.mark.netty.action.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Operation operation, List<Object> list) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), operation);

        list.add(requestMessage);
    }
}
