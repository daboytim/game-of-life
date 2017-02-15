import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLife {
    private boolean ready;
    private boolean[][] universe, nextState;
    private int width, height;

    private GameOfLife(boolean[][] state, int height, int width) {
        this.universe = state;
        this.height = height;
        this.width = width;
        ready = true;
    }

    public GameOfLife(String file) {
        width = 8;
        height = 6;
        universe = new boolean[height][width];
        nextState = new boolean[height][width];
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

    public void advance() {
        ruleOne();
    }

    public void ruleOne() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (universe[i][j] && countLiveNeighbors(i, j) < 2) {
                    nextState[i][j] = false;
                } else if (universe[i][j]) {
                    nextState[i][j] = true;
                }
            }
        }
    }

    private int countLiveNeighbors(int x, int y) {
        int liveNeighbors = 0;
        if (x == 0 && y == 0) {
            liveNeighbors += universe[x][y+1] ? 1 : 0;
            liveNeighbors += universe[x+1][y+1] ? 1 : 0;
            liveNeighbors += universe[x+1][y] ? 1 : 0;
        } else if (x == 0 && y == width-1) {
            liveNeighbors += universe[x][y-1] ? 1 : 0;
            liveNeighbors += universe[x+1][y-1] ? 1 : 0;
            liveNeighbors += universe[x+1][y] ? 1 : 0;
        } else if (x == height-1 && y == 0) {
            liveNeighbors += universe[x][y+1] ? 1 : 0;
            liveNeighbors += universe[x-1][y+1] ? 1 : 0;
            liveNeighbors += universe[x-1][y] ? 1 : 0;
        } else if (x == height-1 && y == width-1) {
            liveNeighbors += universe[x][y-1] ? 1 : 0;
            liveNeighbors += universe[x-1][y-1] ? 1 : 0;
            liveNeighbors += universe[x-1][y] ? 1 : 0;
        } else if (x == 0) {
            liveNeighbors += universe[x][y-1] ? 1 : 0;
            liveNeighbors += universe[x][y+1] ? 1 : 0;
            liveNeighbors += universe[x+1][y-1] ? 1 : 0;
            liveNeighbors += universe[x+1][y] ? 1 : 0;
            liveNeighbors += universe[x+1][y+1] ? 1 : 0;
        } else if (y == 0) {
            liveNeighbors += universe[x-1][y] ? 1 : 0;
            liveNeighbors += universe[x+1][y] ? 1 : 0;
            liveNeighbors += universe[x-1][y+1] ? 1 : 0;
            liveNeighbors += universe[x][y+1] ? 1 : 0;
            liveNeighbors += universe[x+1][y+1] ? 1 : 0;
        } else if (x == height-1) {
            liveNeighbors += universe[x][y-1] ? 1 : 0;
            liveNeighbors += universe[x][y+1] ? 1 : 0;
            liveNeighbors += universe[x-1][y-1] ? 1 : 0;
            liveNeighbors += universe[x-1][y] ? 1 : 0;
            liveNeighbors += universe[x-1][y+1] ? 1 : 0;
        } else if (y == width-1) {
            liveNeighbors += universe[x-1][y] ? 1 : 0;
            liveNeighbors += universe[x+1][y] ? 1 : 0;
            liveNeighbors += universe[x-1][y-1] ? 1 : 0;
            liveNeighbors += universe[x][y-1] ? 1 : 0;
            liveNeighbors += universe[x+1][y-1] ? 1 : 0;
        } else {
            liveNeighbors += universe[x-1][y-1] ? 1 : 0;
            liveNeighbors += universe[x-1][y] ? 1 : 0;
            liveNeighbors += universe[x-1][y+1] ? 1 : 0;
            liveNeighbors += universe[x][y-1] ? 1 : 0;
            liveNeighbors += universe[x][y+1] ? 1 : 0;
            liveNeighbors += universe[x+1][y-1] ? 1 : 0;
            liveNeighbors += universe[x+1][y] ? 1 : 0;
            liveNeighbors += universe[x+1][y+1] ? 1 : 0;
        }
        return liveNeighbors;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GameOfLife)) return false;
        final GameOfLife other = (GameOfLife) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isReady() != other.isReady()) return false;
        if (!java.util.Arrays.deepEquals(this.universe, other.universe)) return false;
        if (this.width != other.width) return false;
        if (this.height != other.height) return false;
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof GameOfLife;
    }

    public GameOfLife getTrasitionalState() {
        return new GameOfLife(nextState, height, width);
    }

    public static void main(String[] args) {
        GameOfLife gol = new GameOfLife("testUniverse");
        gol.print();
        gol.advance();
        gol.getTrasitionalState().print();
    }
}
