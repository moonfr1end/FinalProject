package com.company;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.Socket;

public class A_Chat_Client_GUI {
    // Globals
    private static A_Chat_Client ChatClient;
    public static String UserName = "Anonymous";

    // GUI - Main Menu
    public static JFrame MainWindow = new JFrame();
    private static JButton B_CONNECT = new JButton();
    private static JButton B_DISCONNECT = new JButton();
    private static JButton B_SEND = new JButton();
    private static JLabel L_Message = new JLabel("Message: ");
    public static JTextField TF_Message = new JTextField(20);
    public static JTextArea TA_CONVERSATION = new JTextArea();
    private static JScrollPane SP_CONVERSATION = new JScrollPane();
    private static JLabel L_ONLINE = new JLabel();
    public static JList JL_ONLINE = new JList();
    private static JScrollPane SP_ONLINE = new JScrollPane();
    private static JLabel L_LoggedInAsBox = new JLabel();

    // GUI LogIn Window
    public static JFrame LogInWindow = new JFrame();
    public static JTextField TF_UserNameBox = new JTextField(20);
    private static JButton B_ENTER = new JButton("ENTER");
    private static JLabel L_EnterUserName = new JLabel("Enter Username: ");
    private static JPanel P_LogIn = new JPanel();

    // ------------------------------------------------------------------------------->

    public static void main(String[] args) {
        BuildMainWindow();
        Initialize();
    }

    // ------------------------------------------------------------------------------->

    public static void Connect() {
        try {
            final int PORT = 444;
            final String HOST = "localhost";
            Socket SOCK = new Socket(HOST, PORT);
            System.out.println("You connected to: " + HOST);

            ChatClient = new A_Chat_Client(SOCK);

            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(UserName);
            OUT.flush();

            Thread X = new Thread(ChatClient);
            X.start();
        } catch(Exception X) {
            System.out.println(X);
            JOptionPane.showMessageDialog(null, "Server not responding");
            System.exit(0);
        }
    }

    // ------------------------------------------------------------------------------->

    public static void Initialize() {
        B_SEND.setEnabled(false);
        B_DISCONNECT.setEnabled(false);
        B_CONNECT.setEnabled(true);
    }

    public static void BuildLogInWindow() {
        LogInWindow.setTitle("What's your name?");
        LogInWindow.setSize(400,100);
        LogInWindow.setLocation(250,200);
        LogInWindow.setResizable(false);

        P_LogIn = new JPanel();
        P_LogIn.add(L_EnterUserName);
        P_LogIn.add(TF_UserNameBox);
        P_LogIn.add(B_ENTER);
        LogInWindow.add(P_LogIn);

        Login_Action();
        LogInWindow.setVisible(true);
    }

    public static void BuildMainWindow() {
        LogInWindow.setTitle(UserName + "'s Chat Box");
        LogInWindow.setSize(450,500);
        LogInWindow.setLocation(220, 180);
        LogInWindow.setResizable(false);
        ConfigureMainWindow();
        MainWindow_Action();
        MainWindow.setVisible(true);
    }

