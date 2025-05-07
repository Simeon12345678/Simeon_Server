import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        int port = 3000;

        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        try {
            server = new ServerSocket(port);
        } catch (IOException ie) {
            System.out.println("Unable to open socket " + ie);
            System.exit(1);
        }

        while (true) {
            try {
                System.out.println("Waiting for connection");
                client = server.accept();
                System.out.println("Connection succesful");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host= " + clientHost + " Client port = " + clientPort);

                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));

                System.out.println("Write a SIMPLE math problem ie addition, multiplication, subtraction or division in the format of example 1+2");
                String msgFromClient = br.readLine();
                int index = 1;
                // format of 1+2 as a string means the operator is at index 1 which is what we check for

                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    char operator = msgFromClient.charAt(index);
                    int num1 = msgFromClient.charAt(0);
                    int num2 = msgFromClient.charAt(2);
                    int ans;

                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);

                    switch (operator) {
                        case '+':
                            ans = num1 + num2;
                            pw.println(msgFromClient + " is " + ans);
                            break;
                        case '-':
                            ans = num1 - num2;
                            pw.println(msgFromClient + " is " + ans);
                            break;
                        case '*':
                            ans = num1 * num2;
                            pw.println(msgFromClient + " is " + ans);
                            break;
                        case '/':
                            ans = num1 / num2;
                            pw.println(msgFromClient + " is " + ans);
                    }
                }
            } catch (IOException ie) {
                System.out.println("bad");
            }
        }
    }
}