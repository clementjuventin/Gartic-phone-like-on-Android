package iut.projet.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import iut.projet.R;

public class RoomListViewHolder extends RecyclerView.ViewHolder {
    private TextView name;

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public RoomListViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.player_list_cell_name);
    }
}
