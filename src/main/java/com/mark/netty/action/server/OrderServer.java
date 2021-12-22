package com.mark.netty.action.server;

import com.mark.netty.action.server.codec.OrderFrameDecoder;
import com.mark.netty.action.server.codec.OrderFrameEncoder;
import com.mark.netty.action.server.codec.OrderProtocolDecoder;
import com.mark.netty.action.server.codec.OrderProtocolEncoder;
import com.mark.netty.action.server.handler.MetricHandler;
import com.mark.netty.action.server.handler.OrderServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutionException;

public class OrderServer {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);

        NioEventLoopGroup boss = new NioEventLoopGroup(5, new DefaultThreadFactory("boss"));
        NioEventLoopGroup worker = new NioEventLoopGroup(10, new DefaultThreadFactory("worker"));
        serverBootstrap.group(boss, worker);

        MetricHandler metricHandler = new MetricHandler();

        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));

        // 作用于SocketChannel
        // 设置是否启用Nagle算法：将小的碎片数据连接成更大的报文，来提高发送效率，默认为false（需要发送一些较小报文时不需修改）
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY, true);

        // 作用于ServerSocketChannel
        // 最大的等待连接数量，先取/proc/sys/net/core/somaxcon,没有再取sysctl，还是没有默认为128
        serverBootstrap.option(NioChannelOption.SO_BACKLOG, 1024);

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();

                pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

                pipeline.addLast("OrderFrameDecoder", new OrderFrameDecoder());
                pipeline.addLast("OrderFrameEncoder", new OrderFrameEncoder());
                pipeline.addLast("OrderProtocolEncoder", new OrderProtocolEncoder());
                pipeline.addLast("OrderProtocolDecoder", new OrderProtocolDecoder());

                pipeline.addLast("metricHandler",metricHandler);

                pipeline.addLast("OrderServerProcessHandler", new OrderServerProcessHandler());

                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().get();
    }
}
