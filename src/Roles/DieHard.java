package Roles;


/**
 * The type Die hard.
 */
public class DieHard extends Role{
    private boolean isInGame;
    private int ability = 2;

    /**
     * Instantiates a new Die hard.
     *
     * @param isInGame shows role is included in a game or not
     */
    public DieHard(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "DieHard";
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
     * Gets ability.
     *
     * @return the current number of ability
     */
    public int getAbility() {
        return ability;
    }

    /**
     * Sets ability.
     *
     * @param ability the ability of DieHard
     */
    public void setAbility(int ability) {
        this.ability = ability;
    }
}
