package cn.xjn.xim.bio;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author xjn
 * @date 2023-12-20
 */
public class BIOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    socket.getOutputStream().write((new Date() + ": hello bio").getBytes(StandardCharsets.UTF_8));
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
