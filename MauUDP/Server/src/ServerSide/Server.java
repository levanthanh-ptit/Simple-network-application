/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void showDataPacketInfo(DatagramPacket packet){
        System.out.println("----------------------------");
        System.out.println("From: "+ packet.getAddress()+":"+packet.getPort());
        System.out.println(new String(packet.getData()));      
        System.out.println("----------------------------");
    }
    public static void main(String[] args) {
        DatagramSocket socket;
        int dataCount = 0;
        int num[];
        InetAddress address;
        int port;

        try {
            socket = new DatagramSocket(4445);
            System.out.println("Server started");
            // get connect request
            byte[] buffArray = new byte[256];
            DatagramPacket packetLength = new DatagramPacket(buffArray, buffArray.length);
            socket.receive(packetLength);
            System.out.println("Packet redcived");
            showDataPacketInfo(packetLength);
            dataCount = Integer.parseInt(new String(packetLength.getData()).trim());
            num = new int[dataCount]; 
            //save client info
            address = packetLength.getAddress();
            port = packetLength.getPort();
            //recive datagram num1
            DatagramPacket packet1 = new DatagramPacket(buffArray, buffArray.length);
            socket.receive(packet1);
            showDataPacketInfo(packet1);
            String[] sp1 = new String(packet1.getData()).trim().split("#");
            num[Integer.parseInt(sp1[0])] = Integer.parseInt(sp1[1]);

            //recive datagram num2
            DatagramPacket packet2 = new DatagramPacket(buffArray, buffArray.length);
            socket.receive(packet2);
            showDataPacketInfo(packet2);
            String[] sp2 = new String(packet2.getData()).trim().split("#");
            num[Integer.parseInt(sp2[0])] = Integer.parseInt(sp2[1]);

            //caculate 1
            int valueToReturn = num[0] + num[1];
            byte[] bufRes1 = new byte[256];
            bufRes1 = String.valueOf(valueToReturn).getBytes();
            //Response 1
            DatagramPacket Res1 = new DatagramPacket(bufRes1, bufRes1.length, address, port);
            socket.send(Res1);

            //caculate 2
            valueToReturn = num[0] - num[1];
            byte[] bufRes2 = new byte[256];
            bufRes2 = String.valueOf(valueToReturn).getBytes();
            //Response 1
            DatagramPacket Res2 = new DatagramPacket(bufRes2, bufRes2.length, address, port);
            socket.send(Res2);
            socket.close();
        } catch (IOException | NumberFormatException e) {
        }
        
    }
}
