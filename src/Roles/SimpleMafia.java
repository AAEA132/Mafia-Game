package Roles;
public class SimpleMafia extends Role{

    private boolean isInGame;

    public SimpleMafia(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "SimpleMafia";
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
