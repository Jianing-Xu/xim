package cn.xjn.xim.server.handler;

import cn.xjn.xim.protocol.request.ListGroupMemberRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xjn
 * @date 2023-12-27
 */
public class ListGroupMemberRequestHandler extends SimpleChannelInboundHandler<ListGroupMemberRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberRequestPacket msg) throws Exception {

    }
}
