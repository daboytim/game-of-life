import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLifeTest {
    private int height = 6, width = 8;
    private GameOfLife underTest;

    @Before
    public void setup() {
        underTest = new GameOfLife(height, width, "testUniverse");
    }

    @Test
    public void shouldInitializeUniverseFromFile() throws Exception {
        underTest = new GameOfLife(height, width, "packedUniverse");
        for (int i = 0; i < underTest.height(); i++){
            for (int j = 0; j < underTest.width(); j++) {
                assertThat(underTest.get(i, j), equalTo(true));
            }
        }
    }

    @Test
    public void shouldInitializeUniverseUsingCustomDelim() throws Exception {
        underTest = new GameOfLife(height, width, "customDelim", " ");
        for (int i = 0; i < underTest.height(); i++){
            for (int j = 0; j < underTest.width(); j++) {
                assertThat(underTest.get(i, j), equalTo(true));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithNonExistentFile() throws Exception {
        new GameOfLife(height, width, "notARealFile");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithSmallUniverse() throws Exception {
        new GameOfLife(1, -3, "packedUniverse");
    }

    @Test
    public void shouldCountLiveNeighbors() throws Exception {
        int y = underTest.height()-1;
        int x = underTest.width()-1;
        //corners
        assertThat(underTest.countLiveNeighbors(0, 0), equalTo(2));
        assertThat(underTest.countLiveNeighbors(0, x), equalTo(2));
        assertThat(underTest.countLiveNeighbors(y, 0), equalTo(0));
        assertThat(underTest.countLiveNeighbors(y, x), equalTo(0));
        //middle of edges
        assertThat(underTest.countLiveNeighbors(0, x/2), equalTo(1));
        assertThat(underTest.countLiveNeighbors(y, x/2), equalTo(3));
        assertThat(underTest.countLiveNeighbors(y/2, 0), equalTo(2));
        assertThat(underTest.countLiveNeighbors(y/2, x), equalTo(2));
        //middle of universe
        assertThat(underTest.countLiveNeighbors(y/2, x/2), equalTo(1));
    }

    @Test
    public void shouldAdvanceUsingAllRules() throws Exception {
        underTest.advance();
        assertThat(underTest, equalTo(new GameOfLife(height, width, "testUniverseNextState")));
    }
}
