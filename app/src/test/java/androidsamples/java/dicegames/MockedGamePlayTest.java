package androidsamples.java.dicegames;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockedGamePlayTest {
    @Spy
    private GamesViewModel m = new GamesViewModel();

    @Test
    public void fourAlikeWinsWhenAllDiceReturn1() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 1});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        when(m.diceValues()).thenReturn(new int[]{2, 1, 1, 1});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.LOSS));
    }

    @Test
    public void twoAlikeWinsWhenD1D2Same() {
        when(m.diceValues()).thenReturn(new int[]{3, 3, 1, 4});

        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void threeAlikeWinsWhenD1D2D3Same() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 2});

        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.WIN));
    }
    @Test
    public void playTwoAlikeWinUpdatesBalanceCorrectly() {
        when(m.diceValues()).thenReturn(new int[]{2, 2, 3, 4});
        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(10);
        m.play();
        assertThat(m.balance(), is(120));
    }

    @Test
    public void playTwoAlikeLossUpdatesBalanceCorrectly() {
        when(m.diceValues()).thenReturn(new int[]{2, 5, 3, 4});
        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(10);
        m.play();
        assertThat(m.balance(), is(80));
    }

    @Test
    public void playThreeAlikeWinUpdatesBalanceCorrectly() {
        when(m.diceValues()).thenReturn(new int[]{6, 6, 6, 4});
        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(30);
        m.play();
        assertThat(m.balance(), is(190));
    }

    @Test
    public void playThreeAlikeLossUpdatesBalanceCorrectly() {
        when(m.diceValues()).thenReturn(new int[]{2, 5, 3, 4});
        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(30);
        m.play();
        assertThat(m.balance(), is(10));
    }

    @Test
    public void playFourAlikeWinUpdatesBalanceCorrectly() {
        when(m.diceValues()).thenReturn(new int[]{6, 6, 6, 6});
        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(25);
        m.play();
        assertThat(m.balance(), is(200));
    }

    @Test
    public void playFourAlikeLossUpdatesBalanceCorrectly() {
        when(m.diceValues()).thenReturn(new int[]{2, 5, 3, 4});
        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(25);
        m.play();
        assertThat(m.balance(), is(0));
    }

    @Test
    public void playWithMaximumWagerAndWinDoesNotOverflow() {
        when(m.diceValues()).thenReturn(new int[]{6, 6, 6, 6});
        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(Integer.MAX_VALUE);
        m.setWager(Integer.MAX_VALUE / 4);
        m.play();
        assertThat(m.balance(), is(Integer.MAX_VALUE));
    }

    @Test
    public void playTwoAlikeWithThreeMatchingDiceStillWins() {
        when(m.diceValues()).thenReturn(new int[]{3, 3, 3, 4});
        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(10);
        assertThat(m.play(), is(GameResult.WIN));
        assertThat(m.balance(), is(120));
    }

    @Test
    public void playThreeAlikeWithFourMatchingDiceStillWins() {
        when(m.diceValues()).thenReturn(new int[]{5, 5, 5, 5});
        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(10);
        assertThat(m.play(), is(GameResult.WIN));
        assertThat(m.balance(), is(130));
    }
    @Test
    public void consecutivePlaysMaintainCorrectBalance() {
        m.setBalance(100);
        m.setWager(10);
        m.setGameType(GameType.TWO_ALIKE);

        when(m.diceValues()).thenReturn(new int[]{2, 2, 3, 4});
        m.play();
        assertThat(m.balance(), is(120));

        when(m.diceValues()).thenReturn(new int[]{1, 2, 3, 4});
        m.play();

        m.setGameType(GameType.THREE_ALIKE);
        when(m.diceValues()).thenReturn(new int[]{1, 2, 3, 4});
        m.play();
        assertThat(m.balance(), is(70));
    }

}