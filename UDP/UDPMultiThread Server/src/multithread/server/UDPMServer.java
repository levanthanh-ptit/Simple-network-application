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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class UDPMServer extends Thread {

    DatagramSocket socket;
    InetAddress ClientAddress;
    int ClientPort;
    int DataReceivedCount = 0;
    int PacketSize = 256;
    String[] dataPool;

    public UDPMServer(DatagramSocket socket) {
        this.socket = socket;
    }
    public byte[] getBuffPacket(){
        return new byte[this.PacketSize];
    }
    public void showClientInfo(){
        System.out.println(this.ClientAddress.toString());
        System.out.println("ClientPort: "+this.ClientPort);
        System.out.println("PacketSize: "+this.PacketSize+" Byte");
        System.out.println("Data Pool size: "+this.dataPool.length);
    }
    public boolean acceptConnect() {
        DatagramPacket p = new DatagramPacket(this.getBuffPacket(), this.PacketSize);
        try {         
            socket.receive(p);
            String ex = new String(p.getData()).trim();
            String[] DPSizeAndPacketSize = new String(p.getData()).trim().split(",");
             int UpComingPacketCount = Integer.parseInt(DPSizeAndPacketSize[0]);
            this.PacketSize = Integer.parseInt(DPSizeAndPacketSize[1]);           
            this.dataPool = new String[UpComingPacketCount];
            this.ClientAddress = p.getAddress();
            this.ClientPort = p.getPort();
            Calendar currentTime = Calendar.getInstance();
            System.out.println("Accept connect at: " +new SimpleDateFormat("HH:mm:ss").format(currentTime.getTime()));
            showClientInfo();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UDPMServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int receive() throws IOException {
        DatagramPacket p = new DatagramPacket(this.getBuffPacket(), this.PacketSize);
        socket.receive(p);
        String[] sp = new String(p.getData()).trim().split("#");
        dataPool[Integer.parseInt(sp[0])] = sp[1];
        DataReceivedCount++;
        return Integer.parseInt(sp[0]);
    }

    public void send(int num) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = String.valueOf(num).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ClientAddress, ClientPort);
        this.socket.send(Res);
    }

    public void send(String data) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = data.getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, ClientAddress, ClientPort);
        this.socket.send(Res);
    }

    public void run() {

        /**
         * Application contents --------------------------------
         */
        try {
            int[] arr = new int[3];
            int numCount = 0;
            while (numCount < 3) {
                int i = receive();
                int num = Integer.parseInt(dataPool[i]);
                if ((num % 2) == 0) {
                    send("sai");
                    DataReceivedCount--;
                } else {
                    numCount++;
                    send("dung");
                }
            }
            for (int i = 0; i < this.dataPool.length; i++) {
                arr[i] = Integer.parseInt(dataPool[i]);
            }
            send(arr[0] + arr[1] + arr[2]);

        } catch (IOException ex) {
            Logger.getLogger(UDPMServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Application contents --------------------------------
         */
        socket.close();
    }
}
