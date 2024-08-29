import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;



class Server 
{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;


    //constr
    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server is ready to accept Connection on port " + port);
            System.out.println("waiting...");
            socket = server.accept();
    
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
    
            startReading();
            startWriting();
    
        } catch (BindException be) {
            System.out.println("Port " + port + " is already in use or you don't have permission to use it.");
            System.out.println("Try a different port number above 1024 or run the application with admin privileges.");
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
                        System.out.println("client terminated the chat");
                        break;
                    }

                    System.out.println("Client :"+msg);
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



    public static void main(String[] args) 
    {
        System.out.println("this is Server.. Sarting the Server");
        new Server(7777);

    }
}