package com.example.dmos5_projetofinal.presentation.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dmos5_projetofinal.model.entities.Remedio;

public interface MainMVP{
    interface View{
        Context getContext();
        RecyclerView getRecyclerView();
    }

    interface Presenter{
        void detach();
        void populateList(RecyclerView recyclerView);
        void openDetails(Remedio remedio);
        void openEditing();
        void openEditing(Remedio remedio);
        void editRemedio(Remedio remedio);
        void deleteRemedio(Remedio task);
    }
}
