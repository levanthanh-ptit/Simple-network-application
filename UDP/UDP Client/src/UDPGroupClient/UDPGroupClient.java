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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class UDPGroupClient {

    MulticastSocket socket;
    InetAddress GroupServerAddress;
    InetAddress ServerAddress;
    int ServerPort;
    int PacketTag = 0;
    int PacketSize = 256;

    public byte[] getBuffPacket() {
        return new byte[this.PacketSize];
    }

    public UDPGroupClient(String groupHost, int ServerPort) throws UnknownHostException, IOException {
        socket = new MulticastSocket(4448);
        GroupServerAddress = InetAddress.getByName(groupHost);
        ServerAddress = InetAddress.getLocalHost();
        socket.joinGroup(GroupServerAddress);
        this.ServerPort = ServerPort;
    }

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        Scanner scan = new Scanner(System.in);
        /**
         * Application contents --------------------------------
         */
        UDPGroupClient client = new UDPGroupClient("230.0.0.55",4448);
        ReciveThread reciveThread = new ReciveThread(client.socket);
        SendThread sendThread = new SendThread(client.socket, client.ServerAddress, client.ServerPort);
        //3 packets 1024 bytes
        reciveThread.start();
        sendThread.start();
        while (sendThread.live) {
        }
        reciveThread.live = false;
        client.socket.leaveGroup(client.GroupServerAddress);
        /**
         * Application contents --------------------------------
         */
    }
}
