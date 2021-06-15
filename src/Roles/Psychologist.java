package Roles;

/**
 * The type Psychologist.
 */
public class Psychologist extends Role{

    private boolean isInGame;

    /**
     * Instantiates a new Psychologist.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Psychologist(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Psychologist";
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
