package Roles;


/**
 * The type God father.
 */
public class GodFather extends Role{

    private boolean isInGame;

    /**
     * Instantiates a new God father.
     *
     * @param isInGame shows role is included in a game or not
     */
    public GodFather(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "GodFather";
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
