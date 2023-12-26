package cn.xjn.xim.server.handler;

import cn.xjn.xim.protocol.request.LoginRequestPacket;
import cn.xjn.xim.protocol.response.LoginResponsePacket;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class LoginRequestHandlerTest {

    @Test
    public void test_login() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoginRequestHandler());
        LoginRequestPacket source = new LoginRequestPacket();
        String username = UUID.randomUUID().toString();
        source.setUsername(username);
        source.setPassword("123456");

        embeddedChannel.writeInbound(source);
        LoginResponsePacket responsePacket = embeddedChannel.readOutbound();

        assertTrue(responsePacket.isSuccess());
        assertEquals(username, responsePacket.getUsername());
    }
}