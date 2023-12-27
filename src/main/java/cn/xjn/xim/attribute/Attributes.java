package cn.xjn.xim.attribute;

import io.netty.util.AttributeKey;

/**
 * @author xjn
 * @date 2023-12-27
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
