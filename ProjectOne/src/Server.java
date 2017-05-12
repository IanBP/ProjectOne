import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by christianbartram on 5/11/17.
 */
class Server {


    public static void main(String[] args)
    {

        ServerSocket service = null;
        Socket connection = null;
        DataOutputStream out = null;
        DataInputStream in = null;

        try {
            //creating a server socket
            service = new ServerSocket(43594);

            // Wait for connection
            System.out.println("[Server] Waiting for connection...");

            connection = service.accept();

            System.out.println("[Server] Connection received from " + connection.getInetAddress().getHostName());

            //Get streams from client
            out = new DataOutputStream(connection.getOutputStream());
            in = new DataInputStream(connection.getInputStream());

            try {

                //Here we read the buffer from the client
                switch(in.read()) {
                    case 1:
                        System.out.println("[Server] Performing action one!");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                }

            } catch(Exception e){
                System.err.println("Data received in unknown format");
                e.printStackTrace();
            }

        } catch(IOException ioException) {
            ioException.printStackTrace();
        } finally {
            //Closing connection
            try{
                System.out.println("[Server] Closing Connection...");
                in.close();
                out.close();
                service.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
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