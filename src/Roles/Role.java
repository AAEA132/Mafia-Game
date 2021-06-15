package Roles;

public abstract class Role {

    @Override
    public abstract String toString();

    public abstract boolean getIsInGame();
    public abstract void setIsInGame(boolean state);

}
