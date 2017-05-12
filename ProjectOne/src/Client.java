import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by christianbartram on 5/11/17.
 */
class Client {

    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;

     void request()
    {
        try {
            System.out.println("[Client] Making connection request...");
            //1. creating a socket to connect to the server
            requestSocket = new Socket("127.0.0.1", 43594);

            System.out.println("[Client] Connected to localhost in port 2004");

            //Get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(requestSocket.getInputStream());

            System.out.println("[Client] Attempting to send a message to server...");
            //Communicating with the server
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("[Server] " + message);

                    sendMessage("Hi my server");

                    message = "bye";

                    sendMessage(message);
                } catch(ClassNotFoundException e){
                    System.err.println("data received in unknown format");
                    e.printStackTrace();
                }
            } while(!message.equals("bye"));

        } catch(UnknownHostException unknownHost) {

            System.err.println("You are trying to connect to an unknown host!");
            unknownHost.printStackTrace();

        } catch(IOException ioException) {

            ioException.printStackTrace();

        } finally{

            //Closing connection
            try{

                System.out.println("[Client] Closing Connection to host");
                in.close();
                out.close();
                requestSocket.close();

            } catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
            System.out.println("[Client] " + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

}
