import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class Levels {
    private File file;
    private int level;
    private int[][] desktop;

    public Levels() {
        level = 1;
    }

    public int[][] getNextLevel() {
        desktop = null;

        switch (level) {
            case 1:
                desktop = getFirstLevel();
                break;
            case 2:
                desktop = getSecondLevel();
                break;
            case 3:
                desktop = getThirdLevel();
                break;
            case 4:
                file = new File("levels/level4.sok");
                if (file != null) {
                    desktop = getLevel(file);
                } else {
                    System.out.println("File is null");
                }
                break;
            case 5:
                file = new File("levels/level5.sok");
                if (file != null) {
                    desktop = getLevel(file);
                } else {
                    System.out.println("File is null");
                }
                break;
            case 6:
                file = new File("levels/level6.sok");
                if (file != null) {
                    desktop = getLevel(file);
                } else {
                    System.out.println("File is null");
                }
                break;
            case 7:
                desktop = getLevelFromServer(0);
                break;
            case 8:
                desktop = getLevelFromServer(1);
                break;
            case 9:
                desktop = getLevelFromServer(2);
                break;
            default:
                desktop = getFirstLevel();
                level = 1;
                break;
        }
        level = level + 1;
        return desktop;
    }

    private int[][] getLevelFromServer(int numberLevel) {
        Ulukbek ulukbek = getObjectFromServer(numberLevel);
	int[][] desktop;
	if (ulukbek != null) {
	    desktop = ulukbek.getDesktop();
        } else {
	    desktop = getFirstLevel();
	    level = 1;
        }
        
        return desktop;
    }

    private Ulukbek getObjectFromServer(int numberLevel) {
        Ulukbek ulukbek = null;

        try {
//            Socket clientSocket = new Socket("localhost", 4999);
            Socket clientSocket = new Socket("194.152.37.7", 4446);
            OutputStream outputStream = clientSocket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);

            writer.write("" + numberLevel);
            writer.newLine(); // Заработало только после добавления этой команды
            writer.flush();

            //get Object with array
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ulukbek = (Ulukbek) objectInputStream.readObject();

            objectInputStream.close();
            writer.close();
            clientSocket.close();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
	    return null;
        } catch (IOException ioException) {
            ioException.printStackTrace();
   	    return null;
        }

        return ulukbek;
    }

    private int[][] getFirstLevel() {
        return new int[][] {
                {0, 0, 0, 2, 2, 2, 2, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 2, 0, 0},
                {0, 2, 0, 0, 4, 4, 4, 0, 2, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 1, 0, 0, 3, 0, 3, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 3, 0, 2},
                {2, 0, 0, 0, 0, 3, 0, 0, 0, 2},
                {0, 2, 0, 4, 0, 0, 0, 0, 2, 0},
                {0, 0, 2, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 2, 2, 2, 2, 0, 0, 0},
        };
    }

    private int[][] getSecondLevel() {
        return new int[][]{
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 0},
                {2, 0, 0, 0, 0, 4, 4, 4, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 1, 0, 0, 3, 3, 3, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 0}
        };
    }

    private int[][] getThirdLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 4, 3, 0, 0, 2},
                {2, 0, 0, 0, 0, 4, 3, 0, 0, 2},
                {2, 0, 1, 0, 0, 4, 3, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
        };
    }

    private int[][] getLevel(File file) {
        List<Integer> countElemsList = new ArrayList<>();

        String text = getText(file);
        int indexRow = 0;
        int indexColumn = 0;

        int countElemsInLine = 0;
        int lineSeparators = 1;
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (symbol >= '0' && symbol <= '9') {
                countElemsInLine++;
            } else if (symbol == '\n') {
                countElemsList.add(countElemsInLine);
                countElemsInLine = 0;
                lineSeparators++;
            }
        }
        countElemsList.add(countElemsInLine);

        desktop = new int[lineSeparators][];
        for (int i = 0; i < desktop.length; i++) {
            desktop[i] = new int[countElemsList.get(i)];
        }
        countElemsList.clear();
        countElemsList = null;

        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (symbol >= '0' && symbol <= '9') {
                desktop[indexRow][indexColumn] = Integer.parseInt(symbol + "");
                indexColumn++;
            } else if (symbol == '\n') {
                indexColumn = 0;
                indexRow++;
            }
        }
        return desktop;
    }

    private String getText(File file) {
        FileReader fileReader = null;
        BufferedReader inputStream = null;
        String text = null;
        try {
            fileReader = new FileReader(file);
            inputStream = new BufferedReader(fileReader);

            List<String> list = new ArrayList<String>();

            String line;
            while ((line = inputStream.readLine()) != null) {
                list.add(line);
            }

            text = String.join("\n", list);
            list.clear();
            list = null;

            return text;

        } catch (FileNotFoundException fne) {
            System.out.println("Error");
            return null;
        } catch (IOException ioe) {
            System.out.println("Error in reading");
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                return null;
            }

            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioe) {
                return null;
            }
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberLevel() {
        return level - 1;
    }
}
