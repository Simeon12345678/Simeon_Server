import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        // default port
        int port = 3000;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        // create the socket
        try {
            server = new ServerSocket(port);
        } catch (IOException ie) {
            System.out.println("Unable to open socket " + ie);
            System.exit(1);
        }

        // main loop waits for client data and replies
        while (true) {
            try {
                // wait for connection
                System.out.println("Waiting for connection");
                client = server.accept();

                System.out.println("Connection successful");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

                // read data
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));

                String msgFromClient = br.readLine();
                System.out.println("Message received " + msgFromClient);
                // format of 1+2 as a string means the operator is at index 1 which is what we check for
                int index = 1;


                // send response
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    // convert the given message into operators and numbers to do math with
                    char operator = msgFromClient.charAt(index);
                    int num1 = Character.getNumericValue(msgFromClient.charAt(0));
                    int num2 = Character.getNumericValue(msgFromClient.charAt(2));
                    int ans;

                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);

                    // select the operators and printout the answers
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

                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();
                    client.close();
                    break;
                }
            } catch (IOException ie) {
                System.out.println("Error in communicating with the client " + ie);
            }
        }
    }
}