/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MTServer;

import EntityDB.DBContext;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class AppNhanVien extends TCPMServer {

    private DBContext context;

    public AppNhanVien(Socket socket) {
        super(socket);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4441);
        while (true) {
            Socket socket = serverSocket.accept();
            AppNhanVien Bai1 = new AppNhanVien(socket);
            Bai1.start();
        }
    }

    public static boolean checkEven(int num) {
        return num % 2 != 0;
    }

    @Override
    public void run() {
        try {
            /**
             * Application contents --------------------------------
             */
            ArrayList<NhanVien> nvList = new ArrayList<>();
            this.context = new DBContext("BANGLUONG", "sa", "123");
            int flag = 0;
            ResultSet rs1 = this.context.executeStoredProcedure("EXEC [dbo].[sp_get_luong] @username = N'1', @password = N'11'");
            rs1.next();
            NhanVien nv1 = new NhanVien("1", "11", rs1.getString("luong"));
            nv1.show();

            /**
             * Application contents --------------------------------
             */
            super.closeThread();
        } catch (Exception e) {
            Logger.getLogger(TCPMServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

class NhanVien {

    public String id;
    public String password;
    public String luong;

    public NhanVien(String id, String password, String luong) {
        this.id = id;
        this.password = password;
        this.luong = luong;
    }

    public void show() {
        System.out.println("id = " + id + " | password = " + password + " | luong = " + luong);
    }
}
