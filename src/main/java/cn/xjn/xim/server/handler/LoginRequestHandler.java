package cn.xjn.xim.server.handler;

import cn.xjn.xim.protocol.request.LoginRequestPacket;
import cn.xjn.xim.protocol.response.LoginResponsePacket;
import cn.xjn.xim.util.IDUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xjn
 * @date 2023-12-26
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        String username = requestPacket.getUsername();
        responsePacket.setUsername(username);

        if (auth(requestPacket)) {
            responsePacket.setSuccess(true);
            responsePacket.setUserId(IDUtil.genRandomId());
            log.info("[{}] login successful.", requestPacket.getUsername());
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("Authentication failed");
            log.info("login failed.");
        }

        ctx.writeAndFlush(responsePacket);
    }

    private boolean auth(LoginRequestPacket requestPacket) {
        // TODO auth
        return true;
    }
}
