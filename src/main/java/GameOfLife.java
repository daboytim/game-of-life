import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLife {
    private boolean ready;
    private boolean[][] universe;
    private int width, height;

    public GameOfLife(String file) {
        width = 8;
        height = 6;
        universe = new boolean[height][width];
        try {
            ready = init(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean init(String file) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(file);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String currentLine;
        String[] states;
        int numLines = 0;
        while ((currentLine = buffer.readLine()) != null) {
            if (numLines > height) {
                break;
            }
            states = currentLine.split(",");
            if (states.length != width) {
                throw new IllegalArgumentException("Expected universe width of 8; got " + states.length);
            }
            for (int i = 0; i < width; i++) {
                universe[numLines][i] = !states[i].equals("0");
            }
            numLines++;
        }
        if (numLines != height) {
            throw new IllegalArgumentException("Expected universe height of 6; got " + numLines);
        }

        return true;
    }

    public boolean isReady() {
        return ready;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public boolean get(int x, int y) {
        return universe[x][y];
    }

    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print( (universe[i][j] ? '1' : '0') + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new GameOfLife("packedUniverse").print();
        new GameOfLife("testUniverse").print();
    }
}