    public static void ConfigureMainWindow() {
        MainWindow.setBackground(new java.awt.Color(255,255,255));
        MainWindow.setSize(600, 400);
        MainWindow.getContentPane().setLayout(null);

        B_SEND.setBackground(new java.awt.Color(0,0,255));
        B_SEND.setForeground(new java.awt.Color(255,255,255));
        B_SEND.setText("SEND");
        MainWindow.getContentPane().add(B_SEND);
        B_SEND.setBounds(495,310,75,30);

        B_DISCONNECT.setBackground(new java.awt.Color(255,0,0));
        B_DISCONNECT.setForeground(new java.awt.Color(255,255,255));
        B_DISCONNECT.setText("DISCONNECT");
        MainWindow.getContentPane().add(B_DISCONNECT);
        B_DISCONNECT.setBounds(110,10,110,50);

        B_CONNECT.setBackground(new java.awt.Color(0,0,255));
        B_CONNECT.setForeground(new java.awt.Color(255,255,255));
        B_CONNECT.setText("CONNECT");
        B_CONNECT.setToolTipText("");
        MainWindow.getContentPane().add(B_CONNECT);
        B_CONNECT.setBounds(10,10,90,50);

        L_Message.setText("MESSAGE");
        MainWindow.getContentPane().add(L_Message);
        L_Message.setBounds(350,70,60,20);

        TF_Message.setForeground(new java.awt.Color(0,0,255));
        TF_Message.requestFocus();
        MainWindow.getContentPane().add(TF_Message);
        TF_Message.setBounds(200,310,300,30);

        TA_CONVERSATION.setColumns(20);
        TA_CONVERSATION.setFont(new java.awt.Font("Tahoma", 0, 12));
        TA_CONVERSATION.setForeground(new java.awt.Color(0,0,255));
        TA_CONVERSATION.setLineWrap(true);
        TA_CONVERSATION.setRows(5);
        TA_CONVERSATION.setEditable(false);

        SP_CONVERSATION.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_CONVERSATION.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_CONVERSATION.setViewportView(TA_CONVERSATION);
        MainWindow.getContentPane().add(SP_CONVERSATION);
        SP_CONVERSATION.setBounds(200,90,370,210);

        L_ONLINE.setHorizontalAlignment(SwingConstants.CENTER);
        L_ONLINE.setText("ONLINES");
        L_ONLINE.setToolTipText("");
        MainWindow.getContentPane().add(L_ONLINE);
        L_ONLINE.setBounds(25,70,130,16);

        // String[] TestNames = {"Aidos", "Nursik", "Olzhas"};
        JL_ONLINE.setForeground(new java.awt.Color(0,0,255));
        // JL_ONLINE.setListData(TestNames);

        SP_ONLINE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_ONLINE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_ONLINE.setViewportView(JL_ONLINE);
        MainWindow.getContentPane().add(SP_ONLINE);
        SP_ONLINE.setBounds(10,90,170,250);

        L_LoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_LoggedInAsBox.setFont(new java.awt.Font("Tahoma", 0, 16));
        L_LoggedInAsBox.setForeground(new java.awt.Color(255,0,0));
        L_LoggedInAsBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,255)));
        MainWindow.getContentPane().add(L_LoggedInAsBox);
        L_LoggedInAsBox.setBounds(250,10,270,50);
    }

    // ------------------------------------------------------------------------------->

    public static void Login_Action() {
        B_ENTER.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        ACTION_B_ENTER();
                    }
                }
        );
    }

    // ------------------------------------------------------------------------------->

    public static void ACTION_B_ENTER() {
        if(!TF_UserNameBox.getText().equals("")) {
            UserName = TF_UserNameBox.getText().trim();
            L_LoggedInAsBox.setText(UserName);
            A_Chat_Server.CurrentUsers.add(UserName);
            MainWindow.setTitle(UserName + "'s Chat Box");
            LogInWindow.setVisible(false);
            B_SEND.setEnabled(true);
            B_DISCONNECT.setEnabled(true);
            B_CONNECT.setEnabled(false);
            Connect();
        }
        else {
            JOptionPane.showMessageDialog(null, "Please enter a name");
        }
    }

    // ------------------------------------------------------------------------------->

    public static void MainWindow_Action() {
        B_SEND.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        ACTION_B_SEND();
                    }
                }
        );
        B_DISCONNECT.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        ACTION_B_DISCONNECT();
                    }
                }
        );
        B_CONNECT.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        BuildLogInWindow();
                    }
                }
        );
    }

    public static void ACTION_B_SEND() {
        if(!TF_Message.getText().equals("")) {
            ChatClient.SEND(TF_Message.getText());
            TF_Message.requestFocus();
        }
    }

    public static void ACTION_B_DISCONNECT() {
        try {
            ChatClient.DISCONNECT();
        } catch (Exception Y) {
            Y.printStackTrace();
        }
    }
}
