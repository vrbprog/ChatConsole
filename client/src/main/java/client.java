import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private static final String IP = "localhost";
    private static final int PORT = 8189;

    public static void main(String[] args) {

        try (Socket socket = new Socket(IP, PORT)){
            System.out.println("Connected to server");
            try(Scanner sc = new Scanner(socket.getInputStream())) {
                String s;

                Thread sendMes = new Thread(() -> {
                        String st;
                    try (Scanner read = new Scanner(System.in);
                         PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {
                        while (true) {
                            st = read.nextLine();
                            out.println(st);
                            if(st.equals("/end")){
                                System.exit(0);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                sendMes.start();

                while (true) {
                    s = sc.nextLine();

                    System.out.println("Server: " + s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
