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


/**
 *
 * @author ThanhLeVan
 */
public class UDPMServer extends Thread {

    DatagramSocket socket;
    byte[] buffArray = new byte[256];
    InetAddress ClientAddress;
    int ClientPort;
    
    
    public UDPMServer(DatagramSocket socket) {
        this.socket = socket;
    }
    
    public DatagramPacket newDatagramPacket(){
        return new DatagramPacket(buffArray, buffArray.length);
    }
    public void receive(DatagramPacket p) throws IOException{
        socket.receive(p);
    }
    public void run() {
        
    }
}
