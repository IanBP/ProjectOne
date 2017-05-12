import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by christianbartram on 5/11/17.
 */
public class Server {

    public void listen()
    {
        try {
            ServerSocket ss = new ServerSocket(80);
            Socket cs = ss.accept();
            PrintWriter out =
                    new PrintWriter(cs.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));

        } catch(Exception e) {
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