package Roles;


public class GodFather extends Role{

    private boolean isInGame;

    public GodFather(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "GodFather";
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
