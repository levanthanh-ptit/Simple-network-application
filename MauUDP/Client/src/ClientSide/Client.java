/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        Scanner scan = new Scanner(System.in);
        InetAddress address = InetAddress.getByName("localhost");
        // get a datagram socket
         int packetLength = scan.nextInt();
        // send request
        byte[] buf0 = new byte[256];
        buf0 = String.valueOf(packetLength).getBytes();
        
        DatagramPacket packetCount = new DatagramPacket(buf0, buf0.length, address, 4445);
        socket.send(packetCount);
        
        int num1 = scan.nextInt();
        // send request number 1
        byte[] buf1 = new byte[256];
        buf1 = "0#".concat(String.valueOf(num1)).getBytes();
        DatagramPacket packet1 = new DatagramPacket(buf1, buf1.length, address, 4445);
        socket.send(packet1);

        int num2 = scan.nextInt();
        // send request number 2
        byte[] buf2 = new byte[256];
        buf2 = "1#".concat(String.valueOf(num2)).getBytes();
        DatagramPacket packet2 = new DatagramPacket(buf2, buf2.length, address, 4445);
        socket.send(packet2);

        // get response
        byte[] bufRes1 = new byte[256];
        DatagramPacket packetToRecive = new DatagramPacket(bufRes1, bufRes1.length);
        socket.receive(packetToRecive);

        // display response
        String received = new String(packetToRecive.getData(), 0, packetToRecive.getLength());
        System.out.println("Value from server: " + received);
        
        // get response
        byte[] bufRes2 = new byte[256];
        DatagramPacket packetToRecive2 = new DatagramPacket(bufRes2, bufRes2.length);
        socket.receive(packetToRecive2);

        // display response
        String received2 = new String(packetToRecive2.getData(), 0, packetToRecive2.getLength());
        System.out.println("Value from server: " + received2);

        socket.close();
    }
}
