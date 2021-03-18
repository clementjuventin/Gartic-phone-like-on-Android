package iut.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import iut.projet.R;
import iut.projet.controller.FirebaseDatabaseHelper;
import iut.projet.controller.RoomDataListener;
import iut.projet.model.metier.Player;
import iut.projet.model.metier.Room;

public class ResultViewStart extends AppCompatActivity {
    private Player player;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.result_start_activity);

        TextView expressionTv = ((TextView) findViewById(R.id.expression_result_start_activity));
        TextView usernameTv = ((TextView) findViewById(R.id.expression_result_start_activity));

        AppCompatActivity thisActivity = this;
        //Turn count
        int turn = 1;
        //Period count: (ie: Quand on suis la trame de jeu à partir du ième joueur, period=2 <=> On part du mot donné par le 3eme joueur)
        //int period = Integer.parseInt(getIntent().getStringExtra("period"));
        int period = 0;
        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                FirebaseDatabaseHelper.getExpression(player.getCurrentRoom().getRoomCode(), player.getCurrentRoom().getPlayers().get(turn-1+period).getPlayerId(), turn, expressionTv);
                usernameTv.setText(player.getCurrentRoom().getPlayers().get(turn-1+period).getPlayerName());
                Test(this);
            }

            @Override
            public void update() {

            }

            @Override
            public void launch() {
                Intent intent = new Intent(thisActivity, ResultDisplayActivity.class);
                intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                intent.putExtra("playerName",player.getPlayerName());
                intent.putExtra("playerId",player.getPlayerId());
                intent.putExtra("turn",String.valueOf(turn));
                intent.putExtra("period",String.valueOf(period));
                startActivity(intent);
            }
        };

        //player = new Player( getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"),false);
        //new Room(getIntent().getStringExtra("roomCode"),player,rdl);

        player = new Player("Clément", "-MW3FJBJ4Tl1h-dn-bqg",false);
        new Room("ABC",player,rdl);
    }
    public void Test(RoomDataListener rdl){
        rdl.launch();
    }
}
