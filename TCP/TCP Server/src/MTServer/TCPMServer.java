package MTServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPMServer extends Thread {

    Socket socket;
    DataInputStream recive;
    DataOutputStream send;

    public TCPMServer(Socket socket) {
        try {
            this.socket = socket;
            recive = new DataInputStream(socket.getInputStream());
            send = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TCPMServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeThread() throws IOException{
        recive.close();
        send.close();
        socket.close();
    }

//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(4441);
//        while (true) {
//            Socket socket = serverSocket.accept();
//            TCPMServer _TCPMServer = new TCPMServer(socket);
//            _TCPMServer.start();
//        }
//    }
}
