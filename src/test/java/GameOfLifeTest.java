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
    public void shouldAdvanceUsingRuleOne() throws Exception {
        underTest.ruleOne();
        assertThat(underTest.getTransitionalState(), equalTo(new GameOfLife(height, width, "testUniverse.1")));
    }

    @Test
    public void shouldAdvanceUsingRuleTwo() throws Exception {
        underTest.ruleTwo();
        assertThat(underTest.getTransitionalState(), equalTo(new GameOfLife(height, width, "testUniverse.2")));
    }

    @Test
    public void shouldAdvanceUsingRuleThree() throws Exception {
        underTest.ruleThree();
        assertThat(underTest.getTransitionalState(), equalTo(new GameOfLife(height, width, "testUniverse.3")));
    }

    @Test
    public void shouldAdvanceUsingRuleFour() throws Exception {
        underTest.ruleFour();
        assertThat(underTest.getTransitionalState(), equalTo(new GameOfLife(height, width, "testUniverse.4")));
    }

    @Test
    public void shouldAdvanceUsingAllRules() throws Exception {
        underTest.advance();
        assertThat(underTest, equalTo(new GameOfLife(height, width, "testUniverseNextState")));
    }
}
