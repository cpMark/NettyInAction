package com.mark.netty.action.client;

import com.mark.netty.action.client.codec.*;
import com.mark.netty.action.client.dispatcher.OperationResultFuture;
import com.mark.netty.action.client.dispatcher.PendingResultCenter;
import com.mark.netty.action.client.dispatcher.ResponseDispatcherHandler;
import com.mark.netty.action.common.OperationResult;
import com.mark.netty.action.common.RequestMessage;
import com.mark.netty.action.order.OrderOperation;
import com.mark.netty.action.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class OrderClientV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        PendingResultCenter pendingResultCenter = new PendingResultCenter();

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());

                pipeline.addLast(new ResponseDispatcherHandler(pendingResultCenter));

                pipeline.addLast(new OperationToRequestMessageEncoder());

                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8090).sync();

        long streamId = IdUtil.nextId();
        RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(123, "tudou"));
        OperationResultFuture operationResultFuture = new OperationResultFuture();
        pendingResultCenter.add(streamId,operationResultFuture);

        channelFuture.channel().writeAndFlush(requestMessage);

        OperationResult operationResult = operationResultFuture.get();
        System.out.println(operationResult);

        channelFuture.channel().closeFuture().get();
    }
}
