package com.company;

import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Scanner;

public class A_Chat_Server_Return implements Runnable {
    // Globals
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
    public static User user = new User();
    public static DataBase dataBase = new DataBase();

    public A_Chat_Server_Return(Socket X) {
        this.SOCK = X;
    }

    public void CheckConnection() throws IOException {
        if(!SOCK.isConnected()) {
            for(int i=1; i<=A_Chat_Server.ConnectionArray.size(); i++) {
                if(A_Chat_Server.ConnectionArray.get(i) == SOCK) {
                    A_Chat_Server.ConnectionArray.remove(i);
                }
            }

            for(int i=1; i<=A_Chat_Server.ConnectionArray.size(); i++) {
                Socket TEMP_SOCK = (Socket) A_Chat_Server.ConnectionArray.get(i-1);
                PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + "disconnected");
                TEMP_OUT.flush();
                System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + "disconnected");
            }
        }
    }

    public void run() {
        try {
            try {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());

                while(true) {
                    CheckConnection();

                    if (!INPUT.hasNext()) {
                        return;
                    }

                    MESSAGE = INPUT.nextLine();

                    DataBase dataBase = new DataBase();
                    User user = new User(MESSAGE);
                    dataBase.setInfo(user);
                    ResultSet rs = dataBase.getInfo();
                    System.out.println("Client said: " + MESSAGE);
                    String send="";
                    while (rs.next()) {
                        send = rs.getString("message");
                    }

                    for (int i = 1; i <= A_Chat_Server.ConnectionArray.size(); i++) {
                        Socket TEMP_SOCK = (Socket) A_Chat_Server.ConnectionArray.get(i - 1);
                        PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println(send);
                        TEMP_OUT.flush();
                        System.out.println("Sent to: " + TEMP_SOCK.getLocalAddress().getHostName());
                    }
                }
            } finally {
                SOCK.close();
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }
}
