package com.example.dmos5_projetofinal.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmos5_projetofinal.R;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.MainMVP;
import com.example.dmos5_projetofinal.view.listeners.ItemClickListener;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private Context context;
    private MainMVP.Presenter presenter;
    private List<Remedio> data;
    private static ItemClickListener clickListener;

    public RecyclerAdapter(Context context, List<Remedio> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Remedio remedio = data.get(position);
        holder.nomeTextView.setText(remedio.getNome());

        holder.editImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ editClick(remedio); }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ deleteClick(remedio); }
        });
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    private void editClick(Remedio remedio){ presenter.editRemedio(remedio); }

    private void deleteClick(Remedio remedio){
        presenter.deleteRemedio(remedio);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nomeTextView;
        public ImageView editImageView;
        public ImageView deleteImageView;

        public ViewHolder(View itemView){
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.text_nome_listitem);
            editImageView = itemView.findViewById(R.id.image_edit_listitem);
            deleteImageView = itemView.findViewById(R.id.image_delete_listitem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener listener){
        clickListener = listener;
    }
}
