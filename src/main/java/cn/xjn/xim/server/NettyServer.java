package cn.xjn.xim.server;

import cn.xjn.xim.codec.PacketCodecHandler;
import cn.xjn.xim.codec.Spliter;
import cn.xjn.xim.handler.IMIdleStateHandler;
import cn.xjn.xim.server.handler.AuthHandler;
import cn.xjn.xim.server.handler.HeartbeatRequestHandler;
import cn.xjn.xim.server.handler.IMServerHandler;
import cn.xjn.xim.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xjn
 * @date 2023-12-20
 */
@Slf4j
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartbeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMServerHandler.INSTANCE);
                    }
                });

        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        long begin = System.currentTimeMillis();
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("The Netty server started up successfully, cost {}ms.",
                        System.currentTimeMillis() - begin);
            } else {
                log.error("The Netty server bind port[{}] failed", port);
            }
        });
    }
}
