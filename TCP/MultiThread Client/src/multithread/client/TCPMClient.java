/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithread.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class TCPMClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 8080);
            DataInputStream rec = new DataInputStream(socket.getInputStream());
            DataOutputStream send = new DataOutputStream(socket.getOutputStream());
            /**
             * Application contents --------------------------------
             */
            send.write(scan.nextInt());
            send.write(scan.nextInt());
            int value =  rec.read();
            System.out.println(value);
            /**
             * Application contents --------------------------------
             */
            rec.close();
            send.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPMClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
