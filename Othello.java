import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Othello {
    private static final int WIDTH = 320 + 16;
    private static final int HEIGHT = 340 + 50;
    private static final int GRID_X = 8;
    private static final int GRID_Y = 8;
    private static final int GRID_SIZE = 40;
    private JFrame gameFrame;
    private CanvasBoard board;
    private JPanel tebanPanel;

    public static void main(String[] args) {
        Othello othello = new Othello();
    }

    public Othello() {
        initGameFrame();
    }

    public void initGameFrame() {
        gameFrame = new JFrame();
        board = new CanvasBoard(WIDTH, HEIGHT, GRID_X, GRID_Y, GRID_SIZE, this);
        gameFrame.getContentPane().add(board);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.setBounds(10, 10, WIDTH, HEIGHT);
        gameFrame.setResizable(false);
        gameFrame.setTitle("ÉIÉZÉçÉQÅ[ÉÄ");

        tebanPanel = new JPanel();
        tebanPanel.add(board.getTebenLable());
        gameFrame.getContentPane().add(tebanPanel, BorderLayout.PAGE_END);
    }

    public JFrame getFrame() {
        return gameFrame;
    }
}