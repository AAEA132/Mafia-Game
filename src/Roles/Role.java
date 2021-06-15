package Roles;

/**
 * The type Role.
 */
public abstract class Role {

    @Override
    public abstract String toString();

    /**
     * Gets that Role is included in game.
     *
     * @return that Role is included or not
     */
    public abstract boolean getIsInGame();

    /**
     * Sets that Role is included or not
     *
     * @param state the state of the Role
     */
    public abstract void setIsInGame(boolean state);

}
