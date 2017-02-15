import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLifeTest {
    private GameOfLife underTest;

    @Before
    public void setup() {
        underTest = new GameOfLife("packedUniverse");
    }

    @Test
    public void shouldInitializeUniverse() throws Exception {
        assertThat(underTest.isReady(), equalTo(true));
    }

    @Test
    public void shouldInitializeUniverseFromFile() throws Exception {
        for (int i = 0; i < underTest.height(); i++){
            for (int j = 0; j < underTest.width(); j++) {
                assertThat(underTest.get(i, j), equalTo(true));
            }
        }
    }
}
