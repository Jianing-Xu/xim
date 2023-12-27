package cn.xjn.xim.client.handler;

import cn.xjn.xim.protocol.request.HeartbeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author xjn
 * @date 2023-12-28
 */
public class HeartbeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleHeartbeat(ctx);
        super.channelActive(ctx);
    }

    private static void scheduleHeartbeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            HeartbeatRequestPacket requestPacket = new HeartbeatRequestPacket();
            ctx.writeAndFlush(requestPacket);
            scheduleHeartbeat(ctx);
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
