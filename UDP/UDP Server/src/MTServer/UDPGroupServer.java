/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MTServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class UDPGroupServer {

    DatagramSocket socket;
    InetAddress GroupAddress;
    int GroupPort;
    int DataReceivedCount = 0;
    int PacketSize = 256;
    int PacketCount;
    String[] dataPool;
    HashMap<String, Date> TimeOutMap;

    public UDPGroupServer(String GroupAddress, int GroupPort) throws UnknownHostException, SocketException {
        this.socket = new DatagramSocket(4448,InetAddress.getLocalHost());
        
        this.GroupAddress = InetAddress.getByName(GroupAddress);
        this.GroupPort = GroupPort;
        TimeOutMap = new HashMap<>();
    }

    public byte[] getBuffPacket() {
        return new byte[this.PacketSize];
    }

    public void showClientInfo() {
        System.out.println("ClientPort: " + this.GroupPort);
        System.out.println("PacketSize: " + this.PacketSize + " Byte");
        System.out.println("Data Pool size: " + this.dataPool.length);
        System.out.println("Data Pool  content: "+ this.dataPool[0]);
    }
    public boolean checkKey(String adress){
        boolean ret;
        try {
            ret = TimeOutMap.containsKey(adress);
            }
         catch (NullPointerException ex) {
            Logger.getLogger(UDPGroupServer.class.getName()).log(Level.SEVERE, null, ex);
            ret = false;
        }
        return  ret;
    }
    public boolean acceptConnect() {
        DatagramPacket p = new DatagramPacket(this.getBuffPacket(), this.PacketSize);
        try {
            socket.receive(p);
            String adress = p.getAddress().toString();
            System.out.println("Adress :@@ "+adress);
//            if (!checkKey(adress)) 
//            {
//                TimeOutMap.put(adress, new Date());
//            } else {
//                Date current = new Date();
//                if ((current.getTime() - TimeOutMap.get(adress).getTime()) >= 200000) //1s = 1000mls
//                {
//                    TimeOutMap.remove(p);
//                    byte[] bufRes = getBuffPacket();
//                    bufRes = "qua 20 giay. dong ket noi cua ban.".getBytes();
//                    DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, p.getAddress(), p.getPort());
//                    this.socket.send(Res);
//                    return false;
//                } else {
//                    byte[] bufRes = getBuffPacket();
//                    bufRes = "acept".getBytes();
//                    DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, p.getAddress(), p.getPort());
//                    this.socket.send(Res);
//                }
//            }
            String[] DPSizeAndPacketSize = new String(p.getData()).trim().split(",");
            int UpComingPacketCount = Integer.parseInt(DPSizeAndPacketSize[0]);
            this.PacketCount = UpComingPacketCount;
            this.PacketSize = Integer.parseInt(DPSizeAndPacketSize[1]);
            this.dataPool = new String[UpComingPacketCount];
            Calendar currentTime = Calendar.getInstance();
            System.out.println("Accept connect at: " + new SimpleDateFormat("HH:mm:ss").format(currentTime.getTime()));
            showClientInfo();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UDPGroupServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int receive() throws IOException {
        DatagramPacket p = new DatagramPacket(this.getBuffPacket(), this.PacketSize);
        socket.receive(p);
        System.out.println("Data come: "+new String(p.getData()).trim());
        String[] sp = new String(p.getData()).trim().split("`");
        int i = Integer.parseInt(sp[0]);
        dataPool[i] = sp[1];
        DataReceivedCount++;
        System.out.println(dataPool[i]);
        return i;
    }

    public void send(int num) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = String.valueOf(num).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, GroupAddress, GroupPort);
        this.socket.send(Res);
    }

    public void send(String data) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = data.getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, GroupAddress, GroupPort);
        this.socket.send(Res);
    }

    public static void main(String[] args) throws IOException {
        int GroupClientPort = 4448;
        String GroupAddress = "230.0.0.55";
        UDPGroupServer _UDPGroupServer = new UDPGroupServer(GroupAddress, GroupClientPort);
        _UDPGroupServer.start();

    }

    public void dataDelete(int i) {
        this.dataPool[i] = null;
        this.DataReceivedCount--;
    }

    public void start() {

        /**
         * Application contents --------------------------------
         */
        try {
            while (true) {
                if(this.acceptConnect())
                {
                    System.out.println("__acept__");
                    int i = this.receive();
                    System.out.println("data: == "+dataPool[i]);
                    this.send(dataPool[i]);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UDPGroupServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Application contents --------------------------------
         */
        socket.close();
    }
}
