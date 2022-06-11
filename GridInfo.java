import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridInfo {
    public final int NON_STATE = 0;
    public final int BLACK_STATE = 1;
    public final int WHITE_STATE = 2;
    public int[][] gridStateFlg;
    private int tebanFlg;
    private int TempState;
    private int grid_x;
    private int grid_y;
    private int grid_size;
    private int revColor; 

    public int getTebanFlg() {
        return tebanFlg;
    }

    public GridInfo(int grid_x, int grid_y, int grid_size) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.grid_size = grid_size;
        gridStateFlg = new int[grid_y][grid_x];
        for (int y = 0; y < grid_y; y++) {
            for (int x = 0; x < grid_x; x++) {
                gridStateFlg[y][x] = NON_STATE;
            }
        }
        gridStateFlg[3][3] = WHITE_STATE;
        gridStateFlg[4][4] = WHITE_STATE;
        gridStateFlg[4][3] = BLACK_STATE;
        gridStateFlg[3][4] = BLACK_STATE;
        tebanFlg = BLACK_STATE;
    }

    public int changeIndex(int click_tile) {
        int index = 0;
        for (int i = 0; i < grid_x; i++) {
            if (i * grid_size <= click_tile && click_tile < (i + 1) * grid_size) {
                index = i;  break;
            }
        }
        return index;
    }

    //手番を変えるタイミングは一番最後
    public void tebanFlgChange() { 
        if (tebanFlg == BLACK_STATE) {
            tebanFlg = WHITE_STATE; 
            System.out.println(tebanFlg + "手番を白に変更");
        } else if (tebanFlg == WHITE_STATE) {
            tebanFlg = BLACK_STATE;
            System.out.println(tebanFlg + "手番を黒に変更");
        }
    }

    public void setGridStateFlg(int indexX, int indexY) {
        switch (tebanFlg) {
            case BLACK_STATE : gridStateFlg[indexY][indexX] = BLACK_STATE;
            break;
            case WHITE_STATE : gridStateFlg[indexY][indexX] = WHITE_STATE;
            break;
        }
    }

    public Color getGridStoneColor(int indexX, int indexY) {
        switch (gridStateFlg[indexY][indexX]) {
            case BLACK_STATE : return Color.black;
            case WHITE_STATE : return Color.white;
            default :          return (new Color(0, 120, 0));
        }
    }

    public int getGridStateFlg(int indexX, int indexY) {
        return gridStateFlg[indexY][indexX];
    }

    public int reverseGridStateFlg(int click_x, int click_y) {
        System.out.println("パラメータ" + click_x + "::" + click_y);
        int revCnt = 0, top = 0, leftTop = 0, left = 0, leftDown = 0, 
        down = 0, rightDown = 0, right = 0, rightTop = 0, loopX = 0, loopY = 0;
        
        switch (tebanFlg) {
            case BLACK_STATE :  revColor = WHITE_STATE;
            break;
            case WHITE_STATE :  revColor = BLACK_STATE;
            break;
        } 
        // 左
        if (click_x >= 1) {
            loopX = click_x - 1;    loopY = click_y;
            System.out.print("左: ");
            while (checkReverse(loopX, loopY, revColor)) { left++; loopX--; } 
            if (loopX < 0) {loopX++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { left = 0; }
        }
        // 左上
        if (click_x >= 1 && click_y >= 1) {
            loopX = click_x - 1;    loopY = click_y - 1;
            System.out.print("左上: ");
            while (checkReverse(loopX, loopY, revColor)) { leftTop++; loopX--; loopY--; } 
            if (loopX < 0 || loopY < 0) { loopX++; loopY++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { leftTop = 0; }
        }
        // 上
        if (click_y >= 1) {
            loopX = click_x;    loopY = click_y - 1; 
            System.out.print("上: ");
            while (checkReverse(loopX, loopY, revColor)) { top++; loopY--; } 
            if (loopY < 0) { loopY++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { top = 0; }
        }
        // 右上
        if (click_x < 7 && click_y > 0) {
            loopX = click_x + 1;    loopY = click_y - 1;
            System.out.print("右上: ");
            while (checkReverse(loopX, loopY, revColor)) { rightTop++; loopX++; loopY-- ;}
            if (loopX == grid_x || loopY < 0) { loopX--; loopY++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { rightTop = 0; } 
        }
        // 右
        if (click_x < 7) {  
            loopX = click_x + 1;    loopY = click_y;
            System.out.print("右: ");
            while (checkReverse(loopX, loopY, revColor)) { right ++;  loopX ++; } 
            if (loopX == grid_x) { loopX--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { right = 0; }
        }
        // 右下
        if (click_x < 7 && click_y < 7) {
            loopX = click_x + 1;    loopY = click_y + 1;
            System.out.print("右下: ");
            while (checkReverse(loopX, loopY, revColor)) { rightDown ++;  loopX ++;  loopY ++; } 
            if (loopX == grid_x || loopY == grid_y) { loopX--; loopY--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { rightDown = 0; }
        }
        // 下
        if (click_y < 7) {
            loopX = click_x;    loopY = click_y + 1;
            System.out.print("下: ");
            while (checkReverse(loopX, loopY, revColor)) { down ++;  loopY ++; }
            if (loopY == grid_y) { loopY--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { down = 0; }
        }
        // 左下 
        if (click_x > 0 && click_y < 7) {
            loopX = click_x - 1;    loopY = click_y + 1;
            System.out.print("左下 ");
            while (checkReverse(loopX, loopY, revColor)) { leftDown ++; loopX --; loopY ++; } 
            if (loopX < 0 || loopY == grid_y) { loopX ++; loopY--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { leftDown = 0; }
        }
        System.out.println("上" + top + ":右上" + rightTop + ":右" + right + ":右下" + rightDown + ":下" + down + ":左下" + leftDown + ":左" + left + ":左上" + leftTop);
        doReverse(top, rightTop, right, rightDown, down, leftDown, left, leftTop, click_x, click_y);
        return (top + rightTop + right + rightDown + down + leftDown + left + leftTop);
    }

    public boolean checkReverse(int x, int y, int revColor) {
        if (x < 0 || x >= grid_x || y < 0 || y >= grid_y) {
            System.out.println("clear"); 
            return false;
        }
        //System.out.println("checkReverseメソッドの中のパラメータ" + x + " : " + y);
        if (gridStateFlg[y][x] == revColor) return true;
        else return false;
    }

    public void doReverse(int top, int rightTop, int right, int rightDown,
    int down, int leftDown, int left, int leftTop, int click_x, int click_y) {
        for (int i = 1; i <= top; i++) {
            gridStateFlg[click_y - i][click_x] = tebanFlg;
        }
        for (int i = 1; i <= rightTop; i++) {
            gridStateFlg[click_y - i][click_x + i] = tebanFlg;
        }
        for (int i = 1; i <= right; i++) {
            gridStateFlg[click_y][click_x + i] = tebanFlg;
        }
        for (int i = 1; i <= rightDown; i++) {
            gridStateFlg[click_y + i][click_x + i] = tebanFlg;
        }
        for (int i = 1; i <= down; i++) {
            gridStateFlg[click_y + i][click_x] = tebanFlg;
        }
        for (int i = 1; i <= leftDown; i++) {
            gridStateFlg[click_y + i][click_x - i] = tebanFlg;
        }
        for (int i = 1; i <= left; i++) {
            gridStateFlg[click_y][click_x - i] = tebanFlg;
        }
        for (int i = 1; i <= leftTop; i++) {
            gridStateFlg[click_y - i][click_x - i] = tebanFlg;
        }
    }

     public boolean setCheck(int click_x, int click_y) {
         if (gridStateFlg[click_y][click_x] != NON_STATE) { return false; }
        //System.out.println("パラメータ" + click_x + "::" + click_y);
        int revCnt = 0, top = 0, leftTop = 0, left = 0, leftDown = 0, 
        down = 0, rightDown = 0, right = 0, rightTop = 0, loopX = 0, loopY = 0;
        
        switch (tebanFlg) {
            case BLACK_STATE :  revColor = WHITE_STATE;
            break;
            case WHITE_STATE :  revColor = BLACK_STATE;
            break;
        } 
        // 左
        if (click_x >= 1) {
            loopX = click_x - 1;    loopY = click_y;
            System.out.print("左: ");
            while (checkReverse(loopX, loopY, revColor)) { left++; loopX--; } 
            if (loopX < 0) {loopX++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { left = 0; }
        }
        // 左上
        if (click_x >= 1 && click_y >= 1) {
            loopX = click_x - 1;    loopY = click_y - 1;
            System.out.print("左上: ");
            while (checkReverse(loopX, loopY, revColor)) { leftTop++; loopX--; loopY--; } 
            if (loopX < 0 || loopY < 0) { loopX++; loopY++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { leftTop = 0; }
        }
        // 上
        if (click_y >= 1) {
            loopX = click_x;    loopY = click_y - 1; 
            System.out.print("上: ");
            while (checkReverse(loopX, loopY, revColor)) { top++; loopY--; } 
            if (loopY < 0) { loopY++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { top = 0; }
        }
        // 右上
        if (click_x < 7 && click_y > 0) {
            loopX = click_x + 1;    loopY = click_y - 1;
            System.out.print("右上: ");
            while (checkReverse(loopX, loopY, revColor)) { rightTop++; loopX++; loopY-- ;}
            if (loopX == grid_x || loopY < 0) { loopX--; loopY++; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { rightTop = 0; } 
        }
        // 右
        if (click_x < 7) {  
            loopX = click_x + 1;    loopY = click_y;
            System.out.print("右: ");
            while (checkReverse(loopX, loopY, revColor)) { right ++;  loopX ++; } 
            if (loopX == grid_x) { loopX--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { right = 0; }
        }
        // 右下
        if (click_x < 7 && click_y < 7) {
            loopX = click_x + 1;    loopY = click_y + 1;
            System.out.print("右下: ");
            while (checkReverse(loopX, loopY, revColor)) { rightDown ++;  loopX ++;  loopY ++; } 
            if (loopX == grid_x || loopY == grid_y) { loopX--; loopY--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { rightDown = 0; }
        }
        // 下
        if (click_y < 7) {
            loopX = click_x;    loopY = click_y + 1;
            System.out.print("下: ");
            while (checkReverse(loopX, loopY, revColor)) { down ++;  loopY ++; }
            if (loopY == grid_y) { loopY--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { down = 0; }
        }
        // 左下 
        if (click_x > 0 && click_y < 7) {
            loopX = click_x - 1;    loopY = click_y + 1;
            System.out.print("左下 ");
            while (checkReverse(loopX, loopY, revColor)) { leftDown ++; loopX --; loopY ++; } 
            if (loopX < 0 || loopY == grid_y) { loopX ++; loopY--; }
            if (gridStateFlg[loopY][loopX] != tebanFlg) { leftDown = 0; }
        }
        System.out.println("" + top + ":" + rightTop + ":" + right + ":" + rightDown + ":" + down + ":" + leftDown + ":" + left + ":" + leftTop);
        if (top + rightTop + right + rightDown + down + leftDown + left + leftTop != 0) { return true; }
        else { return false; }
    }

    public boolean getPassFlg() {
        for (int y = 0; y < grid_y; y++) {
            for (int x = 0; x < grid_x; x++) {
                if (setCheck(x, y) == true) { return false; }
            }
        }
        return true;
    }

    public JPanel getResult() {
        for (int[] i : gridStateFlg) {
            for (int v : i) {
                if (v != WHITE_STATE) { 
                    System.out.println("黒の勝ち"); 
                    JLabel label = new JLabel("黒の勝ち");
                    JPanel panel = new JPanel();
                    panel.add(label);
                    return panel;
                }
                else if (v != BLACK_STATE) { 
                    System.out.println("白の勝ち");
                    JLabel label = new JLabel("白の勝ち");
                    JPanel panel = new JPanel();
                    panel.add(label);
                    return panel;
                }
            }
        }
        return new JPanel();
    }

}