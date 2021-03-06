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

    public static Socket socket;
    public static DataInputStream receive;
    public static DataOutputStream send;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            socket = new Socket(InetAddress.getByName("localhost"), 4441);
            receive = new DataInputStream(socket.getInputStream());
            send = new DataOutputStream(socket.getOutputStream());
            /**
             * Application contents --------------------------------
             */
            int numCount = 0;
            while (numCount < 3) {
                int a;
                System.out.print("Nhap so: ");
                a = scan.nextInt();
                send.writeInt(a);
                String res = receive.readUTF();
                if (res.compareTo("sai") == 0) {
                    System.out.println("Nhap sai, xin nhap lai");
                } else if (res.compareTo("dung") == 0) {
                    numCount++;
                }
            }
            int sum = receive.readInt();
            System.out.println("Tong 3 so la: " + sum);
            /**
             * Application contents --------------------------------
             */
            receive.close();
            send.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPMClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
