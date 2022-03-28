package com.model;

import java.awt.*;

public class GameOver {
    public boolean active;
    Score score;
    Rectangle resetBtn;
    String resetBtnText = "Reset";
    boolean resetBtnHovered;
    Font font;

    GameOver() {
        active = false;
        int width, height, btnX, btnY;
        width = 300;
        height = 50;
        btnX = GamePanel.GAME_WIDTH / 2 - width / 2;
        btnY = GamePanel.GAME_HEIGHT / 2 - height / 2;
        resetBtn = new Rectangle(btnX, btnY, width, height);
    }

    public void draw(Graphics g) {
        if(active){
            //reset btn
            g.setColor(Color.GREEN);
            if (resetBtnHovered) g.setColor(Color.PINK);
            g.fillRect(resetBtn.x, resetBtn.y, resetBtn.width, resetBtn.height);

            //reset btn text
            font = new Font("Arial", Font.PLAIN, 30);
            int stringWidth;
            int stringHeight;

            //center string
            stringWidth = g.getFontMetrics(font).stringWidth(resetBtnText);
            stringHeight = g.getFontMetrics(font).getHeight();

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(resetBtnText, (int) (resetBtn.getX() + resetBtn.getWidth() / 2 - stringWidth / 2), (int) (resetBtn.getY() + resetBtn.getHeight() / 2 + stringHeight / 4));
        }
    }
}
