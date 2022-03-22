package com.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    static int GAME_WIDTH = 1000;
    static int GAME_HEIGHT = 600;
    static Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static int BALL_DIAMETER = 20; //duong kinh bóng
    static int PADDLE_WIDTH = 25;  // chiều rộng của thanh trượt
    static int PADDLE_HEIGHT = 100; // chiều cao thanh trượt

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Score score;
    Paddle player1;
    Paddle player2;
    Ball ball;
    Menu menu;
    GameOver gameOver;

    GamePanel() {
        newPaddles();
        newBall();
        newMenu();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        setFocusable(true); //events chỉ hoạt động trên panel được focus
        addKeyListener(new Handler());
        setPreferredSize(SCREEN_SIZE); // giống setSize nhưng setSize sử dụng khi layout manager cha không tồn tại

        this.addMouseListener(menu); // để menu "nghe" sự kiện
        this.addMouseMotionListener(menu);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, random.nextInt(GAME_HEIGHT / 2 - BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        player1 = new Paddle(10, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        player2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH - 10, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void newMenu() {
        menu = new Menu();
    }
    public void newGameOver(){gameOver = new GameOver();}

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        menu.draw(g);
        if (!menu.active) {
            player1.draw(g);
            player2.draw(g);
            ball.draw(g);
            score.draw(g);
        }
//        gameOver.draw(g);
////
//        if(score.player1 == 5){
//            gameOver.draw(g);
     //   }
    }

    public void move() {
        if (!menu.active) {
            player1.move();
            player2.move();
            ball.move();
        }
    }

    public void checkCollision() {
        //để người chơi k trượt quá khung game
        int limit = 2;
        if (player1.y <= limit) player1.y = limit;
        if (player1.y >= GAME_HEIGHT - PADDLE_HEIGHT - limit) player1.y = GAME_HEIGHT - PADDLE_HEIGHT - limit;
        if (player2.y <= limit) player2.y = limit;
        if (player2.y >= GAME_HEIGHT - PADDLE_HEIGHT - limit) player2.y = GAME_HEIGHT - PADDLE_HEIGHT - limit;

        //bóng đập vào và bật khi chạm vào phần trên và dưới của cửa sổ
        if (ball.y <= 0) ball.setYDirection(-ball.yVelocity);
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) ball.setYDirection(-ball.yVelocity);

        //bóng đập vào và bật ra khỏi thanh trượt
        if (ball.intersects(player1)) { // intersects(): check xem bóng và thanh trượt có chạm vào nhau hay không
            ball.xVelocity = Math.abs(ball.xVelocity); // đổi hướng
            ball.xVelocity++; // tăng tốc bóng
            if (ball.yVelocity > 0) ball.yVelocity++; // tăng tốc bóng
            else ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if (ball.intersects(player2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // tăng tốc bóng
            if (ball.yVelocity > 0) ball.yVelocity++; // tăng tốc bóng
            else ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //player1 được điểm -> reset bàn chơi
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            newBall();
        }

        // player2 được điểm -> reset bàn chơi
        if (ball.x <= 0) {
            score.player2++;
            newBall();
        }
    }

    public void run() {
        //game loop: liên tục update thông tin và repaint
        long lastTime = System.nanoTime();
        double FPS = 60.0;
        double drawInterval = 1_000_000_000 / FPS; // mỗi 0.0166 vẽ lại
        double delta = 0; // "delta/accumulator method"
        while (true) {
            long currentTime = System.nanoTime(); // lấy thời gian hiện tại theo nano sec
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) { // 1 = drawInterval
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class Handler extends KeyAdapter { //Event Handler class
        public void keyPressed(KeyEvent e) {
            player1.keyPressed(e);
            player2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }
}
