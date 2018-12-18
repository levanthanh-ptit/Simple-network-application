/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPGroupClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class SendThread extends Thread {

    MulticastSocket socket;
    InetAddress ServerAddress;
    int ServerPort;
    int PacketTag = 0;
    int PacketSize = 256;
    boolean live = true;

    public SendThread(MulticastSocket socket, InetAddress ServerAddress, int ServerPort) {
        this.socket = socket;
        this.ServerAddress = ServerAddress;
        this.ServerPort = ServerPort;
    }

    public byte[] getBuffPacket() {
        return new byte[this.PacketSize];
    }

    public boolean requestConnect(int packetCount, int packetSize) {
        int PacketTag = 0;
        byte[] bufRes = getBuffPacket();
        String requestConnectString = "" + packetCount + "," + packetSize + "";
        bufRes = requestConnectString.getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        this.PacketSize = packetSize;
        try {
            this.socket.send(Res);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public void send(int num) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = (String.valueOf(PacketTag) + "`" + String.valueOf(num)).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        this.socket.send(Res);
        PacketTag++;
    }

    public void send(String data) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = (String.valueOf(PacketTag) + "`" + data).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        this.socket.send(Res);
        PacketTag++;
    }

    public String receive() {
        DatagramPacket p = new DatagramPacket(getBuffPacket(), this.PacketSize);
        try {
            socket.receive(p);
        } catch (IOException ex) {
            Logger.getLogger(UDPGroupClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(p.getData()).trim();
    }

    public void close() {
        this.live = false;
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        try {
            while (live) {
                /**
                 * Application contents --------------------------------
                 */
                String d = scan.nextLine();
                if(d.equals("EXIT"))
                {
                    this.live = false;
                    continue;
                }
                requestConnect(1, 1024);
                this.send(d);
                this.PacketTag = 0;
                /**
                 * Application contents --------------------------------
                 */
            }
        } catch (IOException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
