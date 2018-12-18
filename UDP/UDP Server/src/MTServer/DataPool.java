/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MTServer;

import java.net.InetAddress;

/**
 *
 * @author ThanhLeVan
 */
class DataPool {

    public InetAddress ClientAddress;
    public int ClientPort;
    public int dataCount = 0;
    public String[] dataPool;

    public DataPool(InetAddress ClientAddress, int ClientPort, int poolSize) {
        this.ClientAddress = ClientAddress;
        this.ClientPort = ClientPort;
        this.dataPool = new String[poolSize];
    }

    public void show() {
        System.out.println("Client Host: " + this.ClientAddress + " : " + this.ClientPort);
        System.out.println("Data Pool size: " + this.dataPool.length);
    }

    public void push(int index, String data) {
        this.dataPool[index] = data;
        dataCount++;
    }

    public void deleteDataPoolAt(int index) {
        dataPool[index] = null;
        dataCount--;
    }
}
