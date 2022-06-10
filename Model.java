public class Model {
    private Viewer viewer;
    private Levels levels;
    private Audio audio;
    private int[][] desktop;
    private int[][] targets;
    private int rowPlayer;
    private int columnPlayer;
    private int positionPlayer;
    private boolean stateGame;
    private int xRect;
    private int yRect;
    private int widthRect;
    private int heightRect;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        audio = new Audio();
        levels = new Levels();
        initialization();
    }

    private void initialization() {
        stateGame = true;
        desktop = null;
        desktop = levels.getNextLevel();
        positionPlayer = 2;
        xRect = 50;
        yRect = 30;
        widthRect = 150;
        heightRect = 50;

        int counterOne = 0;
        int countThree = 0;
        int countFour = 0;

        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    rowPlayer = i;
                    columnPlayer = j;
                    counterOne++;
                }

                if (desktop[i][j] == 3) {
                    countThree++;
                }

                if (desktop[i][j] == 4) {
                    countFour++;
                }
            }
        }

        if (counterOne != 1
            || countThree == 0
            || countFour == 0
            || countThree != countFour) {
            stateGame = false;
            return;
        }

        targets = new int[2][countFour];
        int index = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 4) {
                    targets[0][index] = i;
                    targets[1][index] = j;
                    index++;
                }
            }
        }
    }

    public boolean getStateGame() {
        return stateGame;
    }

    public void move(String direction) {
        switch (direction) {
            case "Up":
                moveUp();
                positionPlayer = 0;
                break;
            case "Right":
                moveRight();
                positionPlayer = 1;
                break;
            case "Down":
                moveDown();
                positionPlayer = 2;
                break;
            case "Left":
                moveLeft();
                positionPlayer = 3;
                break;
            default:
                return;
        }
        checkTarget();
        viewer.update();
        won();
    }

    private void won() {
        boolean isWon = true;
        for (int i = 0; i < targets[0].length; i++) {
            int row = targets[0][i];
            int column = targets[1][i];
            if (desktop[row][column] != 3) {
                isWon = false;
                break;
            }
        }

        if (isWon) {
            audio.playSound(1);
            viewer.showMessageDialog("You won!");
            initialization();
            viewer.update();
        }
    }

    private void checkTarget() {
        for (int i = 0; i < targets[0].length; i++) {
            int row = targets[0][i];
            int column = targets[1][i];
            if (desktop[row][column] == 0) {
                desktop[row][column] = 4;
                break;
            }
        }
    }


    private void moveUp() {
        if (desktop[rowPlayer - 1][columnPlayer] == 3) {
            if (desktop[rowPlayer - 2][columnPlayer] == 0) {
                desktop[rowPlayer - 1][columnPlayer] = 0;
                desktop[rowPlayer - 2][columnPlayer] = 3;
            }

            if (desktop[rowPlayer - 2][columnPlayer] == 4) {
                desktop[rowPlayer - 1][columnPlayer] = 0;
                desktop[rowPlayer - 2][columnPlayer] = 3;
                audio.playSound(0);
            }
        }

        if (desktop[rowPlayer - 1][columnPlayer] == 0 || desktop[rowPlayer - 1][columnPlayer] == 4) {
            desktop[rowPlayer][columnPlayer] = 0;
            rowPlayer = rowPlayer - 1;
            desktop[rowPlayer][columnPlayer] = 1;
        }
    }

    private void moveRight() {
        if (desktop[rowPlayer][columnPlayer + 1] == 3) {
            if (desktop[rowPlayer][columnPlayer + 2] == 0) {
                desktop[rowPlayer][columnPlayer + 1] = 0;
                desktop[rowPlayer][columnPlayer + 2] = 3;
            }

            if (desktop[rowPlayer][columnPlayer + 2] == 4) {
                desktop[rowPlayer][columnPlayer + 1] = 0;
                desktop[rowPlayer][columnPlayer + 2] = 3;
                audio.playSound(0);
            }
        }

        if (desktop[rowPlayer][columnPlayer + 1] == 0 || desktop[rowPlayer][columnPlayer + 1] == 4) {
            desktop[rowPlayer][columnPlayer] = 0;
            columnPlayer = columnPlayer + 1;
            desktop[rowPlayer][columnPlayer] = 1;
        }
    }

    private void moveDown() {
        if (desktop[rowPlayer + 1][columnPlayer] == 3) {
            if (desktop[rowPlayer + 2][columnPlayer] == 0) {
                desktop[rowPlayer + 1][columnPlayer] = 0;
                desktop[rowPlayer + 2][columnPlayer] = 3;
            }

            if (desktop[rowPlayer + 2][columnPlayer] == 4) {
                desktop[rowPlayer + 1][columnPlayer] = 0;
                desktop[rowPlayer + 2][columnPlayer] = 3;
                audio.playSound(0);
            }
        }

        if (desktop[rowPlayer + 1][columnPlayer] == 0 || desktop[rowPlayer + 1][columnPlayer] == 4) {
            desktop[rowPlayer][columnPlayer] = 0;
            rowPlayer = rowPlayer + 1;
            desktop[rowPlayer][columnPlayer] = 1;
        }
    }

    private void moveLeft() {
        if (desktop[rowPlayer][columnPlayer - 1] == 3) {
            if (desktop[rowPlayer][columnPlayer - 2] == 0) {
                desktop[rowPlayer][columnPlayer - 1] = 0;
                desktop[rowPlayer][columnPlayer - 2] = 3;
            }

            if (desktop[rowPlayer][columnPlayer - 2] == 4) {
                desktop[rowPlayer][columnPlayer - 1] = 0;
                desktop[rowPlayer][columnPlayer - 2] = 3;
                audio.playSound(0);
            }
        }

        if (desktop[rowPlayer][columnPlayer - 1] == 0 || desktop[rowPlayer][columnPlayer - 1] == 4) {
            desktop[rowPlayer][columnPlayer] = 0;
            columnPlayer = columnPlayer - 1;
            desktop[rowPlayer][columnPlayer] = 1;
        }
    }

    public int[][] getDesktop() {
        return desktop;
    }

    public int getPositionPlayer() {
        return positionPlayer;
    }

    public int getLevel() {
        return levels.getNumberLevel();
    }

    public void doAction(String command) {
        if (command.equals("Restart")) {
            levels.setLevel(levels.getNumberLevel());
        } else if(command.equals("level_1")) {
            levels.setLevel(1);
        } else if(command.equals("level_2")) {
            levels.setLevel(2);
        } else if(command.equals("level_3")) {
            levels.setLevel(3);
        } else if(command.equals("level_4")) {
            levels.setLevel(4);
        } else if(command.equals("level_5")) {
            levels.setLevel(5);
        }  else if(command.equals("level_6")) {
            levels.setLevel(6);
        } else if(command.equals("level_7")) {
            levels.setLevel(7);
        } else if(command.equals("level_8")) {
            levels.setLevel(8);
        } else if(command.equals("level_9")) {
            levels.setLevel(9);
        }
        initialization();
        viewer.update();
    }

    public void doAction(int x, int y) {
        if(xRect <= x && yRect <= y && x <= xRect + widthRect && y <= yRect + heightRect) {
            doAction("Restart");
        }
    }

    public int getXpostionLevel() {
        return  700;
    }

    public int getYpostionLevel() {
        return 30;
    }

    public int getXpostionRect() {
        return xRect;
    }

    public int getYpostionRect() {
        return yRect;
    }

    public int getWidthRect() {
        return widthRect;
    }

    public int getHeightRect() {
        return heightRect;
    }

    public int getXpostionString() {
        return 70;
    }

    public int getYpostionString() {
        return 70;
    }


}
