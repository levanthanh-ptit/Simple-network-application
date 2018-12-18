/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;

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
class UDPClient {

    DatagramSocket socket;
    InetAddress ServerAddress;
    int ServerPort;
    int PacketTag = 0;
    int PacketSize = 256;

    public UDPClient(int ServerPort) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        ServerAddress = InetAddress.getByName("localhost");
        this.ServerPort = ServerPort;
    }

    public byte[] getBuffPacket() {
        return new byte[this.PacketSize];
    }

    public UDPClient(String host, int ServerPort) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        ServerAddress = InetAddress.getByName(host);
        this.ServerPort = ServerPort;
    }

    public boolean requestConnect(int packetCount, int packetSize) {
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

    public String receive() {
        DatagramPacket p = new DatagramPacket(getBuffPacket(), this.PacketSize);
        try {
            socket.receive(p);
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(p.getData()).trim();
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

    public void failedLastSend() {
        this.PacketTag--;
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        Scanner scan = new Scanner(System.in);
        /**
         * Application contents --------------------------------
         */
        UDPClient client = new UDPClient(4442);
        if (client.requestConnect(3, 1024)) {   //3 packets 1024 bytes
            try {

                int numCount = 0;
                while (numCount < 3) {
                    int a;
                    System.out.print("Nhap so: ");
                    a = scan.nextInt();
                    client.send(a);
                    String res = client.receive();
                    if (res.compareTo("sai") == 0) {
                        System.out.println("Nhap sai, xin nhap lai");
                        client.failedLastSend();
                    } else if (res.compareTo("dung") == 0) {
                        numCount++;
                    }
                }
                int sum = Integer.parseInt(client.receive());
                System.out.println("Tong 3 so la: " + sum);

            } catch (IOException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Server not found");
        }
        /**
         * Application contents --------------------------------
         */
    }

}
