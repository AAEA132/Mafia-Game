package Roles;

/**
 * The type Mayor.
 */
public class Mayor extends Role{

    private boolean isInGame;

    /**
     * Instantiates a new Mayor.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Mayor(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Mayor";
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
