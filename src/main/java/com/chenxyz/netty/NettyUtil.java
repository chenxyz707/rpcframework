package com.chenxyz.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class NettyUtil {

    public static void startServer(String port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerInHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128);
            ChannelFuture future = bootstrap.bind(Integer.parseInt(port)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static String sendMsg(String host, String port, String sendMsg) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final StringBuffer resultMsg = new StringBuffer();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyClientInHandler(resultMsg, sendMsg));
                }
            });

            //这个是连接服务端，一直在等待着服务端的返回消息，返回的信息封装到future，可以监控线程的返回
            ChannelFuture future = bootstrap.connect(host, Integer.parseInt(port))
                    .channel().closeFuture().await();
            return resultMsg.toString();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
