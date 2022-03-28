package com.model;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {
    public boolean active;

    //button play
    Rectangle playBtn;
    String playBtnText = "Play";
    Rectangle quitBtn;
    String quitBtnText = "Quit";
    boolean playBtnHovered = false;
    boolean quitBtnHovered = false;
    Font font;

    public Menu() {
        active = true;
        int width, height, btnX, btnY;
        width = 300;
        height = 50;
        btnX = GamePanel.GAME_WIDTH / 2 - width / 2;
        btnY = GamePanel.GAME_HEIGHT / 4;
        playBtn = new Rectangle(btnX, btnY, width, height);
        quitBtn = new Rectangle(btnX, btnY + 100, width, height);
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.PINK);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Pong Game", GamePanel.GAME_WIDTH / 2 - 150, 75);

            //play btn
            g.setColor(Color.GREEN);
            if (playBtnHovered) g.setColor(Color.PINK);
            g.fillRect(playBtn.x, playBtn.y, playBtn.width, playBtn.height);

            //quit btn
            g.setColor(Color.BLUE);
            if (quitBtnHovered) g.setColor(Color.PINK);
            g.fillRect(quitBtn.x, quitBtn.y, quitBtn.width, quitBtn.height);

            //play btn text
            font = new Font("Arial", Font.PLAIN, 30);
            int stringWidth;
            int stringHeight;

            //center string
            stringWidth = g.getFontMetrics(font).stringWidth(playBtnText);
            stringHeight = g.getFontMetrics(font).getHeight();

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(playBtnText, (int) (playBtn.getX() + playBtn.getWidth() / 2 - stringWidth / 2), (int) (playBtn.getY() + playBtn.getHeight() / 2 + stringHeight / 4));

            //quit btn text
            stringWidth = g.getFontMetrics(font).stringWidth(quitBtnText);
            stringHeight = g.getFontMetrics(font).getHeight();

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(quitBtnText, (int) (quitBtn.getX() + quitBtn.getWidth() / 2 - stringWidth / 2), (int) (quitBtn.getY() + quitBtn.getHeight() / 2 + stringHeight / 4));
        }
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint(); //
        if (playBtn.contains(p)) {
            active = false;
//            System.out.println("Clicked");
        }
        if (quitBtn.contains(p)) {
            System.exit(0);
            System.out.println("Quit");
        }
    }

    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();

        if (playBtn.contains(p)) {
            playBtnHovered = true;
        } else playBtnHovered = false;

        if (quitBtn.contains(p)) {
            quitBtnHovered = true;
        } else quitBtnHovered = false;
    }
}
