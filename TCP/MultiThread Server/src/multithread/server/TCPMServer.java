
package multithread.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPMServer extends Thread {

    Socket socket;
    static boolean flag = false;

    public TCPMServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream rec = new DataInputStream(socket.getInputStream());
            DataOutputStream send = new DataOutputStream(socket.getOutputStream());
/** 
 * --------------------------------
 *      Application contents      
 * --------------------------------
 */
            

/**
 * --------------------------------
 *      Application contents      
 * --------------------------------
 */
            rec.close();
            send.close();
            socket.close();
        } catch (Exception e) {
            Logger.getLogger(TCPMServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
