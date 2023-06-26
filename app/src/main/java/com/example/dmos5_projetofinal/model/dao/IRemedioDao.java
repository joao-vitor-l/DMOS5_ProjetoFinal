package com.example.dmos5_projetofinal.model.dao;

import com.example.dmos5_projetofinal.model.entities.Remedio;

import java.util.List;

public interface IRemedioDao{
    void addToDataset(Remedio remedio);
    boolean updateDataset(int id, Remedio remedio);
    List<Remedio> getDataset();
    Remedio findRemedio(int id);
    void saveRemedio();
    void loadRemedio();
    boolean deleteRemedio(Remedio remedio);
}
