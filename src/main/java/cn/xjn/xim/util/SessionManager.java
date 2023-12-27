package cn.xjn.xim.util;

import cn.xjn.xim.attribute.Attributes;
import cn.xjn.xim.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xjn
 * @date 2023-12-26
 */
public class SessionManager {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }
}
