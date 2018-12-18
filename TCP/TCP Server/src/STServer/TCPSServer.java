package STServer;
import MTServer.TCPMServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPSServer {
   Socket socket;
    DataInputStream recive;
    DataOutputStream send;

    public TCPSServer(Socket socket) {
        try {
            this.socket = socket;
            recive = new DataInputStream(socket.getInputStream());
            send = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TCPMServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
