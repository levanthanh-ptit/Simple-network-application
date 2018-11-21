/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpmultithread.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class UDPMClient {

    DatagramSocket socket;
    byte[] buffArray = new byte[256];
    InetAddress ServerAddress;
    int ServerPort;
    int PacketTag = 0;

    public UDPMClient(int ServerPort) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        ServerAddress = InetAddress.getByName("localhost");
        this.ServerPort = ServerPort;
    }
    public UDPMClient(String host,int ServerPort) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        ServerAddress = InetAddress.getByName(host);
        this.ServerPort = ServerPort;
    }

    public boolean requestConnect(int packetCount) {
        byte[] bufRes = new byte[256];
        bufRes = String.valueOf(packetCount).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        try {
            this.socket.send(Res);
            return true;
        } catch (IOException ex) {
            return false;
        }
        

    }

    public String receive(byte[] buffArray) {
        DatagramPacket p = new DatagramPacket(buffArray, buffArray.length);
        try {
            socket.receive(p);
        } catch (IOException ex) {
            Logger.getLogger(UDPMClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(p.getData()).trim();
    }

    public void send(int num) throws IOException {
        byte[] bufRes = new byte[256];
        bufRes = (String.valueOf(PacketTag) + "#" + String.valueOf(num)).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        this.socket.send(Res);
        PacketTag++;
    }

    public void send(String data) throws IOException {
        byte[] bufRes = new byte[256];
        bufRes = (String.valueOf(PacketTag) + "#" + data).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        this.socket.send(Res);
        PacketTag++;

    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        UDPMClient client = new UDPMClient(8080);
        Scanner scan = new Scanner(System.in);
        /**
         * Application contents --------------------------------
         */
        if (client.requestConnect(scan.nextInt())) {
            try {

                client.send(scan.nextInt());
                client.send(scan.nextInt());
                System.out.println(client.receive(new byte[256]));

            } catch (IOException ex) {
                Logger.getLogger(UDPMClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("Server not found");
        }
        /**
         * Application contents --------------------------------
         */
    }

}
