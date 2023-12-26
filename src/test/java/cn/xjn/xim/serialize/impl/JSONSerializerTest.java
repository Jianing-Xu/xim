package cn.xjn.xim.serialize.impl;

import cn.xjn.xim.protocol.request.LoginRequestPacket;
import cn.xjn.xim.serialize.SerializerAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class JSONSerializerTest {

    @Test
    public void test_getSerializerAlgorithm() {
        Assert.assertEquals(SerializerAlgorithm.JSON, new JSONSerializer().getSerializerAlgorithm());
    }

    @Test
    public void test_serialize_and_deserialize() {
        LoginRequestPacket source = new LoginRequestPacket();
        source.setUsername(UUID.randomUUID().toString());
        source.setPassword("123456");

        JSONSerializer serializer = new JSONSerializer();
        byte[] content = serializer.serialize(source);
        LoginRequestPacket target = serializer.deserialize(content, LoginRequestPacket.class);

        assertEquals(source.getUsername(), target.getUsername());
        assertEquals(source.getPassword(), target.getPassword());
    }
}