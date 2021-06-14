package Roles;

public abstract class Role {
//    private boolean isInGame;
//
//    public Role(boolean isInGame) {
//        this.isInGame = isInGame;
//    }

    @Override
    public abstract String toString();

    public abstract boolean getIsInGame();
    public abstract void setIsInGame(boolean state);

}
