package com.model;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame{
    GamePanel panel;

    GameFrame(){
        panel = new GamePanel();
        add(panel);
        setTitle("Pong");
        setResizable(false);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); // làm cho Frame "vừa" với game panel
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


