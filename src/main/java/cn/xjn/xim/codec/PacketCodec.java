package cn.xjn.xim.codec;

import cn.xjn.xim.protocol.LoginRequestPacket;
import cn.xjn.xim.protocol.Packet;
import cn.xjn.xim.protocol.command.Command;
import cn.xjn.xim.serialize.Serializer;
import cn.xjn.xim.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class PacketCodec {

    private static final int MAGIC_NUMBER = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    public PacketCodec() {
        this.packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        this.serializerMap = new HashMap<>();
        JSONSerializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializerAlgorithm(), jsonSerializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. serialize java object
        byte[] content = Serializer.DEFAULT.serialize(packet);

        // 2. the actual coding process
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
    }

    public Packet decode(ByteBuf byteBuf) {
        // skip magic number
        byteBuf.skipBytes(4);
        // skip version
        byteBuf.skipBytes(1);
        // serialize algorithm
        byte serializeAlgorithm = byteBuf.readByte();
        // command
        byte command = byteBuf.readByte();
        // packet length
        int length = byteBuf.readInt();
        byte[] content = new byte[length];
        byteBuf.readBytes(content);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(content, requestType);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return this.serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
