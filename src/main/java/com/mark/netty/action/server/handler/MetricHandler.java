package com.mark.netty.action.server.handler;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenpeng
 * @description
 * @date 2021/12/23
 */
@ChannelHandler.Sharable
public class MetricHandler extends ChannelDuplexHandler {

    private AtomicLong totalConnectionNum = new AtomicLong();

    {
        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register("totalConnectionNum", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return totalConnectionNum.longValue();
            }
        });

        // console的方式定时发送——5s
        ConsoleReporter consoleReporter=ConsoleReporter.forRegistry(metricRegistry).build();
        consoleReporter.start(5, TimeUnit.SECONDS);

        // jmx的方式发送——实时
        JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNum.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNum.decrementAndGet();
        super.channelInactive(ctx);
    }
}
