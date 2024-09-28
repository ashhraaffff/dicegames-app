package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} shared between {@link androidx.fragment.app.Fragment}s.
 */
public class GamesViewModel extends ViewModel {
    public int balance;
    private Die mDie;
    private int mDieValue;
    public GameType gameType;
    private int wager;
    public int[] diceV;
    public GameResult gameResult;
    /**
     * The no argument constructor.
     */
    public GamesViewModel() {
        mDie = new Die6();
        balance = 0;
        mDieValue = 0;
        gameType=GameType.NONE;
        gameResult=GameResult.UNDECIDED;
        diceV=new int[4];
        wager=0;
    }

    /**
     * Reports the current balance.
     *
     */
    public int balance() {
        return balance;
    }

    /**
     * Rolls the {@link Die} in the wallet and implements the changes accordingly.
     */
    public void rollWalletDie() {

        mDie.roll();
        mDieValue = mDie.value();
        if(mDieValue==6) increaseBalance(5);
    }

    /**
     * Reports the current value of the {@link Die}.
     *
     */
    public int dieValue() {
        return mDieValue;
    }

    public int[] diceValues(){
        for(int i=0;i<4;i++){

            diceV[i]=(int)(Math.random()*6)+1;
        }
        return diceV;
    }


    public void setGameType(GameType gameType) {
        this.gameType=gameType;
    }

    public void setWager(int wager){
        this.wager=wager;
    }
    public int getWager(){
        return wager;
    }

    public int getBalance(){
        return balance;
    }

    public void setBalance(int value){
        balance=value;
    }

    public void increaseBalance(int amount){
        balance+=amount;
    }

    public void decreaseBalance(int amount){
        if(balance-amount>=0) balance-=amount;
        else balance=0;
    }

    public void setDie(Die die) {
        this.mDie = die;
    }

    public Die getDie() {return mDie;}
    public void updateBalance(int amount) {
        balance += amount;
    }

    public boolean isValidWager(){
        if(wager==0) return false;
        switch(gameType){
            case NONE:return true;
            case TWO_ALIKE:return (wager*2<=balance);
            case THREE_ALIKE:return (wager*3<=balance);
            case FOUR_ALIKE:return (wager*4<=balance);
        }
        return false;
    }

    public GameResult play() {
        diceV = diceValues();
        int targetCount = 0;

        if (!isValidWager()) {
            throw new IllegalStateException("Wager not set, can't play!");
        }
        else{
            switch (gameType) {
                case TWO_ALIKE:
                    targetCount = 2;
                    break;
                case THREE_ALIKE:
                    targetCount = 3;
                    break;
                case FOUR_ALIKE:
                    targetCount = 4;
                    break;
                case NONE:
                    throw new IllegalStateException("Game Type not set, can't play!");
            }

        }

        int[] counts = new int[7];
        for (int value : diceV) {
            counts[value]++;
        }
        boolean won = false;
        for (int count : counts) {
            if (count >= targetCount) {
                won = true;
                break;
            }
        }
        if (won) {
            increaseBalance(wager * targetCount);
            gameResult = GameResult.WIN;
        }
        else {
            decreaseBalance(wager * targetCount);
            gameResult = GameResult.LOSS;
        }
        return gameResult;
    }

}