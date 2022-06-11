public class Algorithm {
    private final int GRID_X;
    private final int GRID_Y;
    private final int GRID_SIZE;
    private final int BLACK_STATE = 1;
    private final int WHITE_STATE = 2;
    private final int NON_STATE = 0;
    public int[][] othelloBoard;
    private int turnFlg;
    private int reverseFlg = WHITE_STATE;
    private boolean black_pass, white_pass;
    private String winner;

    public Algorithm(int grid_x, int grid_y, int grid_size) {
        this.GRID_X = grid_x; 
        this.GRID_Y = grid_y;
        this.GRID_SIZE = grid_size;
        othelloBoard = new int[GRID_X][GRID_Y]; //64マスのオセロボードを生成

        for (int[] v : othelloBoard) { // すべてのマスに何も置かれていない意味を
            for (int i : v) {          // もつNON_STATEを代入
                i = NON_STATE;
            }
        }
        othelloBoard[3][3] = WHITE_STATE; // 初期石を配置
        othelloBoard[4][4] = WHITE_STATE;
        othelloBoard[3][4] = BLACK_STATE;
        othelloBoard[4][3] = BLACK_STATE;

        turnFlg = BLACK_STATE; // 先手は黒
        reverseFlg = WHITE_STATE; // 後ろ手は白
    } 

    public int changeIndex(int clickPos) {
        int index = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (i * GRID_SIZE <= clickPos && clickPos < (i + 1) * GRID_SIZE) {
                index = i;  break;
            }
        }
        return index;
    }

    private int checkTop(int x, int y) {
        int top = 0;
        // 上の確認
        if (y >= 2) {
            if (othelloBoard[y - 1][x] == reverseFlg) {
                top++;
                for (int i = y - 2; i >= 0; i--, top++) {
                    if (othelloBoard[i][x] == turnFlg) {
                        return top;
                    } else if (othelloBoard[i][x] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    private int checkRightTop(int x, int y) {
        int rightTop = 0;
        // 右上の確認
        if (y >= 2 && x <= 5) {
            if (othelloBoard[y - 1][x + 1] == reverseFlg) {
                rightTop++;
                for (int i = y - 2, j = x + 2; i >= 0 && j <= 7; i--, j++, rightTop++) {
                    if (othelloBoard[i][j] == turnFlg) {
                        return rightTop;
                    } else if (othelloBoard[i][j] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    private int checkRight(int x, int y) {
        int right = 0;
        // 右の確認
        if (x <= 5) {
            if (othelloBoard[y][x + 1] == reverseFlg) {
                right++;
                for (int j = x + 2; j <= 7; j++, right++) {
                    if (othelloBoard[y][j] == turnFlg) {
                        return right;
                    } else if (othelloBoard[y][j] == NON_STATE) {
                        return 0;
                    }
                } 
            }
        }
        return 0;
    }
    private int checkRightDown(int x, int y) {
        int rightDown = 0;
        //　右下の確認
        if (y <= 5 && x <= 5) {
            if (othelloBoard[y + 1][x + 1] == reverseFlg) {
                rightDown++;
                for (int i = y + 2, j = x + 2; i <= 7 && j <= 7; i++, j++, rightDown++) {
                    if (othelloBoard[i][j] == turnFlg) {
                        return rightDown;
                    } else if (othelloBoard[i][j] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    private int checkDown(int x, int y) {
        int down = 0;
        // 下の確認
        if (y <= 5) {
            if (othelloBoard[y + 1][x] == reverseFlg) {
                down++;
                for (int i = y + 2; i <= 7; i++, down++) {
                    if (othelloBoard[i][x] == turnFlg) {
                        return down;
                    } else if (othelloBoard[i][x] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    private int checkLeftDown(int x, int y) {
        int leftDown = 0;
        //　左下の確認
        if (y <= 5 && x >= 2) {
            if (othelloBoard[y + 1][x - 1] == reverseFlg) {
                leftDown++;
                for (int i = y + 2, j = x - 2; i <= 7 && j >= 0; i++, j--, leftDown++) {
                    if (othelloBoard[i][j] == turnFlg) {
                        return leftDown;
                    } else if (othelloBoard[i][j] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    private int checkLeft(int x, int y) {
        int left = 0;
        //　左の確認
        if (x >= 2) {
            if (othelloBoard[y][x - 1] == reverseFlg) {
                left++;
                for (int j = x - 2; j >= 0; j--, left++) {
                    if (othelloBoard[y][j] == turnFlg) {
                        return left;
                    } else if (othelloBoard[y][j] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    private int checkLeftTop(int x, int y) {
        int leftTop = 0;
        //　左上の確認
        if (y >= 2 && x >= 2) {
            if (othelloBoard[y - 1][x - 1] == reverseFlg) {
                leftTop++;
                for (int i = y - 2, j = x - 2; i >= 0 && j >= 0; i--, j--, leftTop++) {
                    if (othelloBoard[i][j] == turnFlg) {
                        return leftTop;
                    } else if (othelloBoard[i][j] == NON_STATE) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    public int reverseCheck(int x, int y) {
        if (x < 0 || x >= GRID_X || y < 0 || y >= GRID_Y
        || othelloBoard[y][x] != NON_STATE) {
            return 0;
        }
        reverseFlg = (turnFlg == BLACK_STATE) ? WHITE_STATE : BLACK_STATE;
        int top = checkTop(x, y); int rightTop = checkRightTop(x, y);
        int right = checkRight(x, y); int rightDown = checkRightDown(x, y);
        int down = checkDown(x, y); int leftDown = checkLeftDown(x, y);
        int left = checkLeft(x, y); int leftTop = checkLeftTop(x, y);
        return (top + rightTop + right + rightDown + down + leftDown + left + leftTop);
    }

    public void set(int x, int y) {
        if (reverseCheck(x, y) != 0) {
            int top = checkTop(x, y); int rightTop = checkRightTop(x, y);
            int right = checkRight(x, y); int rightDown = checkRightDown(x, y);
            int down = checkDown(x, y); int leftDown = checkLeftDown(x, y);
            int left = checkLeft(x, y); int leftTop = checkLeftTop(x, y);
            othelloBoard[y][x] = turnFlg;


            System.out.println("reverseCheckの結果 : " + "その時のターン" + turnFlg);
            System.out.println("上" + top +"右上"+ rightTop +"右"+ right +"右下"+ rightDown + "下" +
            down + "左下" + leftDown + "左" + left + "左上" + leftTop);
        

            while (top != 0) {
                othelloBoard[y - top][x] = turnFlg;
                top--;
            }
            while (rightTop != 0) {
                othelloBoard[y - rightTop][x + rightTop] = turnFlg;
                rightTop--;
            }
            while (right != 0) {
                othelloBoard[y][x + right] = turnFlg;
                right--;
            }
            while (rightDown != 0) {
                othelloBoard[y + rightDown][x + rightDown] = turnFlg;
                rightDown--;
            }
            while (down != 0) {
                othelloBoard[y + down][x] = turnFlg;
                down--;
            }
            while (leftDown != 0) {
                othelloBoard[y + leftDown][x - leftDown] = turnFlg;
                leftDown--;
            }
            while (left != 0) {
                othelloBoard[y][x - left] = turnFlg;
                left--;
            }
            while (leftTop != 0) {
                othelloBoard[y - leftTop][x - leftTop] = turnFlg;
                leftTop--;
            }

            if (turnFlg == BLACK_STATE) {
                black_pass = false;  //　このフラグは両者が連続してパスしたとき
            } else if (turnFlg == WHITE_STATE) { // ゲームを終了させるために使う
                white_pass = false;
            }
        }
    }

    public void changeTurnFlg() {
        if (turnFlg == BLACK_STATE) {
            turnFlg = WHITE_STATE;
            reverseFlg = BLACK_STATE;
        }else if (turnFlg == WHITE_STATE) {
            turnFlg = BLACK_STATE;
            reverseFlg = WHITE_STATE;
        }
        System.out.println("ターン変更");
    }

    public boolean getPassFlg() {
        for (int y = 0; y < GRID_Y; y++) {
            for (int x = 0; x < GRID_X; x++) {
                if (reverseCheck(x, y) != 0) { // 0ではない＝反転できる箇所がある
                    return false;
                }
            }
        }
        System.out.println("おける場所がありません。パスします。");
        if (turnFlg == BLACK_STATE) {
            black_pass = true;
        } else if (turnFlg == WHITE_STATE) {
            white_pass = true;
        }
        changeTurnFlg();
        return true;
    }

    public int getTurnFlg() {
        return this.turnFlg;
    }

    public void test() {
        System.out.println("中身");
        for (int[] i : othelloBoard) {
            for (int isi : i) {
                System.out.print(isi);
            }
            System.out.println();
        }
        System.out.println("turnFlg : " + turnFlg);
        System.out.println("黒のパス状況 :" + black_pass);
        System.out.println("白のパス状況 : " + white_pass);
    }

    public boolean checkGameOver() {
        // 全滅　全埋　両者ともパス が終了条件
        boolean zenmetu = true;
        boolean zenume = true;

        u:for (int[] y : othelloBoard) {// 全埋
            for (int x : y) {
                if (x == NON_STATE) {
                    zenume = false;
                    System.out.println("空きマスがあります");
                    break u;
                }
            }
        }
        m:for (int[] y : othelloBoard) {
            for (int x : y) {
                if (turnFlg == BLACK_STATE) {
                    if (x == WHITE_STATE) {
                        zenmetu = false;
                        System.out.println("白色の石が残ってます");
                        break m;
                    }
                } else if (turnFlg == WHITE_STATE) {
                    if (x == BLACK_STATE) {
                        zenmetu = false;
                        System.out.println("黒色の石が残ってます");
                        break m;
                    }
                }
            }
        }
        if (zenume == true || black_pass == true && white_pass == true
         || zenmetu == true) {
            int resultBlack = 0;
            int resultWhite = 0;

            for (int[] y : othelloBoard) {
                for (int x : y) {
                    if (x == BLACK_STATE) {
                        resultBlack++;
                    } else if (x == WHITE_STATE) {
                        resultWhite++;
                    }
                }
            }

            if (resultBlack > resultWhite) {
                winner = "黒の勝ち";
            } else if (resultBlack < resultWhite) {
                winner = "白の勝ち";
            } else if (resultBlack == resultWhite) {
                winner = "勝者なし引き分けです";
            }

            return true;
        } 
        return false;
    }

    public String getWinner() {
        return this.winner;
    }
}