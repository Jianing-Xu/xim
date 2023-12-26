package cn.xjn.xim.protocol;

import cn.xjn.xim.protocol.command.Command;
import lombok.Data;

/**
 * @author xjn
 * @date 2023-12-26
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
