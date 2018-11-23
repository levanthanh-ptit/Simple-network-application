package multithread.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPMServer extends Thread {

    Socket socket;
    
    public TCPMServer(Socket socket) {
        this.socket = socket;
    }
 
    public boolean checkEven(int num){
        return num%2==0?false:true;
    }
    public void run() {
        try {
            DataInputStream rec = new DataInputStream(socket.getInputStream());
            DataOutputStream send = new DataOutputStream(socket.getOutputStream());
            /**
             * Application contents --------------------------------
             */
            int a = rec.readInt();
            System.out.println(a);
//            int[] arr = new int[3];
//            int numCount = 0;
//            while (numCount<3) {                
//                int num = rec.readInt();
//                if(!checkEven(num)){
//                    send.writeUTF("sai");
//                }
//                else{
//                    send.writeUTF("dung");
//                    arr[numCount] = num;
//                    numCount++;
//                }
//            }
//            send.writeInt(arr[0]+arr[1]+arr[2]);
            /**
             * Application contents --------------------------------
             */
            rec.close();
            send.close();
            socket.close();
        } catch (Exception e) {
            Logger.getLogger(TCPMServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
