package com.mark.netty.action.client;

import com.mark.netty.action.client.codec.OrderFrameDecoder;
import com.mark.netty.action.client.codec.OrderFrameEncoder;
import com.mark.netty.action.client.codec.OrderProtocolDecoder;
import com.mark.netty.action.client.codec.OrderProtocolEncoder;
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

public class OrderClientV0 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());

                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8090).sync();

        RequestMessage tudou = new RequestMessage(IdUtil.nextId(), new OrderOperation(123, "tudou"));

        for (int i = 0; i < 10000; i++) {
            channelFuture.channel().writeAndFlush(tudou);
        }

        channelFuture.channel().closeFuture().get();
    }
}
