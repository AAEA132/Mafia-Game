package Roles;


/**
 * The type Citizen.
 */
public class Citizen extends Role{
    private boolean isInGame;

    /**
     * Instantiates a new Citizen.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Citizen(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Citizen";
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
