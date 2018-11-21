/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithread.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class UDPMServer extends Thread {

    DatagramSocket socket;
    byte[] buffArray = new byte[256];
    InetAddress ClientAddress;
    int ClientPort;
    int DataReceivedCount = 0;

    String[] dataPool;

    public UDPMServer(DatagramSocket socket) {
        this.socket = socket;
    }

    public boolean acceptConnect() {

        try {
            DatagramPacket p = new DatagramPacket(buffArray, buffArray.length);
            socket.receive(p);
            int UpComingPacketCount = Integer.parseInt(new String(p.getData()).trim());
            dataPool = new String[UpComingPacketCount];
            ClientAddress = p.getAddress();
            ClientPort = p.getPort();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UDPMServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void receive() throws IOException {
        DatagramPacket p = new DatagramPacket(buffArray, buffArray.length);
        socket.receive(p);
        String[] sp = new String(p.getData()).trim().split("#");
        dataPool[Integer.parseInt(sp[0])] = sp[1];
        DataReceivedCount++;
    }

    public void send(int num) throws IOException {
        byte[] bufRes = new byte[256];
        bufRes = String.valueOf(num).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ClientAddress, ClientPort);
        this.socket.send(Res);
    }

    public void send(String data) throws IOException {
        byte[] bufRes = new byte[256];
        bufRes = data.getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ClientAddress, ClientPort);
        this.socket.send(Res);
    }

    public void run() {

        /**
         * Application contents --------------------------------
         */
        try {
            System.out.println("Client connected");
            this.receive();
            System.out.println(dataPool[0]);
            this.receive();
            System.out.println(dataPool[1]);
            this.send(Integer.parseInt(dataPool[0]) - Integer.parseInt(dataPool[1]));

        } catch (IOException ex) {
            Logger.getLogger(UDPMServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Application contents --------------------------------
         */
        socket.close();
    }
}
