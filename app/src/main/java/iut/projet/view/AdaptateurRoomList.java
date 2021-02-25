package iut.projet.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iut.projet.R;
import iut.projet.metier.Player;

public class AdaptateurRoomList extends RecyclerView.Adapter {
    private List<Player> playerList;

    public AdaptateurRoomList(List<Player> liste){
        this.playerList = liste;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_cell, parent, false);
        return new RoomListViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RoomListViewHolder)holder).getName().setText(playerList.get(position).getPlayerName());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}