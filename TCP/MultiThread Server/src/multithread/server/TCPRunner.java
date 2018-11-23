
package multithread.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPRunner {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        
            Socket socket = serverSocket.accept();
            TCPMServer _TCPMServer = new TCPMServer(socket);
            _TCPMServer.start();
        
    }
    
}
