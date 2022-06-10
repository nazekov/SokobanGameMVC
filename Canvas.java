import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Image;
import java.io.IOException;

public class Canvas extends JPanel {
    private Model model;
    private Image[] imagesPlayer;

    private Image imageWall;
    private Image imageEmptySpace;
    private Image imageBox;
    private Image imageTarget;

    private Font fontLabelLevel;

    private boolean insideMap;
    private boolean checkedSpace;

    private int[][] desktop;

    public Canvas(Model model) {
        this.model = model;
        setBackground(Color.BLACK);
        setOpaque(true);
        fontLabelLevel = new Font("Impact", Font.PLAIN, 30);

        File fileNameImagePlayerFront = new File("images/player_front_2.png");
        File fileNameImagePlayerBack = new File("images/player_back_2.png");
        File fileNameImagePlayerRight = new File("images/player_right_2.png");
        File fileNameImagePlayerLeft = new File("images/player_left_2.png");

        File fileNameImageWall = new File("images/wall.png");
        File fileNameImageEmptySpace = new File("images/empty_space.png");
        File fileNameImageBox = new File("images/box.png");
        File fileNameImageTarget = new File("images/target.png");

        try {
            Image imagePlayerFront = ImageIO.read(fileNameImagePlayerFront);
            Image imagePlayerBack = ImageIO.read(fileNameImagePlayerBack);
            Image imagePlayerRight = ImageIO.read(fileNameImagePlayerRight);
            Image imagePlayerLeft = ImageIO.read(fileNameImagePlayerLeft);
            imagesPlayer = new Image[]{imagePlayerBack, imagePlayerRight, imagePlayerFront, imagePlayerLeft};

            imageWall = ImageIO.read(fileNameImageWall);
            imageEmptySpace = ImageIO.read(fileNameImageEmptySpace);
            imageBox = ImageIO.read(fileNameImageBox);
            imageTarget = ImageIO.read(fileNameImageTarget);
        } catch (IOException ioe) {
            System.out.println("Not Found Image " + ioe);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (model.getStateGame()) {
            drawMessage(g);
            drawDesktop(g);
        } else {
            drawError(g);
        }
    }

    private void drawMessage(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(fontLabelLevel);

        int xPositionLevel = model.getXpostionLevel();
        int yPositionLevel = model.getYpostionLevel();
        g.drawString("Level " + model.getLevel(), xPositionLevel, yPositionLevel);

        g.setColor(Color.GRAY);
        int xPositionRect = model.getXpostionRect();
        int yPositionRect = model.getYpostionRect();
        int widthRect = model.getWidthRect();
        int heightRect = model.getHeightRect();
        g.fillRect(xPositionRect, yPositionRect, widthRect, heightRect);
        g.setColor(Color.WHITE);
        g.drawRect(xPositionRect, yPositionRect, widthRect, heightRect);
        g.setColor(Color.RED);
        int xPositionString = model.getXpostionString();
        int yPositionString = model.getYpostionString();
        g.drawString("RESTART", xPositionString, yPositionString);
    }

    private void drawDesktop(Graphics g) {
        int start = 100;
        int x = start;
        int y = start;
        int width = 50;
        int height = 50;
        int offset = 10;

        desktop = model.getDesktop();
        int positionPlayer = model.getPositionPlayer();

        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                int element = desktop[i][j];
                if (element == 1) {
                    g.drawImage(imagesPlayer[positionPlayer], x, y, null);
                } else if (element == 2) {
                    insideMap = false;
                    checkedSpace = false;
                    g.drawImage(imageWall, x, y, null);
                } else if (element == 3) {
                    g.drawImage(imageBox, x, y, null);
                } else if (element == 4) {
                    g.drawImage(imageTarget, x, y, null);
                } else {
                    if (insideMap || !checkedSpace && findPlayerOrBoxOrTargetOrWalls(i, j)) {
                        g.drawImage(imageEmptySpace, x, y, null);
                    }
                }
                x = x + width + offset;
            }
            x = start;
            y = y + height + offset;
        }
    }


    private boolean findPlayerOrBoxOrTargetOrWalls(int row, int column) {
        boolean containsInUp = findInUp(row, column);
        if (insideMap) return true;
        if (!containsInUp) {
            checkedSpace = true;
            return false;
        }
        boolean containsInRight = findInRight(row, column);
        if (insideMap) return true;
        if (!containsInRight) {
            checkedSpace = true;
            return false;
        }
        boolean containsInDown = findInDown(row, column);
        if (insideMap) return true;
        if (!containsInDown) {
            checkedSpace = true;
            return false;
        }
        boolean containsInLeft = findInLeft(row, column);
        if (insideMap) return true;
        if (!containsInLeft) {
            checkedSpace = true;
            return false;
        }
        insideMap = true;
        return true;
    }

    private boolean findInUp(int row, int column) {
        for (int i = row - 1; i >= 0; i--) {
            if (desktop[i].length < column + 1) {
                return false;
            }

            if (desktop[i][column] == 3
                || desktop[i][column] == 4
                || desktop[i][column] == 1) {
                insideMap = true;
                return insideMap;
            }

            if (desktop[i][column] == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean findInDown(int row, int column) {
        for (int i = row + 1; i < desktop.length; i++) {
            if (desktop[i].length < column + 1) {
                return false;
            }
            if (desktop[i][column] == 3
                || desktop[i][column] == 4
                || desktop[i][column] == 1) {
                insideMap = true;
                return insideMap;
            }

            if (desktop[i][column] == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean findInRight(int row, int column) {
        for (int i = column + 1; i < desktop[row].length; i++) {
            if (desktop[row][i] == 3
                || desktop[row][i] == 4
                || desktop[row][i] == 1) {
                insideMap = true;
                return insideMap;
            }

            if (desktop[row][i] == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean findInLeft(int row, int column) {
        for (int i = column - 1; i >= 0; i--) {
            if (desktop[row][i] == 3
                || desktop[row][i] == 4
                || desktop[row][i] == 1) {
                insideMap = true;
                return insideMap;
            }
            if(desktop[row][i] == 2) {
                return true;
            }
        }
        return false;
    }

    private void drawError(Graphics g) {
        g.setFont(new Font("Impact", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString("Initialization Error", 150, 150);
    }
}