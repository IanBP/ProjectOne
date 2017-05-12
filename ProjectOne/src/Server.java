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
        PrintStream out = null;
        DataInputStream in = null;

        try {
            //creating a server socket
            service = new ServerSocket(43594);

            // Wait for connection
            System.out.println("[Server] Waiting for connection...");

            connection = service.accept();

            System.out.println("[Server] Connection received from " + connection.getInetAddress().getHostName());

            //Get streams from client
            out = new PrintStream(connection.getOutputStream());
            in = new DataInputStream(connection.getInputStream());

            try {

                //Here we read the buffer from the client
                switch(in.read()) {
                    case 1:
                        System.out.println("[Server] Finding Current Date and Time");

                        //Run a command read its output and respond to the clients request
                        clientResponse(out, "Hosts current date and time is " + readCommandOutput(runCommand("date")), true);

                        break;
                    case 2:
                        System.out.println("[Server] Finding Host Uptime");

                        clientResponse(out, "Hosts current uptime is " + readCommandOutput(runCommand("uptime")), true);

                        break;
                    case 3:
                        System.out.println("[Server] Finding Host Memory Use");

                        clientResponse(out, "Hosts current memory use is " + readCommandOutput(runCommand("free")), true);

                        break;
                    case 4:
                        System.out.println("[Server] Host Network Statistics");

                        clientResponse(out, "Host NetStat is " + readCommandOutput(runCommand("netstat -s")), true);

                        break;
                    case 5:
                        System.out.println("[Server] Finding Current Users");

                        clientResponse(out, "Hosts current user " + readCommandOutput(runCommand("who")), true);

                        break;
                    case 6:
                        System.out.println("[Server] Currently Running Processes");

                        clientResponse(out, "Host currently running processes are " + readCommandOutput(runCommand("ps -c")), true);

                        break;
                        //todo add a default? in case unexpected data comes from the client
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

    /**
     * Sends a response back to the client through a data output stream
     * @param outputStream PrintStream output stream which we write too
     * @param successfulResponse Boolean true if the program should output a successful response code to stop the client
     * from reading futher false otherwise
     */
    private static void clientResponse(PrintStream outputStream, String responseText, boolean successfulResponse)
    {
        try {

            outputStream.println("[Server] Response: " + responseText);

            if(successfulResponse) {
                //Give a response code so the client knows when to stop reading
                outputStream.println("[Server] [500] OK");
            }

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
            return Runtime.getRuntime().exec(command);
        } catch(IOException e) {
            e.printStackTrace();
        }
        //Something went wrong
        return null;
    }
}




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