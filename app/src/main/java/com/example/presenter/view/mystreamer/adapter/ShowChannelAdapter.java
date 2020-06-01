package com.example.presenter.view.mystreamer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.presenter.view.mystreamer.R;

import java.util.ArrayList;

public class ShowChannelAdapter extends RecyclerView.Adapter<ShowChannelAdapter.ShowChannelViewHolder>
{
    private OnItemClickListener listener;
    private ArrayList<String> chName;
    private int width;

    public ShowChannelAdapter(OnItemClickListener listener, ArrayList<String> chName,int width) {
        this.listener = listener;
        this.chName = chName;
        this.width=width;
    }

    @NonNull
    @Override
    public ShowChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ch_rv_item,parent,false);
        return new ShowChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowChannelViewHolder holder, int position) {
        String name=chName.get(position);
        holder.ch_name_txt.setText(name);
        holder.onBind(name);
    }

    @Override
    public int getItemCount() {
        return chName.size();
    }

    public class ShowChannelViewHolder extends RecyclerView.ViewHolder
    {

        TextView ch_name_txt;

        public ShowChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ch_name_txt=itemView.findViewById(R.id.ch_name_txt);

            final ViewGroup.LayoutParams layoutparams = (LinearLayout.LayoutParams) ch_name_txt.getLayoutParams();
            layoutparams.width=width;
            ch_name_txt.setLayoutParams(layoutparams);
        }

        void onBind(String chName){
            itemView.setOnClickListener(view -> listener.onClick(chName));
        }

    }

    public interface OnItemClickListener
    {
        void onClick(String name);
    }
}
