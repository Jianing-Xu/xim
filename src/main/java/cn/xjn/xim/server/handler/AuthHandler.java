package cn.xjn.xim.server.handler;

import cn.xjn.xim.util.SessionManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xjn
 * @date 2023-12-27
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionManager.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // TODO optimise the processing logic
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionManager.hasLogin(ctx.channel())) {
            log.info("Authenticate successful, there is no need to auth again, the AuthHandler is removed");
        } else {
            log.warn("Authenticate failed, force to close connection.");
        }
        super.handlerRemoved(ctx);
    }
}
