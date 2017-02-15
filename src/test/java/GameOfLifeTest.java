import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLifeTest {
    private GameOfLife underTest;

    @Test
    public void shouldInitializeUniverse() throws Exception {
        underTest = new GameOfLife();
        assertThat(underTest.isReady(), equalTo(true));
    }

    @Test
    public void shouldInitializeUniverseFromFile() throws Exception {
        underTest = new GameOfLife("packedUniverse");
        for (int i = 0; i < underTest.width(); i++){
            for (int j = 0; j < underTest.height(); i++) {
                assertThat(underTest.get(i, j), equalTo(true));
            }
        }
    }
}
