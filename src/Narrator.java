import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import Roles.*;

/**
 * The type Narrator.
 */
public class Narrator extends Thread{

    private final Server server;
    /**
     * The Number of players.
     */
    int numberOfPlayers;
    /**
     * The Client handlers.
     */
    ArrayList<ClientHandler> clientHandlers;

    private ArrayList<Player> players;
    /**
     * The Mafia group.
     */
    ArrayList<Player> mafiaGroup;
    /**
     * The City group.
     */
    ArrayList<Player> cityGroup;
    /**
     * The Roles.
     */
    ArrayList<Role> roles;
    private ArrayList<Player> overallDeadPlayers;

    private boolean hasCitizen;
    private boolean hasDetective;
    private boolean hasDieHard;
    private boolean hasDr;
    private boolean hasDr_Lackter;
    private boolean hasGodFather;
    private boolean hasLe_Professionnel;
    private boolean hasMayor;
    private boolean hasPsychologist;
    private boolean hasSimpleMafia;


    private ArrayList<Player> lastNightDeadPlayers;
    private boolean mafiaShoot;
    private Player silenced = null;
    private boolean stateGameState;


    /**
     * Is state game state boolean.
     *
     * @return the boolean
     */
    public boolean isStateGameState() {
        return stateGameState;
    }

    /**
     * Sets state game state.
     *
     * @param stateGameState the state game state
     */
    public void setStateGameState(boolean stateGameState) {
        this.stateGameState = stateGameState;
    }

    /**
     * Is has citizen boolean.
     *
     * @return the boolean
     */
    public boolean isHasCitizen() {
        return hasCitizen;
    }

    /**
     * Sets has citizen.
     *
     * @param hasCitizen the has citizen
     */
    public void setHasCitizen(boolean hasCitizen) {
        this.hasCitizen = hasCitizen;
    }

    /**
     * Is has detective boolean.
     *
     * @return the boolean
     */
    public boolean isHasDetective() {
        return hasDetective;
    }

    /**
     * Sets has detective.
     *
     * @param hasDetective the has detective
     */
    public void setHasDetective(boolean hasDetective) {
        this.hasDetective = hasDetective;
    }

    /**
     * Is has die hard boolean.
     *
     * @return the boolean
     */
    public boolean isHasDieHard() {
        return hasDieHard;
    }

    /**
     * Sets has die hard.
     *
     * @param hasDieHard the has die hard
     */
    public void setHasDieHard(boolean hasDieHard) {
        this.hasDieHard = hasDieHard;
    }

    /**
     * Is has dr boolean.
     *
     * @return the boolean
     */
    public boolean isHasDr() {
        return hasDr;
    }

    /**
     * Sets has dr.
     *
     * @param hasDr the has dr
     */
    public void setHasDr(boolean hasDr) {
        this.hasDr = hasDr;
    }

    /**
     * Is has dr lackter boolean.
     *
     * @return the boolean
     */
    public boolean isHasDr_Lackter() {
        return hasDr_Lackter;
    }

    /**
     * Sets has dr lackter.
     *
     * @param hasDr_Lackter the has dr lackter
     */
    public void setHasDr_Lackter(boolean hasDr_Lackter) {
        this.hasDr_Lackter = hasDr_Lackter;
    }

    /**
     * Is has god father boolean.
     *
     * @return the boolean
     */
    public boolean isHasGodFather() {
        return hasGodFather;
    }

    /**
     * Sets has god father.
     *
     * @param hasGodFather the has god father
     */
    public void setHasGodFather(boolean hasGodFather) {
        this.hasGodFather = hasGodFather;
    }

    /**
     * Is has le professionnel boolean.
     *
     * @return the boolean
     */
    public boolean isHasLe_Professionnel() {
        return hasLe_Professionnel;
    }

    /**
     * Sets has le professionnel.
     *
     * @param hasLe_Professionnel the has le professionnel
     */
    public void setHasLe_Professionnel(boolean hasLe_Professionnel) {
        this.hasLe_Professionnel = hasLe_Professionnel;
    }

