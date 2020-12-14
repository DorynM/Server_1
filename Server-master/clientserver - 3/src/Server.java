import com.sun.security.ntlm.Client;
import jdk.nashorn.internal.IntDeque;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class Server {
    public static HashMap<Integer, Socket> clientInfo = new HashMap<>();
    public static final int PORT = 8000;
    public static int ClientId = 1020;
    public static Stack TaskQueue = new Stack();
    public static Stack ResultQeue = new Stack();
    private static int count = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);

        Thread myThready = new Thread(new Runnable() {
            public void run() {
                while (true){
                    Socket socket = null;
                    try {
                        socket = server.accept();
                        clientInfo.put(ClientId, socket);
                        BufferedReader in;
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String inputLine = null;
                        try {
                            if ((inputLine = in.readLine()) != null) {
                                System.out.println("input: " + inputLine);
                                String[] ops = inputLine.split(" ");
                                int a = Integer.parseInt(ops[0]);
                                int b = Integer.parseInt(ops[2]);
                                int result = 0;
                                Task value = new Task(ClientId, ++count, ops[1], a, b, socket);
                                ClientId++;
                                TaskQueue.push(value);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myThready.start();

        Thread myThready2 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    while (!TaskQueue.empty()) {
                        int result = 0;
                        Task Clinet = (Task) TaskQueue.pop();

                        switch (Clinet.GetOperator()) {
                            case "+":
                                result = Clinet.GetFirst() + Clinet.GetSecond();
                                break;
                            case "-":
                                result = Clinet.GetFirst() - Clinet.GetSecond();
                                break;
                            case "*":
                                result = Clinet.GetFirst() * Clinet.GetSecond();
                                break;
                            case "/":
                                result = Clinet.GetFirst() / Clinet.GetSecond();
                                break;
                        }
                        Clinet.SetResult(result);
                        ResultQeue.push(Clinet);
                    }
                }
            }
        });

        myThready2.start();

        Thread myThready3 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (!ResultQeue.isEmpty()) {
                        Task val = new Task();
                        val = (Task) ResultQeue.pop();
                        Socket socket1 = val.GetSocket();
                        PrintWriter out;
                        try {
                            out = new PrintWriter(socket1.getOutputStream(), true);
                            out.println(val.GetResult());
                            socket1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        myThready3.start();
    }
}