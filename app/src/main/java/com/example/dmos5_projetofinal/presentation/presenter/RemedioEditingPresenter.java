package com.example.dmos5_projetofinal.presentation.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.dmos5_projetofinal.model.dao.IRemedioDao;
import com.example.dmos5_projetofinal.model.dao.RemedioDaoSingleton;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.RemedioEditingMVP;
import com.example.dmos5_projetofinal.utils.Constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RemedioEditingPresenter implements RemedioEditingMVP.Presenter{
    private RemedioEditingMVP.View view;
    private Remedio remedio;
    private IRemedioDao dao;

    public RemedioEditingPresenter(RemedioEditingMVP.View view, Context context){
        this.view = view;
        remedio = null;
        dao = RemedioDaoSingleton.getInstance(context);
    }

    @Override
    public void detach(){
        this.view = null;
    }

    @Override
    public void verifyUpdate(){
        int id;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            id = Integer.parseInt(bundle.getString(Constant.ATTR_ID));
            remedio = dao.findRemedio(id);
            view.updateUI(remedio.getNome(), remedio.getDescricao(), remedio.getDatas(), remedio.getPeriodo(), remedio.getDisponibilidade(), remedio.getMedico());
        }
    }

    @Override
    public void save(String nome, String descricao,
                     List<DayOfWeek> daysOfWeek, int timeGap, int qtdDosagem, String tipoDosagem, LocalTime initialTime,
                     LocalDate dtInicio, LocalDate dtFim, int disponibilidade,
                     String recMedica, String medNome, String medEspecializacao){
        if(remedio == null){
            // Novo remédio
            remedio = new Remedio(nome, descricao, new Remedio.PeriodoTratamento(dtInicio, dtFim), disponibilidade, new Remedio.Medico(recMedica, medNome, medEspecializacao));
            remedio.addDataMedicacao(daysOfWeek, timeGap, qtdDosagem, tipoDosagem, initialTime);
            dao.addToDataset(remedio);
            view.showToast("Remédio registrado.");
            view.close();
        }else{
            // Atualizar remédio existente
            int id = remedio.getId();
            Remedio newRemedio = new Remedio(nome, descricao, new Remedio.PeriodoTratamento(dtInicio, dtFim), disponibilidade, new Remedio.Medico(recMedica, medNome, medEspecializacao));
            newRemedio.addDataMedicacao(daysOfWeek, timeGap, qtdDosagem, tipoDosagem, initialTime);
            if(dao.updateDataset(id, newRemedio)){
                view.showToast("Informações atualizadas.");
                view.close();
            }else{
                view.showToast("Houve um erro ao atualizar as informações.");
            }
        }
    }

    @Override
    public List<DayOfWeek> addDaysOfWeek(boolean domingo, boolean segunda, boolean terca, boolean quarta, boolean quinta, boolean sexta, boolean sabado){
        List<DayOfWeek> diasDaSemana = new ArrayList<>();
        if(domingo) diasDaSemana.add(DayOfWeek.SUNDAY);
        if(segunda) diasDaSemana.add(DayOfWeek.MONDAY);
        if(terca) diasDaSemana.add(DayOfWeek.TUESDAY);
        if(quarta) diasDaSemana.add(DayOfWeek.WEDNESDAY);
        if(quinta) diasDaSemana.add(DayOfWeek.THURSDAY);
        if(sexta) diasDaSemana.add(DayOfWeek.FRIDAY);
        if(sabado) diasDaSemana.add(DayOfWeek.SATURDAY);

        return diasDaSemana;
    }

    @Override
    public String formatDate(LocalDate date){
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return formattedDate;
    }

    @Override
    public LocalTime convertToLocalTime(int hour, int minute){
        LocalTime localTime = LocalTime.of(hour, minute);
        return localTime;
    }

    @Override
    public int convertTimeGap(String selectedGap){
        int timeGap = 0;
        switch(selectedGap){
            case "Uma vez ao dia":
                timeGap = 24;
                break;
            case "12 em 12h":
                timeGap = 12;
                break;
            case "8 em 8h":
                timeGap = 8;
                break;
            case "4 em 4h":
                timeGap = 4;
                break;
            case "2 em 2h":
                timeGap = 2;
                break;
        }
        return timeGap;
    }

    @Override
    public int getTimeGap(List<Remedio.DataMedicacao> datas){
        int timeGap = -1;
        if (datas != null && datas.size() >= 2) {
            Remedio.DataMedicacao frstDate = datas.get(0);
            Remedio.DataMedicacao scndDate = datas.get(1);

            List<Remedio.HoraDosagem> frstDateHours = frstDate.getHorarios();
            List<Remedio.HoraDosagem> scndDateHours = scndDate.getHorarios();

            if(!frstDateHours.isEmpty() && !scndDateHours.isEmpty()){
                Remedio.HoraDosagem frstHour = frstDateHours.get(0);
                Remedio.HoraDosagem scndHour = scndDateHours.get(0);

                timeGap = calculateGap(frstHour.getHorario(), scndHour.getHorario());
            }
        }
        return reConvertTimeGap(timeGap);
    }

    private int calculateGap(LocalTime h1, LocalTime h2) {
        return Math.abs(h1.getHour() - h2.getHour());
    }

    private int reConvertTimeGap(int selectedGap){
        int timeGap = 0;
        switch(selectedGap){
            case 24:
                timeGap = 0;
                break;
            case 12:
                timeGap = 1;
                break;
            case 8:
                timeGap = 2;
                break;
            case 4:
                timeGap = 3;
                break;
            case 2:
                timeGap = 4;
                break;
        }
        return timeGap;
    }

    @Override
    public int getTipoDosagem(String tipoDosagem){
        int value = 0;
        switch(tipoDosagem){
            case "Cápsula(s)":
                value = 0;
                break;
            case "Comprimido(s)":
                value = 1;
                break;
            case "Gota(s)":
                value = 2;
                break;
        }
        return value;
    }
}
