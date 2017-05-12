import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by christianbartram on 5/11/17.
 */
class Server {
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    void listen()
    {
        try {
            //creating a server socket
            providerSocket = new ServerSocket(43594);

            // Wait for connection
            System.out.println("[Server] Waiting for connection...");

            new Client().request();

            connection = providerSocket.accept();

            System.out.println("[Server] Connection received from " + connection.getInetAddress().getHostName());

            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());

            sendMessage("Connection successful");

            do {
                try {

                    message = (String) in.readObject();

                    System.out.println("[Client] " + message);

                    if (message.equals("bye")) {
                        sendMessage("bye");
                    }

                } catch(ClassNotFoundException e){
                    System.err.println("Data received in unknown format");
                    e.printStackTrace();
                }

            } while(!message.equals("bye"));

        } catch(IOException ioException) {
            ioException.printStackTrace();
        } finally {
            //Closing connection
            try{
                System.out.println("[Server] Closing Connection...");
                in.close();
                out.close();
                providerSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(String msg)
    {
        try {

            out.writeObject(msg);
            out.flush();
            System.out.println("[Server] " + msg);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}

//Menu is on the client side
/*

for(int i = 0 i < 10 i++ {

new Thread();

}

for(int i = 0; i < 10; i++)
{
 thread[i].start();
}

startTime;
thread.start.

 must have separate time measurements for each thread

thread.stop;
stopTime;

response time = startTime - stopTime
 */
//date
//uptime
//free
//netstat
//who
//ps -c
//ps -aux