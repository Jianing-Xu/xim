package cn.xjn.xim.server.handler;

import cn.xjn.xim.protocol.request.MessageRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xjn
 * @date 2023-12-27
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        log.info("[{}]: {}", ctx.channel().remoteAddress(), msg.getMessage());
    }
}
