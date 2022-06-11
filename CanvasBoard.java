import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CanvasBoard extends JPanel implements MouseListener {
    private int width, height, grid_x, grid_y, grid_size;
    private final int BLACK_STATE = 1;
    private final int WHITE_STATE = 2;
    private Algorithm algo;
    private JLabel tebanLabel;  
    Othello othello;  

    public CanvasBoard(int w, int h, int x, int y, int size, Othello othello) {
        width = w;
        height = h;
        grid_x = x;
        grid_y = y;
        grid_size = size;
        this.othello = othello;

        algo = new Algorithm(grid_x, grid_y, grid_size);
        this.addMouseListener(this);
        tebanLabel = new JLabel("���݂̎�� : ��");

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 120, 0));

        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);

        for (int y = 0; y <= grid_y; y++) {
            g.drawLine(0, y * grid_size, width, y * grid_size);
        }
        for (int x = 0; x <= grid_x; x++) {
            g.drawLine(x * grid_size, 0, x * grid_size, height);
        }
        for (int y = 0; y < grid_y; y++) {
            for (int x = 0; x < grid_x; x++) {
                if (algo.othelloBoard[y][x] == BLACK_STATE) {
                    g.setColor(Color.black);
                    g.fillOval(x * grid_size + 5, y * grid_size + 5, 30, 30);
                }
                if (algo.othelloBoard[y][x] == WHITE_STATE) {
                    g.setColor(Color.white);
                    g.fillOval(x * grid_size + 5, y * grid_size + 5, 30, 30);
                }
            }
        }
    }
    public void mouseClicked(MouseEvent e) {
        int clickPosX = 0, clickPosY = 0, indexX = 0, indexY = 0;
        clickPosX = e.getX();
        clickPosY = e.getY();
        indexX = algo.changeIndex(clickPosX); // �N���b�N���W����Ή�����C���f�b�N�X�ɕϊ�����B
        indexY = algo.changeIndex(clickPosY);
        System.out.println("���݂̎��" + algo.getTurnFlg());

        if (algo.reverseCheck(indexX, indexY) == 0) { 
            return;  // ���]�ł���ӏ����Ȃ��̂ŉ������Ȃ�
        }
        
        algo.set(indexX, indexY); // �΂𔽓]
        algo.test();

        repaint(); // �ĕ`��

        if (algo.checkGameOver()) { // ��Ԍ��̑O�ɔ���
            JOptionPane.showMessageDialog(othello.getFrame(),
            "�Q�[���I�� " + algo.getWinner());
            return;
        }

        algo.changeTurnFlg(); // ��Ԍ��

        if (algo.getPassFlg()) {
            if (algo.getPassFlg()) {
                System.out.println("2��ڂ̃p�X�`�F�b�N");
                algo.checkGameOver();
                JOptionPane.showMessageDialog(othello.getFrame(), 
                ("�Q�[���I�� " + algo.getWinner()));
                return;
            }
            JOptionPane.showMessageDialog(othello.getFrame(), 
            "������ꏊ������܂���B�p�X���܂�");
        }

        String turn = null;
        int i = algo.getTurnFlg();
        if (i == 1) {
            turn = "��";
        } else {
            turn = "��";
        }
        tebanLabel.setText("���݂̎�� : " + turn);
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}

    public JLabel getTebenLable() {
        return this.tebanLabel;
    }
}