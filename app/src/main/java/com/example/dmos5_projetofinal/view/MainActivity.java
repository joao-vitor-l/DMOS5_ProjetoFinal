package com.example.dmos5_projetofinal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.dmos5_projetofinal.R;
import com.example.dmos5_projetofinal.presentation.mvp.MainMVP;
import com.example.dmos5_projetofinal.presentation.presenter.MainPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements MainMVP.View{
    private MainMVP.Presenter presenter;
    private FloatingActionButton actionButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
        presenter = new MainPresenter(this, this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.populateList(recyclerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        // Apresenta os dados de acordo com a orientação do dispositivo
        super.onConfigurationChanged(newConfig);
        presenter.populateList(recyclerView);
    }

    @Override
    protected void onDestroy(){
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public Context getContext(){
        return this;
    }

    @Override
    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    private void findViews(){
        actionButton = findViewById(R.id.fab_add_remedio);
        recyclerView = findViewById(R.id.recyclerview_remedio);
    }

    private void setListener(){
        actionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                presenter.openEditing();
            }
        });
    }
}
