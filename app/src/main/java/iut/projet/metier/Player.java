package iut.projet.metier;

public class Player {
    //Identificateur du joueur
    private String playerId;
        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }
        public String getPlayerId() {
            return playerId;
        }

    private final String playerName;
        public String getPlayerName() {
        return playerName;
    }

    private boolean isHost;
        public boolean isHost() { return isHost; }
        public void setHost(boolean host) { isHost = host; }


    private boolean ready;
        public void setReady(boolean ready) {this.ready = ready;}

    private Room currentRoom;
        public Room getCurrentRoom() {
            return currentRoom;
        }
        public void setCurrentRoom(Room currentRoom) {
            this.currentRoom = currentRoom;
        }

    public Player(String playerName,boolean host) {
        this.ready = false;
        this.playerName = playerName;
        this.isHost= host;
    }



    public void createRoom(){
        currentRoom = new Room(this);
    }
    public void connectToRoom(String roomCode){
        currentRoom = new Room(roomCode, this);
    }
}
