package Roles;

public class Psychologist extends Role{

    private boolean isInGame;

    public Psychologist(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Psychologist";
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
