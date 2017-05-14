import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by christianbartram on 5/11/17.
 */
class Server {


    public static void main(String[] args) {

        ServerSocket service = null;
        Socket connection = null;
        PrintStream out = null;
        DataInputStream in = null;
        boolean shutdown = false;

        try {
            //creating a server socket
            service = new ServerSocket(8000);

            // Wait for connection
            System.out.println("[Server] Waiting for connection...");

            while (true) {

                connection = service.accept();

                System.out.println("[Server] Connection received from " + connection.getInetAddress().getHostName());

                //Get streams from client
                out = new PrintStream(connection.getOutputStream());
                in = new DataInputStream(connection.getInputStream());


                handleClientConnection(in, out, service, connection, shutdown);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void handleClientConnection(DataInputStream in, PrintStream out, ServerSocket service, Socket connection, boolean shutdown)
    {
        try {

            int data = in.read();

            System.out.println("[Server] Reading data from client: " + data);

            //Here we read the buffer from the client
            switch(data) {
                case 1:
                    System.out.println("[Server] Finding Current Date and Time from " + service.getInetAddress().getHostName());

                    //Run a command read its output and respond to the clients request
                    clientResponse(out, "Hosts current date and time is " + readCommandOutput(runCommand("date")));

                    break;
                case 2:
                    System.out.println("[Server] Received Request for Finding Host Uptime from " + service.getInetAddress().getHostName());

                    clientResponse(out, "Hosts current uptime is " + readCommandOutput(runCommand("uptime")));

                    break;
                case 3:
                    System.out.println("[Server] Received Request for Host Memory Use " + service.getInetAddress().getHostName());

                    clientResponse(out, "Hosts current memory use is " + readCommandOutput(runCommand("free")));

                    break;
                case 4:
                    System.out.println("[Server] Received Request for Host Network Statistics from " + service.getInetAddress().getHostName());

                    clientResponse(out, "Host NetStat is " + readCommandOutput(runCommand("netstat -s")));

                    break;
                case 5:
                    System.out.println("[Server] Received Request for Current Users from " + service.getInetAddress().getHostName());

                    clientResponse(out, "Hosts current user " + readCommandOutput(runCommand("who")));

                    break;
                case 6:
                    System.out.println("[Server] Received Request for Currently Running Processes from " + service.getInetAddress().getHostName());

                    clientResponse(out, "Host currently running processes are " + readCommandOutput(runCommand("ps -c")));

                    break;
                case 7:
                    System.out.println("[Server] Received Request to shutdown...");

                    shutdown = true;

                default:
                    System.err.println("[Server] Input from client does not match expected values");

            }

        } catch(Exception e) {
            System.err.println("Data received in unknown format");
            e.printStackTrace();
        } finally {

            //Closing connection
            try {
                if(shutdown) {
                    System.out.println("[Server] Closing Connection...");
                    in.close();
                    out.close();
                    service.close();
                    connection.close();
                }
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }


    /**
     * Sends a response back to the client through a data output stream
     * @param outputStream PrintStream output stream which we write too
     *
     */
    private static void clientResponse(PrintStream outputStream, String responseText)
    {
        try {
            outputStream.println("[Server] Response: " + responseText);

            //Give a response code so the client knows when to stop reading
            outputStream.println("[Server] [500] OK");

            outputStream.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a commands output and returns it as a string
     * @param process Process object
     * @return String command output
     */
    private static String readCommandOutput(Process process)
    {
        String s;
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //Read the command response
            while ((s = stdInput.readLine()) != null) {
                sb.append(s);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * Wrapper for Runtime Exec command
     * which executes a command on a linux or windows terminal
     *
     * @return Process returns a process object
     */
    private static Process runCommand(String command)
    {
        try {
            //Get the runtime object and execute the command
            return Runtime.getRuntime().exec(command);
        } catch(IOException e) {
            e.printStackTrace();
        }

        //Something went wrong
        return null;
    }
}
