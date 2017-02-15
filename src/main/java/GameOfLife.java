/**
 * Created by derek on 2/14/17.
 */
public class GameOfLife {
    private boolean ready;
    private boolean[][] universe;

    public GameOfLife() {
        ready = init();
    }

    private boolean init() {
        universe = new boolean[8][6];
        return true;
    }

    public boolean isReady() {
        return ready;
    }

    public static void main(String[] args) {

    }
}
