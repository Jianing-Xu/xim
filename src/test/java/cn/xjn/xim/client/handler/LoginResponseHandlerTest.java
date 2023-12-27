package cn.xjn.xim.client.handler;

import cn.xjn.xim.protocol.response.LoginResponsePacket;
import cn.xjn.xim.util.IDUtil;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

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
        String userId = IDUtil.genRandomId();
        String username = IDUtil.genRandomId();
        packet.setSuccess(true);
        packet.setUserId(userId);
        packet.setUsername(username);

        embeddedChannel.writeInbound(packet);
        assertNull(embeddedChannel.readOutbound());
    }

    @Test
    public void test_loginFailedResponse() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoginResponseHandler());
        LoginResponsePacket packet = new LoginResponsePacket();
        String userId = IDUtil.genRandomId();
        String username = IDUtil.genRandomId();
        packet.setSuccess(false);
        packet.setUsername(username);
        packet.setReason("Authentication failed");

        embeddedChannel.writeInbound(packet);
        assertNull(embeddedChannel.readOutbound());
    }
}