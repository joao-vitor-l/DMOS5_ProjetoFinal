package com.example.dmos5_projetofinal.presentation.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.dmos5_projetofinal.model.dao.IRemedioDao;
import com.example.dmos5_projetofinal.model.dao.RemedioDaoSingleton;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.RemedioDetailsMVP;
import com.example.dmos5_projetofinal.utils.Constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RemedioDetailsPresenter implements RemedioDetailsMVP.Presenter{
    private RemedioDetailsMVP.View view;
    private Remedio remedio;
    private IRemedioDao dao;

    public RemedioDetailsPresenter(RemedioDetailsMVP.View view, Context context){
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
            id = bundle.getInt(Constant.ATTR_ID, -1);
            if(id != -1){
                remedio = dao.findRemedio(id);
                view.updateUI(remedio.getNome(), remedio.getDescricao(), remedio.getDatas(), remedio.getPeriodo(), remedio.getDisponibilidade(), remedio.getMedico());
            }
        }
    }

    public void close(){
        view.close();
    }

    @Override
    public String concatenateDaysOfWeek(List<Remedio.DataMedicacao> datas){
        StringBuilder sb = new StringBuilder();
        boolean[] days = new boolean[7]; // Array para acompanhar os dias da semana indicados pelo usuário

        for(Remedio.DataMedicacao data : datas){
            DayOfWeek dayOfWeek = data.getDayOfWeek();
            if(!days[dayOfWeek.getValue() - 1]){ // Verifica se o dia da semana já não foi adicionado ao array
                sb.append(formatDayOfWeek(dayOfWeek)).append(", ");
                days[dayOfWeek.getValue() - 1] = true;
            }

            // Verifica se todos os dias da semana já foram adicionados ao array
            boolean allDaysOfWeek = true;
            for(boolean day : days){
                if(!day){
                    allDaysOfWeek = false;
                    break;
                }
            }

            if(allDaysOfWeek) break; // Interrompe o loop se todos os dias já estiverem presentes no array
        }

        // Reordena os dias da semana
        StringBuilder reorderedSb = new StringBuilder();
        String[] orderedDaysOfWeek = { "Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado" };
        for(String orderedDayOfWeek : orderedDaysOfWeek){
            if(sb.indexOf(orderedDayOfWeek) != -1){
                reorderedSb.append(orderedDayOfWeek).append(", ");
            }
        }

        if(sb.length() > 0) reorderedSb.setLength(reorderedSb.length() - 2); // Remove a última vírgula e espaço

        return reorderedSb.toString();
    }

    @Override
    public String formatDayOfWeek(DayOfWeek dayOfWeek){
        switch(dayOfWeek){
            case SUNDAY:
                return "Domingo";
            case MONDAY:
                return "Segunda";
            case TUESDAY:
                return "Terça";
            case WEDNESDAY:
                return "Quarta";
            case THURSDAY:
                return "Quinta";
            case FRIDAY:
                return "Sexta";
            case SATURDAY:
                return "Sábado";
            default:
                return "";
        }
    }

    @Override
    public String formatDate(LocalDate date){
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return formattedDate;
    }

    @Override
    public String getTimeGap(List<Remedio.DataMedicacao> datas){
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

    private int calculateGap(LocalTime h1, LocalTime h2){
        return Math.abs(h1.getHour() - h2.getHour());
    }

    private String reConvertTimeGap(int selectedGap){
        String timeGap = null;
        switch(selectedGap){
            case 24:
                timeGap = "Uma vez ao dia";
                break;
            case 12:
                timeGap = "12 em 12h";
                break;
            case 8:
                timeGap = "8 em 8h";
                break;
            case 4:
                timeGap = "4 em 4h";
                break;
            case 2:
                timeGap = "2 em 2h";
                break;
        }
        return timeGap;
    }
}
