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
        underTest = new GameOfLife("testUniverse");
    }

    @Test
    public void shouldInitializeUniverse() throws Exception {
        assertThat(underTest.isReady(), equalTo(true));
    }

    @Test
    public void shouldInitializeUniverseFromFile() throws Exception {
        underTest = new GameOfLife("packedUniverse");
        for (int i = 0; i < underTest.height(); i++){
            for (int j = 0; j < underTest.width(); j++) {
                assertThat(underTest.get(i, j), equalTo(true));
            }
        }
    }

    @Test
    public void shouldAdvanceUsingRuleOne() throws Exception {
        underTest.ruleOne();
        assertThat(underTest.getTrasitionalState(), equalTo(new GameOfLife("testUniverse.1")));
    }

    @Test
    public void shouldAdvanceUsingRuleTwo() throws Exception {
        underTest.ruleTwo();
        assertThat(underTest.getTrasitionalState(), equalTo(new GameOfLife("testUniverse.2")));
    }
}
