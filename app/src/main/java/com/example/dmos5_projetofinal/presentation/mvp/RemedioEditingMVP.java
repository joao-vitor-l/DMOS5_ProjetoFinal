package com.example.dmos5_projetofinal.presentation.mvp;

import android.os.Bundle;

import com.example.dmos5_projetofinal.model.entities.Remedio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RemedioEditingMVP{
    interface View{
        void updateUI(String nome, String descricao, List<Remedio.DataMedicacao> datas, Remedio.PeriodoTratamento periodo, int disponibilidade, Remedio.Medico medico);
        Bundle getBundle();
        void showToast(String message);
        void close();
    }

    interface Presenter{
        void detach();
        void verifyUpdate();
        void save(String nome, String descricao,
                  List<DayOfWeek> daysOfWeek, int timeGap, int qtdDosagem, String tipoDosagem, LocalTime initialTime,
                  LocalDate dtInicio, LocalDate dtFim, int disponibilidade,
                  String recMedica, String medNome, String medEspecializacao);
        List<DayOfWeek> addDaysOfWeek(boolean domingo, boolean segunda, boolean terca, boolean quarta, boolean quinta, boolean sexta, boolean sabado);
        String formatDate(LocalDate date);
        LocalTime convertToLocalTime(int hour, int minute);
        int convertTimeGap(String selectedGap);
        int getTimeGap(List<Remedio.DataMedicacao> datas);
        int getTipoDosagem(String tipoDosagem);
    }
}
