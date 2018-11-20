
package multithread.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Runner {

    public static void main(String[] args) throws IOException {
         ServerSocket serverSocket = new ServerSocket(8080);
        while(true){
            Socket socket = serverSocket.accept();
            Server b1_Server = new Server(socket);
            b1_Server.start();
        }
    }
    
}