    /**
     * Is has mayor boolean.
     *
     * @return the boolean
     */
    public boolean isHasMayor() {
        return hasMayor;
    }

    /**
     * Sets has mayor.
     *
     * @param hasMayor the has mayor
     */
    public void setHasMayor(boolean hasMayor) {
        this.hasMayor = hasMayor;
    }

    /**
     * Is has psychologist boolean.
     *
     * @return the boolean
     */
    public boolean isHasPsychologist() {
        return hasPsychologist;
    }

    /**
     * Sets has psychologist.
     *
     * @param hasPsychologist the has psychologist
     */
    public void setHasPsychologist(boolean hasPsychologist) {
        this.hasPsychologist = hasPsychologist;
    }

    /**
     * Is has simple mafia boolean.
     *
     * @return the boolean
     */
    public boolean isHasSimpleMafia() {
        return hasSimpleMafia;
    }

    /**
     * Sets has simple mafia.
     *
     * @param hasSimpleMafia the has simple mafia
     */
    public void setHasSimpleMafia(boolean hasSimpleMafia) {
        this.hasSimpleMafia = hasSimpleMafia;
    }

    /**
     * Gets mafia shoot.
     *
     * @return the mafia shoot
     */
    public boolean getMafiaShoot() {
        return mafiaShoot;
    }

    /**
     * Sets mafia shoot.
     *
     * @param mafiaShoot the mafia shoot
     */
    public void setMafiaShoot(boolean mafiaShoot) {
        this.mafiaShoot = mafiaShoot;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Instantiates a new Narrator.
     *
     * @param server the server
     */
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
        overallDeadPlayers = new ArrayList<>();
        lastNightDeadPlayers = new ArrayList<>();
    }

    @Override
    public void run() {
        gameInit();
        firstNight();
        while (true){
            sendMsgToAllPlayers("Day started, You have 60s to chat");

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            voting();
            if (gameEnded()){
                break;
            }
            night();
            if (gameEnded())
                break;
            day();
        }
    }

    private void day() {
        handleDeadPlayers();
        sendPlayersWhoDiedToPlayers();
        if (stateGameState){
            sendNumberOfDeadCityAndMafia();
        }
        enableChatForAll();
        reset();
    }

    private void reset() {
        silenced = null;
        stateGameState = false;
        for (Player player : players){
            player.setVoteCounter(0);
        }
        lastNightDeadPlayers = new ArrayList<>();
        mafiaShoot = false;
    }

    private void sendNumberOfDeadCityAndMafia() {
        int city = 0;
        int mafia = 0;
        for (Player player : overallDeadPlayers){
            if (player.getRole() instanceof GodFather || player.getRole() instanceof Dr_Lackter || player.getRole() instanceof SimpleMafia){
                mafia++;
            }
            else if (player.getRole() instanceof Detective || player.getRole() instanceof Dr || player.getRole() instanceof Le_Professionnel || player.getRole() instanceof Psychologist || player.getRole() instanceof DieHard || player.getRole() instanceof Mayor ||player.getRole() instanceof Citizen){
                city++;
            }
        }
        sendMsgToAllPlayers("City died: "+city);
        sendMsgToAllPlayers("Mafia died: "+mafia);

    }

