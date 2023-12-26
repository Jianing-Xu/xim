package cn.xjn.xim.client.handler;

import cn.xjn.xim.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xjn
 * @date 2023-12-26
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            log.info("[{}] login successful.", responsePacket.getUsername());
        } else {
            log.warn("[{}] login failed, the reason is: {}.", responsePacket.getUsername(), responsePacket.getReason());
        }
    }
}
