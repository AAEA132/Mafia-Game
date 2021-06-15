package Roles;

public class Dr extends Role{
    private boolean isInGame;
    private boolean hasSavedHimSelf;

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

    public boolean getHasSavedHimSelf() {
        return hasSavedHimSelf;
    }

    public void setHasSavedHimSelf(boolean hasSavedHimSelf) {
        this.hasSavedHimSelf = hasSavedHimSelf;
    }
}
