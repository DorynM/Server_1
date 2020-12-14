import java.io.*;
import java.net.*;
import java.io.IOException;

public class EchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        int i = 1;
        while(i <= 2)
        {
            int finalI = i;
            Thread myThready = new Thread(new Runnable() {
                public void run() {
                    try {
                        Socket echoSocket = new Socket(hostName, portNumber);
                        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                        if (finalI % 2 == 0) {
                            out.println("3 - 201");
                        }
                        else {
                            out.println("3 + 3");
                        }
                        System.out.println("echo: " + in.readLine());
                        echoSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            myThready.start();
            i++;
        }
    }
}
