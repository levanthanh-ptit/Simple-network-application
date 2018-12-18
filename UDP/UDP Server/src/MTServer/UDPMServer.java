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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */


//class UDPMServerThread extends Thread {
//
//    DatagramSocket socket;
//    HashMap<String, DataPool> dataTable;
//    
//
//    public UDPMServerThread(DatagramSocket socket, HashMap<String, DataPool> dataTable) {
//        this.socket = socket;
//        this.dataTable = dataTable;
//    }
//
//    @Override
//    public void run() {
//
//    }
//
//}

public class UDPMServer {

    DatagramSocket socket;
    int PacketSize = 256;
    public InetAddress CurrentClientAddress;
    public int CurrentClientPort;
    HashMap<String, DataPool> dataTable;

    public UDPMServer(DatagramSocket socket) {
        this.socket = socket;
        dataTable = new HashMap<>();
    }

    public byte[] getBuffPacket() {
        return new byte[this.PacketSize];
    }
    
    public DatagramPacket receivePacket() throws IOException {
        DatagramPacket p = new DatagramPacket(this.getBuffPacket(), this.PacketSize);
        socket.receive(p);
        this.CurrentClientAddress = p.getAddress();
        this.CurrentClientPort = p.getPort();
        return p;
    }

    public void send(int num) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = String.valueOf(num).getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, CurrentClientAddress, CurrentClientPort);
        this.socket.send(Res);
    }

    public void send(String data) throws IOException {
        byte[] bufRes = getBuffPacket();
        bufRes = data.getBytes();
        DatagramPacket Res = new DatagramPacket(bufRes, bufRes.length, CurrentClientAddress, CurrentClientPort);
        this.socket.send(Res);
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4442);
        UDPMServer _UDPMServer = new UDPMServer(serverSocket);
        _UDPMServer.start();
    }

    public void start(){
        /**
         * Application contents --------------------------------
         */
        int MAX = 3;
        boolean flag = true;
        while (flag) {
            try {
                DatagramPacket p = this.receivePacket();
                System.out.println("Up coming: "+ new String(p.getData()).trim());
                String[] sp = new String(p.getData()).trim().split("`");
                //--packet tag or packet size--
                int i = Integer.parseInt(sp[0]);
                //--key of Map----
                String key = p.getAddress().toString() + p.getPort();
                if (!dataTable.containsKey(key)) {
                    //--value of Map--
                    System.out.println("pass 1");
                    DataPool value = new DataPool(p.getAddress(), p.getPort(), i);
                    dataTable.put(key, value);
                    continue;
                }
                System.out.println("pass 2");
                String data = sp[1];
                int num = Integer.parseInt(data);
                if(num%2==0){
                    this.send("sai");
                    continue;
                }
                else{
                    this.send("dung");
                }
                dataTable.get(key).push(i, data);
                if(dataTable.get(key).dataCount==MAX)
                {
                    int render = 0;
                    for (String index : dataTable.get(key).dataPool) {
                        render += Integer.parseInt(index);
                    }
                    this.send(render);
                }
            } catch (IOException ex) {
                Logger.getLogger(UDPMServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        socket.close();
    }
    /**
     * Application contents --------------------------------
     */

}
