package cn.xjn.xim.client.handler;

import cn.xjn.xim.protocol.response.LoginResponsePacket;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNull;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class LoginResponseHandlerTest {

    @Test
    public void test_loginSuccessResponse() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoginResponseHandler());
        LoginResponsePacket packet = new LoginResponsePacket();
        String username = UUID.randomUUID().toString();
        packet.setSuccess(true);
        packet.setUsername(username);

        embeddedChannel.writeInbound(packet);
        assertNull(embeddedChannel.readOutbound());
    }

    @Test
    public void test_loginFailedResponse() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoginResponseHandler());
        LoginResponsePacket packet = new LoginResponsePacket();
        String username = UUID.randomUUID().toString();
        packet.setSuccess(false);
        packet.setUsername(username);
        packet.setReason("Authentication failed");

        embeddedChannel.writeInbound(packet);
        assertNull(embeddedChannel.readOutbound());
    }
}