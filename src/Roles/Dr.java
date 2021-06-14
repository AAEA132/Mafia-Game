package Roles;


public class Dr extends Role {
    private boolean isInGame;

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

}
