import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by derek on 2/14/17.
 */
public class GameOfLifeTest {
    @Test
    public void shouldInitializeUniverse() throws Exception {
        GameOfLIfe underTest = new GameOfLIfe();
        assertThat(underTest.isReady(), equalTo(true));
    }
}
