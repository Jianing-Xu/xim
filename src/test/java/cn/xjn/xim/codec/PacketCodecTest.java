package cn.xjn.xim.codec;

import cn.xjn.xim.protocol.Packet;
import cn.xjn.xim.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class PacketCodecTest {

    @Test
    public void test_encode_and_decode() {
        ByteBuf byteBuf = Unpooled.buffer(1024);
        LoginRequestPacket source = new LoginRequestPacket();
        source.setUsername(UUID.randomUUID().toString());
        source.setPassword("123456");

        PacketCodec.INSTANCE.encode(byteBuf, source);
        LoginRequestPacket target = (LoginRequestPacket) PacketCodec.INSTANCE.decode(byteBuf);

        assertEquals(source.getUsername(), target.getUsername());
        assertEquals(source.getPassword(), target.getPassword());
    }
}