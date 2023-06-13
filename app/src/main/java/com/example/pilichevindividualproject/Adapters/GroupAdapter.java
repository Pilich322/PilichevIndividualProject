package com.example.pilichevindividualproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilichevindividualproject.Data.Group;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Group> groupList;
    private final DataBaseManager dbManager;

    private final OnGroupClickListener onGroupClickListener;

    public interface OnGroupClickListener{
        void onGroupClick(Group group, int position);
    }

    public GroupAdapter(Context context, List<Group> groupList,OnGroupClickListener onGroupClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.groupList = groupList;
        dbManager = new DataBaseManager(context);
        this.onGroupClickListener = onGroupClickListener;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.group_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.name.setText(group.getName());
        holder.number.setText(String.valueOf(group.getNumber()));
        holder.delete.setOnClickListener(v -> {
            dbManager.openDbToWrite();
            dbManager.deleteGroup(group);
            groupList.remove(position);
            notifyItemRemoved(position);
            dbManager.closeDb();
        });
        holder.change.setOnClickListener(v -> onGroupClickListener.onGroupClick(group,position));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete, change;
        TextView name, number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewGroupName);
            delete = itemView.findViewById(R.id.imageViewDeleteGroup);
            change = itemView.findViewById(R.id.imageViewChangeGroup);
            number = itemView.findViewById(R.id.textViewGroupNumber);
        }
    }
}
