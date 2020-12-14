import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static HashMap<SocketChannel, Task> clientInfo = new HashMap<>();
    public static final int PORT = 8000;
    public static int ClientId = 1020;

    public static void main(String[] args) throws IOException {

        final ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(PORT));

        final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();

        while (true) {
            final SocketChannel socket = serverSocket.accept();

            if (socket != null) {
                socket.configureBlocking(false);
                sockets.put(socket, ByteBuffer.allocateDirect(256));
                Task value = new Task();
                value.SetSocket(socket);
                value.SetClientId(ClientId++);
                clientInfo.put(socket, value);
            }

            sockets.keySet().removeIf((socketChannel) -> !socketChannel.isOpen());

            sockets.forEach((socketCh, byteBuffer) -> {
                try {
                    int data = socketCh.read(byteBuffer);
                    if (data == -1) {
                        closeSocket(socketCh);
                    } else if (data != 0) {
                        byteBuffer.flip();
                        calculate(byteBuffer, socketCh);
                        while (byteBuffer.hasRemaining()) {
                            socketCh.write(byteBuffer);
                        }
                        byteBuffer.compact();

                    }
                } catch (IOException e) {
                    closeSocket(socketCh);
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    private static void calculate(ByteBuffer byteBuffer, SocketChannel socket) {
        StringBuilder str = new StringBuilder();
        for (int x = 0; x < byteBuffer.limit(); x++) {
            str.append((char)byteBuffer.get());
        }
        int k = byteBuffer.position();
        String s = str.toString();
        String[] str_arr = s.split(" ");
        str_arr[2] = str_arr[2].replaceAll("\n", "").replace("\r", "");
        int result = 0;

        Task value = clientInfo.get(socket);
        value.SetOperator(str_arr[1]);
        value.SetFirst(Integer.parseInt(str_arr[0]));
        value.SetSecond(Integer.parseInt(str_arr[2]));

        switch (str_arr[1]) {
            case "+":
                result = Integer.parseInt(str_arr[0]) + Integer.parseInt(str_arr[2]);
                break;
            case "-":
                result = Integer.parseInt(str_arr[0]) - Integer.parseInt(str_arr[2]);
                break;
            case "*":
                result = Integer.parseInt(str_arr[0]) * Integer.parseInt(str_arr[2]);
                break;
            case "/":
                result = Integer.parseInt(str_arr[0]) / Integer.parseInt(str_arr[2]);
                break;
        }
        System.out.println(str_arr[0] + str_arr[1] + str_arr[2]);
        value.SetResult(result);
        clientInfo.put(socket, value);
        ByteBuffer buf = null;
        String res = String.valueOf(result);
        System.out.println(res);
        k = k - res.length() - 1;
        System.out.println(k);
        byteBuffer.position(k);
        for(int i = 0; i < res.length(); i++)
        {
            byteBuffer.put((byte)res.charAt(i));
        }
        byteBuffer.position(k);
    }

    private static void closeSocket(final SocketChannel socket) {
        try {
            socket.close();
        } catch (IOException ignore) {

        }
    }
}
