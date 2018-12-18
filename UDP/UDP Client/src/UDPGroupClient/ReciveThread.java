/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPGroupClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class ReciveThread extends Thread {

    MulticastSocket socket;
    int PacketTag = 0;
    int PacketSize = 256;
    boolean live = true;

    public ReciveThread(MulticastSocket socket) {
        this.socket = socket;
    }

    public byte[] getBuffPacket() {
        return new byte[this.PacketSize];
    }

    public String receive() {
        DatagramPacket p = new DatagramPacket(getBuffPacket(), this.PacketSize);
        try {
            socket.receive(p);
        } catch (IOException ex) {
            Logger.getLogger(ReciveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(p.getData()).trim();
    }
    public void close(){
        this.live = false;
    }
    @Override
    public void run() {
        while (live) {
            /**
             * Application contents --------------------------------
             */
            System.out.println("==>"+this.receive());
            /**
             * Application contents --------------------------------
             */
        }
    }
}
