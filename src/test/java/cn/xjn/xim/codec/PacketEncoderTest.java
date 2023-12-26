package cn.xjn.xim.codec;

import cn.xjn.xim.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class PacketEncoderTest {

    @Test
    public void test_encode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new PacketEncoder());
        LoginRequestPacket source = new LoginRequestPacket();
        String username = UUID.randomUUID().toString();
        source.setUsername(username);
        source.setPassword("123456");
        embeddedChannel.writeOutbound(source);
        LoginRequestPacket target = (LoginRequestPacket) PacketCodec.INSTANCE.decode(embeddedChannel.readOutbound());

        assertEquals(source.getUsername(), target.getUsername());
        assertEquals(source.getPassword(), target.getPassword());
    }
}