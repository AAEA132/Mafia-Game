package Roles;

/**
 * The type Simple mafia.
 */
public class SimpleMafia extends Role{

    private boolean isInGame;

    /**
     * Instantiates a new Simple mafia.
     *
     * @param isInGame shows role is included in a game or not
     */
    public SimpleMafia(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "SimpleMafia";
    }

    @Override
    public boolean getIsInGame() {
        return isInGame;
    }

    @Override
    public void setIsInGame(boolean state) {
        isInGame = state;
    }
}
