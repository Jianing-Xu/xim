package cn.xjn.xim.protocol.command;

/**
 * @author xjn
 * @date 2023-12-26
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;


}
