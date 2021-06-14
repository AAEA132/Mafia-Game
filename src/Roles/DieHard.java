package Roles;


public class DieHard extends Role {
    private boolean isInGame;

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
}
