package cn.xjn.xim.protocol.request;

import cn.xjn.xim.protocol.Packet;
import cn.xjn.xim.protocol.command.Command;
import lombok.Data;

/**
 * @author xjn
 * @date 2023-12-27
 */
@Data
public class GroupMessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public GroupMessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
