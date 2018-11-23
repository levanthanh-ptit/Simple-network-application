package singlethread.tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {
    
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(4441);
            socket = serverSocket.accept();
            DataInputStream receive = new DataInputStream(socket.getInputStream());
            DataOutputStream send = new DataOutputStream(socket.getOutputStream());
            /**
             * Application contents --------------------------------
             */
         
             int[] arr = new int[3];
            int numCount = 0;
            while (numCount<3) {                
                int num = receive.readInt();
                if((num%2)!=0){
                    arr[numCount] = num;
                    numCount++;
                    send.writeUTF("dung");             
                }
                else{
                    send.writeUTF("sai");
                }
            }
            send.writeInt(arr[0]+arr[1]+arr[2]);
            /**
             * Application contents --------------------------------
             */
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
