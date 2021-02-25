package iut.projet.metier;

public class Player {
    //Identificateur du joueur
    private String playerId;
    private String playerName;

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }
}
