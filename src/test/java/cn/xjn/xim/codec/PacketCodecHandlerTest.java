package cn.xjn.xim.codec;

import cn.xjn.xim.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class PacketCodecHandlerTest {

    @Test
    public void test_decode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new PacketDecoder());
        LoginRequestPacket sourcePacket = new LoginRequestPacket();
        String username = UUID.randomUUID().toString();
        sourcePacket.setUsername(username);
        sourcePacket.setPassword("123456");
        ByteBuf byteBuf = Unpooled.buffer();
        PacketCodec.INSTANCE.encode(byteBuf, sourcePacket);

        embeddedChannel.writeInbound(byteBuf);
        LoginRequestPacket target = embeddedChannel.readInbound();

        assertEquals(sourcePacket.getUsername(), target.getUsername());
        assertEquals(sourcePacket.getPassword(), target.getPassword());
    }

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