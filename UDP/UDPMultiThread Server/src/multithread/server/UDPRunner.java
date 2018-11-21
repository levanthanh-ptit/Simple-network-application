/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithread.server;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 *
 * @author ThanhLeVan
 */
public class UDPRunner {

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(8080);
        while(true){
            UDPMServer _UDPMServer = new UDPMServer(serverSocket);   
            _UDPMServer.start();
        }
    }
}