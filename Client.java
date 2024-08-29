import java.io.*;
import java.net.Socket;

class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(int port) {
        try {
            System.out.println("Sending request to server on port " + port + "...");
            socket = new Socket("127.0.0.1", port);
            System.out.println("Connection established");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading () 
    {
        // thread-read
        Runnable r1 = ()-> {
            System.out.println("reader started..");
            while(true)
            {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit"))
                    {
                        System.out.println("server terminated the chat");
                        break;
                    }

                    System.out.println("Server :"+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }    
            }
        };

        new Thread(r1).start();

    }

    public void startWriting () 
    {
        //thread - send to client 
        Runnable r2 = () -> {
            System.out.println("writer started...");
            while (true) {
                try {

                    BufferedReader br1 = new BufferedReader(new 
                    InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();

    }


    public static void main(String[] args) {
        System.out.println("this is Client...");
        new Client(7777); 
    }
}