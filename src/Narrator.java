
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import Roles.*;



public class Narrator extends Thread{
    private final Server server;
    int numberOfPlayers;
    ArrayList<ClientHandler> clientHandlers;
    ArrayList<Player> players;
    ArrayList<Player> mafiaGroup;
    ArrayList<Player> cityGroup;
    ArrayList<Role> roles;

    public Narrator(Server server) {
        this.server = server;
        synchronized (this.server) {
            this.clientHandlers = server.getClientHandlers();
            this.players = server.getPlayers();
            this.numberOfPlayers = server.getNumber_of_players();
        }
        mafiaGroup = new ArrayList<>();
        cityGroup = new ArrayList<>();
        roles = new ArrayList<>();

    }

    @Override
    public void run() {
        gameInit();
        firstNight();
    }

//    public void phaseMethod(){
//
////        while (true){
////        }
//    }

    private void firstNight() {
        disableChatForAll();
        sendMsgToAllPlayers("First night started, You will be given your role and any information that is needed");
        sendMafiaTeammateListToMafiaGroup();
        sendDrNameToMayor();
        sendMsgToAllPlayers("First night finished, You can now chat");
        enableChatForAll();
    }

    private void enableChatForAll() {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                synchronized (clientHandler) {
                    try {
                        clientHandler.send("ENABLE_CHAT");
                        synchronized (clientHandler.getPlayer()) {
                            clientHandler.getPlayer().setChatStatus(true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sendDrNameToMayor() {
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Mayor) {
                        try {
                            synchronized (player.getClientHandler()) {
                                player.getClientHandler().send(findDr().getName() + " : " + findDr().getRole().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    private Player findDr(){
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Dr) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private void sendMafiaTeammateListToMafiaGroup() {
        synchronized (mafiaGroup) {
            for (Player player : mafiaGroup) {
                synchronized (player) {
                    ClientHandler clientHandler = player.getClientHandler();
                    synchronized (clientHandler) {
                        for (Player player1 : mafiaGroup) {
                            synchronized (player1) {
                                if (!player.getName().equals(player1.getName())) {
                                    try {
                                        clientHandler.send(player1.getName() + " : " + player1.getRole().toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void sendMsgToAllPlayers(String msg) {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                synchronized (clientHandler) {
                    try {
                        clientHandler.send(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void disableChatForAll() {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                synchronized (clientHandler) {
                    try {
                        clientHandler.send("DISABLE_CHAT");
                        synchronized (clientHandler.getPlayer()) {
                            clientHandler.getPlayer().setChatStatus(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void gameInit() {
        synchronized (players) {
            Collections.shuffle(players);
        }
        if (numberOfPlayers == 4){

            roles.add(new Citizen(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
//            Collections.shuffle(roles);
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen = new Citizen(true);
////            players.get(0).setRole(citizen);
////
////            Role detective = new Detective(true);
////            players.get(1).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(2).setRole(dr);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(3).setRole(simpleMafia);
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else if (numberOfPlayers == 5){

            roles.add(new Citizen(true));
            roles.add(new Psychologist(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
//            int n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen1 = new Citizen(true);
////            players.get(0).setRole(citizen1);
////
////            Role citizen2 = new Citizen(true);
////            players.get(1).setRole(citizen2);
////
////            Role detective = new Detective(true);
////            players.get(2).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(3).setRole(dr);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(4).setRole(simpleMafia);
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else if (numberOfPlayers == 6){
            roles.add(new Citizen(true));
            roles.add(new Psychologist(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
            roles.add(new GodFather(true));
//            int n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen1 = new Citizen(true);
////            players.get(0).setRole(citizen1);
////
////            Role citizen2 = new Citizen(true);
////            players.get(1).setRole(citizen2);
////
////            Role detective = new Detective(true);
////            players.get(2).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(3).setRole(dr);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(4).setRole(simpleMafia);
////
////            Role godFather = new GodFather(true);
////            players.get(5).setRole(godFather);
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else if (numberOfPlayers == 7){

            roles.add(new Citizen(true));
            roles.add(new DieHard(true));
            roles.add(new Psychologist(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
            roles.add(new GodFather(true));
//            int n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen1 = new Citizen(true);
////            players.get(0).setRole(citizen1);
////
////            Role citizen2 = new Citizen(true);
////            players.get(1).setRole(citizen2);
////
////            Role detective = new Detective(true);
////            players.get(2).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(3).setRole(dr);
////
////            Role psychologist = new Psychologist(true);
////            players.get(4).setRole(psychologist);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(5).setRole(simpleMafia);
////
////            Role godFather = new GodFather(true);
////            players.get(6).setRole(godFather);
//
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else if (numberOfPlayers == 8){
            roles.add(new Citizen(true));
            roles.add(new Mayor(true));
            roles.add(new DieHard(true));
            roles.add(new Psychologist(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
            roles.add(new GodFather(true));
//            int n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen1 = new Citizen(true);
////            players.get(0).setRole(citizen1);
////
////            Role citizen2 = new Citizen(true);
////            players.get(1).setRole(citizen2);
////
////            Role detective = new Detective(true);
////            players.get(2).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(3).setRole(dr);
////
////            Role psychologist = new Psychologist(true);
////            players.get(4).setRole(psychologist);
////
////            Role mayor = new Mayor(true);
////            players.get(5).setRole(mayor);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(6).setRole(simpleMafia);
////
////            Role godFather = new GodFather(true);
////            players.get(7).setRole(godFather);
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else if (numberOfPlayers == 9){
            roles.add(new Citizen(true));
            roles.add(new Mayor(true));
            roles.add(new DieHard(true));
            roles.add(new Psychologist(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
            roles.add(new GodFather(true));
            roles.add(new Dr_Lackter(true));
//            int n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen = new Citizen(true);
////            players.get(0).setRole(citizen);
////
////            Role le_professionnel = new Le_Professionnel(true);
////            players.get(1).setRole(le_professionnel);
////
////            Role detective = new Detective(true);
////            players.get(2).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(3).setRole(dr);
////
////            Role psychologist = new Psychologist(true);
////            players.get(4).setRole(psychologist);
////
////            Role mayor = new Mayor(true);
////            players.get(5).setRole(mayor);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(6).setRole(simpleMafia);
////
////            Role godFather = new GodFather(true);
////            players.get(7).setRole(godFather);
////
////            Role dr_Lackter = new Dr_Lackter(true);
////            players.get(8).setRole(dr_Lackter);
//
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else if (numberOfPlayers == 10){
            roles.add(new Citizen(true));
            roles.add(new Le_Professionnel(true));
            roles.add(new Mayor(true));
            roles.add(new DieHard(true));
            roles.add(new Psychologist(true));
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new SimpleMafia(true));
            roles.add(new GodFather(true));
            roles.add(new Dr_Lackter(true));
//            int n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
////            Role citizen = new Citizen(true);
////            players.get(0).setRole(citizen);
////
////            Role le_professionnel = new Le_Professionnel(true);
////            players.get(1).setRole(le_professionnel);
////
////            Role detective = new Detective(true);
////            players.get(2).setRole(detective);
////
////            Role dr = new Dr(true);
////            players.get(3).setRole(dr);
////
////            Role psychologist = new Psychologist(true);
////            players.get(4).setRole(psychologist);
////
////            Role mayor = new Mayor(true);
////            players.get(5).setRole(mayor);
////
////            Role dieHard = new DieHard(true);
////            players.get(6).setRole(dieHard);
////
////            Role simpleMafia = new SimpleMafia(true);
////            players.get(7).setRole(simpleMafia);
////
////            Role godFather = new GodFather(true);
////            players.get(8).setRole(godFather);
////
////            Role dr_Lackter = new Dr_Lackter(true);
////            players.get(9).setRole(dr_Lackter);
//
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        else {
            roles.add(new GodFather(true));
            roles.add(new Dr_Lackter(true));
            for (int i = 0; i < ((numberOfPlayers/3)-2); i++) {
                roles.add(new SimpleMafia(true));
            }
            roles.add(new Detective(true));
            roles.add(new Dr(true));
            roles.add(new DieHard(true));
            roles.add(new Mayor(true));
            roles.add(new Psychologist(true));
            roles.add(new Le_Professionnel(true));
            int m = roles.size();
            for (int i = 0; i < (numberOfPlayers - m); i++) {
                roles.add(new Citizen(true));
            }
//            Collections.shuffle(roles);
//            n = 0;
//            for (Player player : players){
//                player.setRole(roles.get(n));
//                n++;
//            }
//            for (Player player : players){
//                System.out.println("Player " + player.getName() + " is " + player.getRole());
//            }
        }
        synchronized (roles) {
            Collections.shuffle(roles);
        }
        int n = 0;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    player.setRole(roles.get(n));
                    if (player.getRole() instanceof SimpleMafia || player.getRole() instanceof GodFather || player.getRole() instanceof Dr_Lackter){
                        synchronized (mafiaGroup){
                            mafiaGroup.add(player);
                        }
                    }
                    else{
                        synchronized (cityGroup){
                            cityGroup.add(player);
                        }
                    }
                    n++;
                }
            }
        }
//            Role citizen = new Citizen(true);
//            players.get(0).setRole(citizen);
//
//            Role detective = new Detective(true);
//            players.get(1).setRole(detective);
//
//            Role dr = new Dr(true);
//            players.get(2).setRole(dr);
//
//            Role simpleMafia = new SimpleMafia(true);
//            players.get(3).setRole(simpleMafia);
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    System.out.println("Player " + player.getName() + " is " + player.getRole());
                }
            }
        }
    }
}

