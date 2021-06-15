package Roles;

public class Dr_Lackter extends Role{

    private boolean isInGame;
    private boolean hasSavedHimSelf;
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

    public boolean getHasSavedHimSelf() {
        return hasSavedHimSelf;
    }

    public void setHasSavedHimSelf(boolean hasSavedHimSelf) {
        this.hasSavedHimSelf = hasSavedHimSelf;
    }
}
