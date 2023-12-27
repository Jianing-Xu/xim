package cn.xjn.xim.protocol.request;

import cn.xjn.xim.protocol.Packet;
import cn.xjn.xim.protocol.command.Command;
import lombok.Data;

/**
 * @author xjn
 * @date 2023-12-26
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
