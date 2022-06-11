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
        othelloBoard = new int[GRID_X][GRID_Y]; //64�}�X�̃I�Z���{�[�h�𐶐�

        for (int[] v : othelloBoard) { // ���ׂẴ}�X�ɉ����u����Ă��Ȃ��Ӗ���
            for (int i : v) {          // ����NON_STATE����
                i = NON_STATE;
            }
        }
        othelloBoard[3][3] = WHITE_STATE; // �����΂�z�u
        othelloBoard[4][4] = WHITE_STATE;
        othelloBoard[3][4] = BLACK_STATE;
        othelloBoard[4][3] = BLACK_STATE;

        turnFlg = BLACK_STATE; // ���͍�
        reverseFlg = WHITE_STATE; // ����͔�
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
        // ��̊m�F
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
        // �E��̊m�F
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
        // �E�̊m�F
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
        //�@�E���̊m�F
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
        // ���̊m�F
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
        //�@�����̊m�F
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
        //�@���̊m�F
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
        //�@����̊m�F
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


            System.out.println("reverseCheck�̌��� : " + "���̎��̃^�[��" + turnFlg);
            System.out.println("��" + top +"�E��"+ rightTop +"�E"+ right +"�E��"+ rightDown + "��" +
            down + "����" + leftDown + "��" + left + "����" + leftTop);
        

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
                black_pass = false;  //�@���̃t���O�͗��҂��A�����ăp�X�����Ƃ�
            } else if (turnFlg == WHITE_STATE) { // �Q�[�����I�������邽�߂Ɏg��
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
        System.out.println("�^�[���ύX");
    }

    public boolean getPassFlg() {
        for (int y = 0; y < GRID_Y; y++) {
            for (int x = 0; x < GRID_X; x++) {
                if (reverseCheck(x, y) != 0) { // 0�ł͂Ȃ������]�ł���ӏ�������
                    return false;
                }
            }
        }
        System.out.println("������ꏊ������܂���B�p�X���܂��B");
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
        System.out.println("���g");
        for (int[] i : othelloBoard) {
            for (int isi : i) {
                System.out.print(isi);
            }
            System.out.println();
        }
        System.out.println("turnFlg : " + turnFlg);
        System.out.println("���̃p�X�� :" + black_pass);
        System.out.println("���̃p�X�� : " + white_pass);
    }

    public boolean checkGameOver() {
        // �S�Ł@�S���@���҂Ƃ��p�X ���I������
        boolean zenmetu = true;
        boolean zenume = true;

        u:for (int[] y : othelloBoard) {// �S��
            for (int x : y) {
                if (x == NON_STATE) {
                    zenume = false;
                    System.out.println("�󂫃}�X������܂�");
                    break u;
                }
            }
        }
        m:for (int[] y : othelloBoard) {
            for (int x : y) {
                if (turnFlg == BLACK_STATE) {
                    if (x == WHITE_STATE) {
                        zenmetu = false;
                        System.out.println("���F�̐΂��c���Ă܂�");
                        break m;
                    }
                } else if (turnFlg == WHITE_STATE) {
                    if (x == BLACK_STATE) {
                        zenmetu = false;
                        System.out.println("���F�̐΂��c���Ă܂�");
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
                winner = "���̏���";
            } else if (resultBlack < resultWhite) {
                winner = "���̏���";
            } else if (resultBlack == resultWhite) {
                winner = "���҂Ȃ����������ł�";
            }

            return true;
        } 
        return false;
    }

    public String getWinner() {
        return this.winner;
    }
}