import java.io.*;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLife {
    private boolean[][] universe, nextState;
    private int width, height;
    private String delim;

    private GameOfLife(boolean[][] state, int height, int width) {
        this.universe = state;
        this.height = height;
        this.width = width;
    }

    public GameOfLife(int height, int width, String file) {
        this.height = height;
        this.width = width;
        delim = ",";
        universe = new boolean[height][width];
        nextState = new boolean[height][width];
        try {
            init(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public GameOfLife(int height, int width, String file, String delim) {
        this.height = height;
        this.width = width;
        this.delim = delim;
        universe = new boolean[height][width];
        nextState = new boolean[height][width];
        try {
            init(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void init(String file) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(file);
        if (stream == null) {
            stream = new FileInputStream(file);
        }
        if (stream == null) {
            throw new FileNotFoundException(String.format("File not found; %s", file));
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String currentLine;
        String[] states;
        int numLines = 0;
        while ((currentLine = buffer.readLine()) != null) {
            if (numLines > height) {
                break;
            }
            states = currentLine.split(delim);
            if (states.length != width) {
                throw new IllegalArgumentException(String.format("Expected universe width of %d; got %d", width, states.length));
            }
            for (int i = 0; i < width; i++) {
                universe[numLines][i] = !states[i].equals("0");
            }
            numLines++;
        }
        if (numLines != height) {
            throw new IllegalArgumentException(String.format("Expected universe height of %d; got %d", height, numLines));
        }
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public boolean get(int y, int x) {
        return universe[y][x];
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
        ruleTwo();
        ruleThree();
        ruleFour();
        universe = nextState;
        nextState = new boolean[height][width];
    }

    public void ruleOne() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (universe[i][j] && countLiveNeighbors(i, j) < 2) {
                    nextState[i][j] = false;
                }
            }
        }
    }

    public void ruleTwo() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (universe[i][j] && countLiveNeighbors(i, j) > 3) {
                    nextState[i][j] = false;
                }
            }
        }
    }

    public void ruleThree() {
        int numNeighbors;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                numNeighbors = countLiveNeighbors(i, j);
                if (universe[i][j] && ( numNeighbors == 2 || numNeighbors == 3 ) ) {
                    nextState[i][j] = true;
                }
            }
        }
    }

    public void ruleFour() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!universe[i][j] && countLiveNeighbors(i, j) == 3 ) {
                    nextState[i][j] = true;
                }
            }
        }
    }

    public int countLiveNeighbors(int y, int x) {
        int liveNeighbors = 0;
        if (y == 0 && x == 0) {
            liveNeighbors += universe[y][x+1] ? 1 : 0;
            liveNeighbors += universe[y+1][x+1] ? 1 : 0;
            liveNeighbors += universe[y+1][x] ? 1 : 0;
        } else if (y == 0 && x == width-1) {
            liveNeighbors += universe[y][x-1] ? 1 : 0;
            liveNeighbors += universe[y+1][x-1] ? 1 : 0;
            liveNeighbors += universe[y+1][x] ? 1 : 0;
        } else if (y == height-1 && x == 0) {
            liveNeighbors += universe[y][x+1] ? 1 : 0;
            liveNeighbors += universe[y-1][x+1] ? 1 : 0;
            liveNeighbors += universe[y-1][x] ? 1 : 0;
        } else if (y == height-1 && x == width-1) {
            liveNeighbors += universe[y][x-1] ? 1 : 0;
            liveNeighbors += universe[y-1][x-1] ? 1 : 0;
            liveNeighbors += universe[y-1][x] ? 1 : 0;
        } else if (y == 0) {
            liveNeighbors += universe[y][x-1] ? 1 : 0;
            liveNeighbors += universe[y][x+1] ? 1 : 0;
            liveNeighbors += universe[y+1][x-1] ? 1 : 0;
            liveNeighbors += universe[y+1][x] ? 1 : 0;
            liveNeighbors += universe[y+1][x+1] ? 1 : 0;
        } else if (x == 0) {
            liveNeighbors += universe[y-1][x] ? 1 : 0;
            liveNeighbors += universe[y+1][x] ? 1 : 0;
            liveNeighbors += universe[y-1][x+1] ? 1 : 0;
            liveNeighbors += universe[y][x+1] ? 1 : 0;
            liveNeighbors += universe[y+1][x+1] ? 1 : 0;
        } else if (y == height-1) {
            liveNeighbors += universe[y][x-1] ? 1 : 0;
            liveNeighbors += universe[y][x+1] ? 1 : 0;
            liveNeighbors += universe[y-1][x-1] ? 1 : 0;
            liveNeighbors += universe[y-1][x] ? 1 : 0;
            liveNeighbors += universe[y-1][x+1] ? 1 : 0;
        } else if (x == width-1) {
            liveNeighbors += universe[y-1][x] ? 1 : 0;
            liveNeighbors += universe[y+1][x] ? 1 : 0;
            liveNeighbors += universe[y-1][x-1] ? 1 : 0;
            liveNeighbors += universe[y][x-1] ? 1 : 0;
            liveNeighbors += universe[y+1][x-1] ? 1 : 0;
        } else {
            liveNeighbors += universe[y-1][x-1] ? 1 : 0;
            liveNeighbors += universe[y-1][x] ? 1 : 0;
            liveNeighbors += universe[y-1][x+1] ? 1 : 0;
            liveNeighbors += universe[y][x-1] ? 1 : 0;
            liveNeighbors += universe[y][x+1] ? 1 : 0;
            liveNeighbors += universe[y+1][x-1] ? 1 : 0;
            liveNeighbors += universe[y+1][x] ? 1 : 0;
            liveNeighbors += universe[y+1][x+1] ? 1 : 0;
        }
        return liveNeighbors;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GameOfLife)) return false;
        final GameOfLife other = (GameOfLife) o;
        if (!other.canEqual((Object) this)) return false;
        if (!java.util.Arrays.deepEquals(this.universe, other.universe)) return false;
        if (this.width != other.width) return false;
        if (this.height != other.height) return false;
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof GameOfLife;
    }

    public GameOfLife getTransitionalState() {
        return new GameOfLife(nextState, height, width);
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 3 || args.length > 4) {
            usage();
            System.exit(1);
        }
        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        String file = args[2];
        checkFile(file);
        String delim = null;
        if (args.length > 3) {
            delim = args[3];
        }

        GameOfLife gameOfLifel;
        if (delim == null) {
            gameOfLifel = new GameOfLife(height, width, file);
        } else {
            gameOfLifel = new GameOfLife(height, width, file, delim);
        }

        System.out.println("Universe initialized:");
        gameOfLifel.print();

        do {
            System.out.print("Advancing to next state...");
            gameOfLifel.advance();
            System.out.println("done");
            gameOfLifel.print();
        } while (ask());

        System.exit(0);
    }

    public static void checkFile(String file) throws FileNotFoundException {
        if (ClassLoader.getSystemClassLoader().getResourceAsStream(file) == null) {
            File f = new File(file);
            if (!(f.exists() && f.isFile())) {
                throw new FileNotFoundException(file);
            }
        }
    }

    public static boolean ask() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Continue advancing the state?[Y/n] ");
        String ans = null;
        try {
            ans = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ans.equals("") || ans.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    public static void usage() {
        System.out.println("Conway's Game Of Life!");
        System.out.println();
        System.out.println("usage: <height> <width> <file> [delimiter]");
        System.out.println("    height    - the number of rows in your universe");
        System.out.println("    width     - the number of columns in your universe");
        System.out.println("    file      - a file containing the initial state of your universe");
        System.out.println("    delimiter - an optional pattern which separates cells in the universe (default: ',')");
    }
}
