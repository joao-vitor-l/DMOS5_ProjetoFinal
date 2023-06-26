package com.example.dmos5_projetofinal.presentation.presenter;

import static com.example.dmos5_projetofinal.utils.Notifications.sendNotification;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmos5_projetofinal.model.dao.IRemedioDao;
import com.example.dmos5_projetofinal.model.dao.RemedioDaoSingleton;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.MainMVP;
import com.example.dmos5_projetofinal.utils.Constant;
import com.example.dmos5_projetofinal.view.RemedioDetailsActivity;
import com.example.dmos5_projetofinal.view.RemedioEditingActivity;
import com.example.dmos5_projetofinal.view.listeners.ItemClickListener;
import com.example.dmos5_projetofinal.view.adapters.RecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter{
    private MainMVP.View view;
    private IRemedioDao dao;
    Remedio remedio;

    public MainPresenter(MainMVP.View view, Context context){
        this.view = view;
        dao = RemedioDaoSingleton.getInstance(context);
    }

    @Override
    public void detach(){
        view = null;
    }

    @Override
    public void populateList(RecyclerView recyclerView){
        RecyclerAdapter adapter = new RecyclerAdapter(view.getContext(), dao.getDataset(), this);
        adapter.setClickListener(new ItemClickListener(){
            @Override
            public void onItemClick(int position){
                remedio = dao.getDataset().get(position);
                openDetails(remedio);
            }
        });

        for(Remedio remedio : dao.getDataset()){
            String nome = remedio.getNome();
            int disponibilidade = remedio.getDisponibilidade();
            if(disponibilidade <= 3){
                sendNotification(view.getContext(), "[" + nome + "] Quantidade de remédios baixa", "Você possui apenas " + disponibilidade + " unidade(s) restante(s).");
            }
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void openDetails(Remedio remedio){
        Intent intent = new Intent(view.getContext(), RemedioDetailsActivity.class);
        intent.putExtra(Constant.ATTR_ID, remedio.getId());
        view.getContext().startActivity(intent);
    }

    @Override
    public void openEditing(){
        Intent intent = new Intent(view.getContext(), RemedioEditingActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openEditing(Remedio remedio){
        Intent intent = new Intent(view.getContext(), RemedioEditingActivity.class);
        intent.putExtra(Constant.ATTR_ID, remedio.getId());
        view.getContext().startActivity(intent);
    }

    @Override
    public void editRemedio(Remedio remedio){
        openEditing(remedio);
    }

    @Override
    public void deleteRemedio(Remedio task){
        dao.deleteRemedio(remedio);
        if(view != null){
            populateList(view.getRecyclerView());
        }
    }
}
