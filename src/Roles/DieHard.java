package Roles;


public class DieHard extends Role{
    private boolean isInGame;
    private int ability = 2;
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

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }
}
