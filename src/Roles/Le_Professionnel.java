package Roles;


public class Le_Professionnel extends Role {


    private boolean isInGame;

    public Le_Professionnel(boolean isInGame) {
        this.isInGame = isInGame;
    }

    @Override
    public String toString() {
        return "Le_Professionnel";
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
