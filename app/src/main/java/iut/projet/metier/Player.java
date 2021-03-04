package iut.projet.metier;

public class Player {
    //Identificateur du joueur
    private String playerId;
    private String playerName;
    private boolean ready;

    private Room currentRoom;

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player(String playerName)
    {
        this.ready = false;
        this.playerName = playerName;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void createRoom(){
        currentRoom = new Room(this);
    }
    public void connectToRoom(String roomCode, RoomStateListener rsl){
        currentRoom = new Room(roomCode, this, rsl);
    }
}
