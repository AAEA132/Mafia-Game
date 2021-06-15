package Roles;


/**
 * The type Detective.
 */
public class Detective extends Role{
    private boolean isInGame;

    /**
     * Instantiates a new Detective.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Detective(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Detective";
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
