package Roles;

/**
 * The type Dr lackter.
 */
public class Dr_Lackter extends Role{

    private boolean isInGame;
    private boolean hasSavedHimSelf;

    /**
     * Instantiates a new Dr lackter.
     *
     * @param isInGame shows role is included in a game or not
     */
    public Dr_Lackter(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Dr_Lackter";
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
     * Gets that Dr_Lackter has saved him self or not.
     *
     * @return that has saved him self or not
     */
    public boolean getHasSavedHimSelf() {
        return hasSavedHimSelf;
    }

    /**
     * Sets that has saved him self or not;
     *
     * @param hasSavedHimSelf that shows Dr_Lackter has saved him self or not
     */
    public void setHasSavedHimSelf(boolean hasSavedHimSelf) {
        this.hasSavedHimSelf = hasSavedHimSelf;
    }
}
