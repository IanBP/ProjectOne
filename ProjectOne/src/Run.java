/**
 * Created by christianbartram on 5/11/17.
 */
public class Run {
    public static void main(String[] args) {
        new Server().listen();
        new Client().request();
    }
}
