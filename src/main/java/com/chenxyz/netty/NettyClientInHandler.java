package com.chenxyz.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class NettyClientInHandler extends ChannelInboundHandlerAdapter {

    private StringBuffer message;

    private String sendMsg;

    public NettyClientInHandler(StringBuffer message, String sendMsg) {
        this.message = message;
        this.sendMsg = sendMsg;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf encoded = ctx.alloc().buffer(4 * sendMsg.length());
        encoded.writeBytes(sendMsg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf)msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        message.append(new String(result1));
        result.release();
    }
}
