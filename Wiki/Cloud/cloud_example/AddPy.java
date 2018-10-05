import py4j.GatewayServer;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

// javac -cp C:\Users\emiewag\* AddPy.java
// cd C:\Users\emiewag\
// java -cp ".;C:\Users\emiewag\*" AddPy


public class AddPy {

    public int addition(int first, int second) {
        return first + second;
    }

    public static void main(String[] args) {
        AddPy app = new AddPy();
        // app is now the gateway.entry_point

        //GatewayServer server = new GatewayServer(app,25334); // for using other port
        GatewayServer server = new GatewayServer(app);
        server.start();

        System.out.println("AddPy server is started");
    }

}
