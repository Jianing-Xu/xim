package cn.xjn.xim.client;

import cn.xjn.xim.client.console.ConsoleCommandManager;
import cn.xjn.xim.client.console.LoginConsoleCommand;
import cn.xjn.xim.client.handler.HeartbeatResponseHandler;
import cn.xjn.xim.client.handler.IMClientHandler;
import cn.xjn.xim.codec.PacketCodecHandler;
import cn.xjn.xim.codec.Spliter;
import cn.xjn.xim.util.SessionManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author xjn
 * @date 2023-12-20
 */
@Slf4j
public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(IMClientHandler.INSTANCE);
                        ch.pipeline().addLast(new HeartbeatResponseHandler());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        long begin = System.currentTimeMillis();
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("The Netty client successfully connected, cost {}ms",
                                System.currentTimeMillis() - begin);
                        startConsoleThread(((ChannelFuture) future).channel());
                    } else {
                        int order = MAX_RETRY - retry + 1;
                        if (order > MAX_RETRY) {
                            log.error("The number of retries has been exhausted, the netty client connected failed.");
                            bootstrap.config().group().shutdownGracefully();
                            return;
                        }
                        int delay = 1 << order;
                        log.error("Connected failed, the client will make a {} attempt to reconnect after {} seconds.", order, delay);
                        bootstrap.config().group()
                                .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
                    }
                });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionManager.hasLogin(channel)) {
                    loginConsoleCommand.exec(sc, channel);
                } else {
                    log.info("Please enter command:");
                    consoleCommandManager.exec(sc, channel);
                }
            }
        }).start();
    }
}
