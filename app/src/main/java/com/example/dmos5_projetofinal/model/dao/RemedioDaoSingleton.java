package com.example.dmos5_projetofinal.model.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RemedioDaoSingleton implements IRemedioDao{
    private static RemedioDaoSingleton instance = null;
    private List<Remedio> dataset;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private RemedioDaoSingleton(Context context){
        dataset = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        loadRemedio();
    }

    public static RemedioDaoSingleton getInstance(Context context){
        if(instance == null) instance = new RemedioDaoSingleton(context);
        return instance;
    }

    @Override
    public void addToDataset(Remedio remedio){
        if(remedio != null){
            dataset.add(remedio);
            saveRemedio();
        }
    }

    @Override
    public boolean updateDataset(int id, Remedio remedio){
        Remedio inDataset = findRemedio(id);
        if(inDataset != null){
            inDataset.setNome(remedio.getNome());
            inDataset.setDescricao(remedio.getDescricao());
            inDataset.setDatas(remedio.getDatas());
            inDataset.setPeriodo(remedio.getPeriodo());
            inDataset.setDisponibilidade(remedio.getDisponibilidade());
            inDataset.setMedico(remedio.getMedico());
            saveRemedio();
            return true;
        }
        return false;
    }

    @Override
    public List<Remedio> getDataset(){
        return dataset;
    }

    @Override
    public Remedio findRemedio(int id){
        for(Remedio remedio : dataset) if(remedio.getId() == id) return remedio;
        return null;
    }

    @Override
    public void loadRemedio(){
        String jsonRemedios = sharedPreferences.getString("remedios", "");
        Type type = new TypeToken<List<Remedio>>() {}.getType();
        List<Remedio> savedRemedios = gson.fromJson(jsonRemedios, type);
        if(savedRemedios != null){
            dataset.clear();
            dataset.addAll(savedRemedios);
        }
    }

    @Override
    public void saveRemedio(){
        String jsonRemedios = gson.toJson(dataset);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remedios", jsonRemedios);
        editor.apply();
    }

    @Override
    public boolean deleteRemedio(Remedio remedio){
        boolean removed = dataset.remove(remedio);
        if(removed){
            saveRemedio();
        }
        return removed;
    }
}
