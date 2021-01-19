import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class server {

    private static final int PORT = 8189;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server started.");
            try (Socket socket = server.accept()) {

                System.out.println("Client connected.");

                try (Scanner sc = new Scanner(socket.getInputStream());
                      ) {
                    Thread sendMes = new Thread(() -> {

                        try (Scanner read = new Scanner(System.in);
                             PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {
                            while (true) {
                                out.println(read.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    sendMes.setDaemon(true);
                    sendMes.start();

                    while (socket.isConnected()) {
                        try {
                            String s = sc.nextLine();
                            if (s.equals("/end")) {
                                System.out.println("Client disconnect");
                                break;
                            }
                            System.out.println("Client: " + s);
                        }catch (NoSuchElementException e){
                            System.out.println("Client dropped the connection");
                            //e.printStackTrace();
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
