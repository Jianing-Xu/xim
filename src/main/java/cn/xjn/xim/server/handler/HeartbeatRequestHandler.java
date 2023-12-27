package cn.xjn.xim.server.handler;

import cn.xjn.xim.protocol.request.HeartbeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xjn
 * @date 2023-12-27
 */
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartbeatRequestPacket msg) throws Exception {

    }
}
