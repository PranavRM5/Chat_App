import java.net.*;
import java.io.*;

import javax.swing.*;

//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class Client extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto",Font.PLAIN,20);

    public Client() {
        try {
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1", 6777);
            System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
            startReading();
            // startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                //System.out.println("Key Released "+e.getKeyCode());
                if (e.getKeyCode() == 10) {
                    //System.out.println("you pressed the enter button");
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me : "+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                   messageInput.requestFocus();
                }
            }

            
            
        });
    }

    private void createGUI(){
        //gui code

        this.setTitle("Client Messager[End]");
        this.setSize(600,700);
        this.setLocationRelativeTo(null);  //it use for to keep our website to the in Center
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //coding for component
        heading.setFont(font);
        messageArea.setFont(font);
        //message after send anybody can't edit the message
        messageArea.setEditable(false);
        messageInput.setFont(font);


       // Load the original image as an ImageIcon
        ImageIcon icon = new ImageIcon("images/clogo.png");
        
        // Resize the image to a smaller size (e.g., 50x50 pixels)
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        
        // Set the resized image as an icon
        heading.setIcon(new ImageIcon(image));

        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        

        messageInput.setHorizontalAlignment(SwingConstants.CENTER);

        //frame layout
        this.setLayout(new BorderLayout());

        //adding component to frame
        this.add(heading, BorderLayout.NORTH);


        //JScrollPane is provide the scrolling effect on the message area
        JScrollPane JScrollPane = new JScrollPane(messageArea);
        this.add(JScrollPane, BorderLayout.CENTER);
        

        this.add(messageInput, BorderLayout.SOUTH);

        this.setVisible(true);

    }

    public void startReading() {
        // thread for read data
        Runnable r1 = () -> {
            System.out.println("reader started");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        //System.out.println("Server terminate chat");
                        JOptionPane.showMessageDialog(this, "Server terminate chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    //System.out.println("Server : " + msg);
                    messageArea.append("Server : " + msg +"\n");
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("connection closed");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // thread for Send data from user to client
        Runnable r2 = () -> {
            System.out.println("writer started");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();

                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } 
            catch (Exception e) {
                //e.printStackTrace();
                System.out.println("connection closed");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Hello i am client");
        new Client();
    }
}
