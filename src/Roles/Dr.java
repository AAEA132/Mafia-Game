package Roles;

/**
 * The type Dr.
 */
public class Dr extends Role{
    private boolean isInGame;
    private boolean hasSavedHimSelf;

    /**
     * Instantiates a new Dr.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Dr(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Dr";
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
     * Gets that Dr has saved him self or not.
     *
     * @return that has saved him self or not
     */
    public boolean getHasSavedHimSelf() {
        return hasSavedHimSelf;
    }

    /**
     * Sets that has saved him self or not;
     *
     * @param hasSavedHimSelf that shows Dr has saved him self or not
     */
    public void setHasSavedHimSelf(boolean hasSavedHimSelf) {
        this.hasSavedHimSelf = hasSavedHimSelf;
    }
}
