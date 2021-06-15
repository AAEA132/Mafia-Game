import java.io.IOException;
import java.util.ArrayList;

import Roles.Dr_Lackter;
import Roles.GodFather;
import Roles.SimpleMafia;

public class MafiaThread extends Thread{
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    public MafiaThread(Narrator narrator,ClientHandler clientHandler) {
        this.narrator = narrator;
        this.clientHandler = clientHandler;
        players = narrator.getPlayers();
    }

    @Override
    public void run() {
        int n = 1;
        for (Player player : players){
            if(!(clientHandler.getPlayer().getName().equals(player.getName()))){
                try {
                    player.getClientHandler().send(n + ". " + player.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                n++;
            }
        }
        if (clientHandler.getPlayer().getRole() instanceof GodFather){
            try {
                String clientVote;
                clientHandler.send("Please enter the username of the player you want to shoot");
                while (true) {
                    clientVote = clientHandler.read();
                    if (narrator.isVoteValid(clientVote)){
                        narrator.shoot(clientVote);
                        narrator.sendMsgToMafiaGroup("GodFather " + clientHandler.getPlayer().getName() + " shot: " + clientVote);
                        break;
                    }
                    else {
                        clientHandler.send("invalid");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (clientHandler.getPlayer().getRole() instanceof Dr_Lackter){
            if ( !narrator.getMafiaShoot() && !narrator.isHasGodFather()){
                try {
                    String clientVote;
                    clientHandler.send("Please enter the username of the player you want to shoot");
                    while (true) {
                        clientVote = clientHandler.read();
                        if (narrator.isVoteValid(clientVote)){
                            narrator.shoot(clientVote);
                            narrator.sendMsgToMafiaGroup("Dr_Lackter " + clientHandler.getPlayer().getName() + " shot: " + clientVote);
                            break;
                        }
                        else {
                            clientHandler.send("invalid");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    String clientVote;
                    clientHandler.send("Please enter the username of the player you suggest to shoot");
                    while (true) {
                        clientVote = clientHandler.read();
                        if (narrator.isVoteValid(clientVote)){
                            narrator.shoot(clientVote);
                            narrator.sendMsgToMafiaGroup("Dr_Lackter " + clientHandler.getPlayer().getName() + " suggest: " + clientVote);
                            break;
                        }
                        else {
                            clientHandler.send("invalid");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (clientHandler.getPlayer().getRole() instanceof SimpleMafia){
            if ( !narrator.getMafiaShoot() && !narrator.isHasGodFather() && !narrator.isHasDr_Lackter()){
                try {
                    String clientVote;
                    clientHandler.send("Please enter the username of the player you want to shoot");
                    while (true) {
                        clientVote = clientHandler.read();
                        if (narrator.isVoteValid(clientVote)){
                            narrator.shoot(clientVote);
                            narrator.sendMsgToMafiaGroup("SimpleMafia " + clientHandler.getPlayer().getName() + " shot: " + clientVote);
                            break;
                        }
                        else {
                            clientHandler.send("invalid");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    String clientVote;
                    clientHandler.send("Please enter the username of the player you suggest to shoot");
                    while (true) {
                        clientVote = clientHandler.read();
                        if (narrator.isVoteValid(clientVote)){
                            narrator.shoot(clientVote);
                            narrator.sendMsgToMafiaGroup("SimpleMafia " + clientHandler.getPlayer().getName() + "  suggest: " + clientVote);
                            break;
                        }
                        else {
                            clientHandler.send("invalid");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
