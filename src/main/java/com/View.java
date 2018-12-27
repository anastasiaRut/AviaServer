package com;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View extends JFrame {
    private Font font;
    private JFrame frame ;
    private JSlider slider;
    private JTextArea text;
    private static View view;

    public static View getView() {
        if(view==null)
        {
            view= new View();
        }
        return view;
    }

    private View(){
        font = new Font("Verdana", Font.PLAIN, 11);
        frame = new JFrame("Окно подключений");
        frame.setFont(font);
        text = new JTextArea("Нет подключения");
        frame.add(text);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(270, 310));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    MultiThreadServer.executeIt.shutdown();
                    MultiThreadServer.server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
    public void settext(String s){
        text.append(s);
    }

}
