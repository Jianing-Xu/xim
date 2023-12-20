package cn.xjn.xim.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xjn
 * @date 2023-12-20
 */
public class BIOServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        // 接受新的链接
        new Thread(() -> {
            while (true) {
                try {
                    // 阻塞获取链接
                    Socket socket = serverSocket.accept();
                    // 新起线程处理数据
                    new Thread(() -> {
                        int len;
                        byte[] data = new byte[1024];
                        try {
                            // 处理数据
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
