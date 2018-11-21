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
public class UDPMultiThreadClient {

    DatagramSocket socket;
    byte[] buffArray = new byte[256];
    InetAddress ServerAddress;
    int ServerPort;
    int PacketTag = 0;

    public UDPMultiThreadClient(int ServerPort) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        ServerAddress = InetAddress.getByName("localhost");
        this.ServerPort = ServerPort;
    }

    public String receive(byte[] buffArray) throws IOException {
        DatagramPacket p = new DatagramPacket(buffArray, buffArray.length);
        socket.receive(p);
        return new String(p.getData()).trim();
    }

    public void send(int num) {
        byte[] bufRes = new byte[256];
        bufRes = (String.valueOf(PacketTag) + "#" + String.valueOf(num)).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        try {
            this.socket.send(Res);
            PacketTag++;
        } catch (IOException ex) {
            Logger.getLogger(UDPMultiThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(String data) {
        byte[] bufRes = new byte[256];

        bufRes = (String.valueOf(PacketTag) + "#" + data).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ServerAddress, ServerPort);
        try {
            this.socket.send(Res);
            PacketTag ++;
        } catch (IOException ex) {
            Logger.getLogger(UDPMultiThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        UDPMultiThreadClient client = new UDPMultiThreadClient(8080);
        Scanner scan = new Scanner(System.in);

    }

}
