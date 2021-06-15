package Roles;

/**
 * The type Le professionnel.
 */
public class Le_Professionnel extends Role{

    private boolean isInGame;
    /**
     * The Shots.
     */
    int shots = 1;

    /**
     * Instantiates a new Le professionnel.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Le_Professionnel(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Le_Professionnel";
    }

    @Override
    public boolean getIsInGame() {
        return isInGame;
    }

    @Override
    public void setIsInGame(boolean state) {
        isInGame = state;
    }

    /**
     * Gets the current number of shots remained.
     *
     * @return the number of shots
     */
    public int getShots() {
        return shots;
    }

    /**
     * Sets the number of the shots.
     *
     * @param shots the number of shots which is going to be set
     */
    public void setShots(int shots) {
        this.shots = shots;
    }
}