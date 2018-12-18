/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package STServer;

import EntityDB.DBContext;
import MTServer.TCPMServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class Sample extends TCPSServer {
    
    DBContext context;
    public Sample(Socket socket) {
        super(socket);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4441);
        Socket socket = serverSocket.accept();
        Sample sample = new Sample(socket);
        sample.start();
    }

    public void start() {
        try {
            
        } catch (Exception e) {
            Logger.getLogger(TCPMServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