    private void sendPlayersWhoDiedToPlayers() {
        for (Player player : players){
            for (Player player1 : lastNightDeadPlayers){
                try {
                    player.getClientHandler().send(player1.getName() + " Died");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleDeadPlayers() {
        for (Player player : players){
            if (player.getIsShot()){
                if (!player.getIsHealed()){
                    if (player.getRole() instanceof DieHard){
                        if ((((DieHard) player.getRole()).getAbility()<=0)){
                            player.setInGame(false);
                            cityGroup.remove(player);
                            overallDeadPlayers.add(player);
                            lastNightDeadPlayers.add(player);
                        }
                        else if (((DieHard) player.getRole()).getAbility()>0){
                            ((DieHard) player.getRole()).setAbility(((DieHard) player.getRole()).getAbility()-1);
                        }
                    }
                    else {
                        if (mafiaGroup.contains(player)){
                            mafiaGroup.remove(player);
                        }
                        else if (cityGroup.contains(player)){
                            cityGroup.remove(player);
                        }
                        player.setInGame(false);
                        overallDeadPlayers.add(player);
                        lastNightDeadPlayers.add(player);
                    }
                }
            }
        }
    }

    private void night() {
        for (Player player : mafiaGroup){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new MafiaThread(this, player.getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hasDr_Lackter && findDr_Lackter().getIsInGame() && findDr_Lackter().getIsInServer()) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Dr_LackterThread(this, findDr_Lackter().getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hasDr && findDr().getIsInServer() && findDr().getIsInGame()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new DrThread(this, findDr().getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hasDetective && findDetective().getIsInServer() && findDetective().getIsInGame()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new DetectiveThread(this, findDetective().getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hasLe_Professionnel && findLe_Professionnel().getIsInServer() && findLe_Professionnel().getIsInGame()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Le_ProfessionnelThread(this, findLe_Professionnel().getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hasPsychologist && findPsychologist().getIsInServer() && findPsychologist().getIsInGame()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new PsychologistThread(this, findPsychologist().getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hasDieHard && findDieHard().getIsInServer() && findDieHard().getIsInGame()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new DieHardThread(this, findDieHard().getClientHandler()));
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Player findDieHard() {
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof DieHard) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private Player findPsychologist() {
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Psychologist) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private Player findLe_Professionnel() {
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Le_Professionnel) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private Player findDetective() {
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Detective) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private Player findDr_Lackter() {
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Dr_Lackter) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private boolean gameEnded() {
        synchronized (mafiaGroup) {
            synchronized (cityGroup) {
                if (mafiaGroup.size() >= cityGroup.size() || mafiaGroup.size() == 0)
                    return true;
                else
                    return false;
            }
        }
    }

    private void voting() {
        Narrator narrator = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                disableChatForAll();
                printAllInGamePlayers();
                synchronized (clientHandlers) {
                    for (ClientHandler clientHandler : clientHandlers) {
                        synchronized (clientHandler) {
                            synchronized (this) {
                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.execute(new VotingThread(clientHandler, narrator));
                            }
                        }
                    }
                }
                long start = System.currentTimeMillis();
                long end = start + 20*1000; // 20 seconds * 1000 ms/sec
                while (System.currentTimeMillis() < end) {}
                decideWhatHappens();
            }
        };
        thread.start();
    }

    private void decideWhatHappens() {
        if (checkWeHaveASigleMostVotedPlayer()) {
            if (hasMayor) {
                Player mayor = findMayor();
                if (mayor.getIsInGame() && mayor.getIsInServer() && mayor != null) {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new MayorThread(mayor.getClientHandler(), this, findMostVotedPalyer()));
                    try {
                        executorService.awaitTermination(30, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    findMostVotedPalyer().setInGame(false);
                }
            }
            else {
                findMostVotedPalyer().setInGame(false);
            }
            clearAllVotingCounter();
        }
        else {
            sendMsgToAllPlayers("No one is going to be voted out");
            clearAllVotingCounter();
        }
    }

    private void clearAllVotingCounter() {
        for (Player player : players){
            player.setVoteCounter(0);
        }
    }

    private boolean checkWeHaveASigleMostVotedPlayer() {
        Player player = findMostVotedPalyer();
        for (Player player1 : players){
            if (!player.getName().equals(player1.getName())){
                if (player.getVoteCounter()==player1.getVoteCounter())
                    return false;
            }
        }
        return true;
    }

    private Player findMostVotedPalyer() {
        Player player1 = new Player("a",null,null);
        player1.setVoteCounter(0);
        for (Player player : players){
            if (player.getVoteCounter()>player1.getVoteCounter()){
                player1 = player;
            }
        }
        return player1;
    }

    private Player findMayor() {
        Player player1 = null;
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    if (player.getRole() instanceof Mayor) {
                        player1 = player;
                        break;
                    }
                }
            }
        }
        return player1;
    }

    private void printAllInGamePlayers() {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                synchronized (clientHandler) {
                    synchronized (players) {
                        int n = 1;
                        for (Player player : players) {
                            synchronized (player) {
                                if (!clientHandler.getPlayer().getName().equals(player.getName()) && player.getIsInGame()) {
                                    try {
                                        clientHandler.send(n + ". " + player.getName());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    n++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void firstNight() {
        disableChatForAll();
        sendMsgToAllPlayers("First night started, You will be given your role and any information that is needed");
        sendEveryOneRole();
        sendMafiaTeammateListToMafiaGroup();
        sendDrNameToMayor();
        sendMsgToAllPlayers("First night finished, You can now chat");
        enableChatForAll();
    }

    private void sendEveryOneRole() {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                synchronized (clientHandler) {
                    try {
                        clientHandler.send(clientHandler.getPlayer().getName() + " : " + clientHandler.getPlayer().getRole().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void enableChatForAll() {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                synchronized (clientHandler) {
                    try {
                        if (silenced!=null) {
                            if (!clientHandler.getPlayer().getName().equals(silenced.getName()))
                                clientHandler.send("ENABLE_CHAT");
                        }
                        else {
                            clientHandler.send("ENABLE_CHAT");
                        }
                        synchronized (clientHandler.getPlayer()) {
                            clientHandler.getPlayer().setChatStatus(true);
                        }
                        clientHandler.resume();
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

    /**
     * Send mafia teammate list to mafia group.
     */
    public void sendMafiaTeammateListToMafiaGroup() {
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

    /**
     * Send msg to all players.
     *
     * @param msg the msg
     */
    public void sendMsgToAllPlayers(String msg) {
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
                            clientHandler.suspend();
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
            hasCitizen = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
        }
        else if (numberOfPlayers == 5){

            roles.add(new Citizen(true));
            hasCitizen = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
        }
        else if (numberOfPlayers == 6){
            roles.add(new Citizen(true));
            hasCitizen = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
            roles.add(new GodFather(true));
            hasGodFather =true;
        }
        else if (numberOfPlayers == 7){

            roles.add(new Citizen(true));
            hasCitizen = true;
            roles.add(new DieHard(true));
            hasDieHard = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
            roles.add(new GodFather(true));
            hasGodFather =true;
        }
        else if (numberOfPlayers == 8){
            roles.add(new Citizen(true));
            hasCitizen = true;
            roles.add(new Mayor(true));
            hasMayor = true;
            roles.add(new DieHard(true));
            hasDieHard = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
            roles.add(new GodFather(true));
            hasGodFather =true;
        }
        else if (numberOfPlayers == 9){
            roles.add(new Citizen(true));
            hasCitizen = true;
            roles.add(new Mayor(true));
            hasMayor = true;
            roles.add(new DieHard(true));
            hasDieHard = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
            roles.add(new GodFather(true));
            hasGodFather =true;
            roles.add(new Dr_Lackter(true));
            hasDr_Lackter = true;
        }
        else if (numberOfPlayers == 10){
            roles.add(new Citizen(true));
            hasCitizen = true;
            roles.add(new Le_Professionnel(true));
            hasLe_Professionnel = true;
            roles.add(new Mayor(true));
            hasMayor = true;
            roles.add(new DieHard(true));
            hasDieHard = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new SimpleMafia(true));
            hasSimpleMafia = true;
            roles.add(new GodFather(true));
            hasGodFather =true;
            roles.add(new Dr_Lackter(true));
            hasDr_Lackter = true;
        }
        else {
            roles.add(new GodFather(true));
            hasGodFather = true;
            roles.add(new Dr_Lackter(true));
            hasDr_Lackter = true;
            for (int i = 0; i < ((numberOfPlayers/3)-2); i++) {
                roles.add(new SimpleMafia(true));
                hasSimpleMafia = true;
            }
            roles.add(new Detective(true));
            hasDetective = true;
            roles.add(new Dr(true));
            hasDr = true;
            roles.add(new DieHard(true));
            hasDieHard = true;
            roles.add(new Mayor(true));
            hasMayor = true;
            roles.add(new Psychologist(true));
            hasPsychologist = true;
            roles.add(new Le_Professionnel(true));
            int m = roles.size();
            hasLe_Professionnel = true;
            for (int i = 0; i < (numberOfPlayers - m); i++) {
                roles.add(new Citizen(true));
                hasCitizen = true;
            }
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
        synchronized (players) {
            for (Player player : players) {
                synchronized (player) {
                    System.out.println("Player " + player.getName() + " is " + player.getRole());
                }
            }
        }
    }

    /**
     * Is vote valid boolean.
     *
     * @param clientVote the client vote
     * @return the boolean
     */
    public boolean isVoteValid(String clientVote) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                return true;
            }
        }
        return false;
    }

    /**
     * Add vote.
     *
     * @param clientVote the client vote
     */
    public void addVote(String clientVote) {
        for (Player player : players){
            if (player.getName().equals(clientVote)){
                player.addVote();
            }
        }
    }

    /**
     * Shoot.
     *
     * @param clientVote the client vote
     */
    public void shoot(String clientVote) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                player.setIsShot(true);
                mafiaShoot = true;
            }
        }
    }

    /**
     * Send msg to mafia group.
     *
     * @param s the s
     */
    public void sendMsgToMafiaGroup(String s) {
        for (Player player : mafiaGroup){
            try {
                player.getClientHandler().send(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Heal.
     *
     * @param clientVote the client vote
     */
    public void heal(String clientVote) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                player.setIsHealed(true);
            }
        }
    }

    /**
     * Is heal valid boolean.
     *
     * @param clientVote the client vote
     * @param dr         the dr
     * @return the boolean
     */
    public boolean isHealValid(String clientVote, Player dr) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                if (dr.getRole() instanceof Dr_Lackter) {
                    Dr_Lackter dr_lackter = (Dr_Lackter) dr.getRole();
                    if (dr_lackter.getHasSavedHimSelf())
                        return false;
                    else {
                        dr_lackter.setHasSavedHimSelf(true);
                    }
                }
                else if (dr.getRole() instanceof Dr) {
                    Dr dr1 = (Dr) dr.getRole();
                    if (dr1.getHasSavedHimSelf())
                        return false;
                    else {
                        dr1.setHasSavedHimSelf(true);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Is detect valid boolean.
     *
     * @param clientVote the client vote
     * @param detective  the detective
     * @return the boolean
     */
    public boolean isDetectValid(String clientVote, Player detective) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                return true;
            }
        }
        return false;
    }

    /**
     * Detect.
     *
     * @param clientVote    the client vote
     * @param clientHandler the client handler
     */
    public void detect(String clientVote, ClientHandler clientHandler) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                if (cityGroup.contains(player)){
                    try {
                        clientHandler.send(clientVote + " is city");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (mafiaGroup.contains(player)){
                if (player.getRole() instanceof GodFather){
                    try {
                        clientHandler.send(clientVote + " is city");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        clientHandler.send(clientVote + " is mafia");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Is shot valid boolean.
     *
     * @param clientVote the client vote
     * @param pro        the pro
     * @return the boolean
     */
    public boolean isShotValid(String clientVote, Player pro) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                return true;
            }
        }
        return false;
    }

    /**
     * Shoot p.
     *
     * @param clientVote    the client vote
     * @param clientHandler the client handler
     */
    public void shootP(String clientVote, ClientHandler clientHandler) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                if (cityGroup.contains(player)){
                    clientHandler.getPlayer().setInGame(false);
                }
                else if (mafiaGroup.contains(player)){
                    player.setIsShot(true);
                    ((Le_Professionnel)clientHandler.getPlayer().getRole()).setShots(((Le_Professionnel)clientHandler.getPlayer().getRole()).getShots()-1);
                }
            }
        }
    }

    /**
     * Silence.
     *
     * @param clientVote    the client vote
     * @param clientHandler the client handler
     */
    public void silence(String clientVote, ClientHandler clientHandler) {
        for (Player player : players){
            if (player.getName().equals(clientVote) && player.getIsInGame() && player.getIsInServer()){
                silenced = player;
            }
        }
    }
}

