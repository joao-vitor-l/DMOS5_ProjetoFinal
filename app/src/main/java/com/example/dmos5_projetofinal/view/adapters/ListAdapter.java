package com.example.dmos5_projetofinal.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dmos5_projetofinal.R;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.MainMVP;

import java.util.List;

public class ListAdapter extends ArrayAdapter{
    private LayoutInflater inflater;
    private MainMVP.Presenter presenter;

    public ListAdapter(Context context, List<Remedio> data, MainMVP.Presenter presenter){
        super(context, R.layout.listview_item, data);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.nomeTextView = convertView.findViewById(R.id.text_nome_listitem);
            holder.editImageView = convertView.findViewById(R.id.image_edit_listitem);
            holder.deleteImageView = convertView.findViewById(R.id.image_delete_listitem);

            holder.editImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) { editClick(position); }
            });

            holder.deleteImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) { deleteClick(position); }
            });

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Remedio remedio = (Remedio) getItem(position);
        holder.nomeTextView.setText(remedio.getNome());

        return convertView;
    }

    private void editClick(int position){
        Remedio remedio = (Remedio) getItem(position);
        presenter.editRemedio(remedio);
    }

    private void deleteClick(int position){
        Remedio remedio = (Remedio) getItem(position);
        presenter.deleteRemedio(remedio);
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        public TextView nomeTextView;
        public ImageView editImageView;
        public ImageView deleteImageView;
    }
}
