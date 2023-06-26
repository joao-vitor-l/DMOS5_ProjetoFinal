package com.example.dmos5_projetofinal.presentation.mvp;

import android.os.Bundle;

import com.example.dmos5_projetofinal.model.entities.Remedio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface RemedioDetailsMVP{
    interface View{
        void updateUI(String nome, String descricao, List<Remedio.DataMedicacao> datas, Remedio.PeriodoTratamento periodo, int disponibilidade, Remedio.Medico medico);
        Bundle getBundle();
        void close();
    }

    interface Presenter{
        void detach();
        void verifyUpdate();
        String concatenateDaysOfWeek(List<Remedio.DataMedicacao> datas);
        String formatDayOfWeek(DayOfWeek dayOfWeek);
        String formatDate(LocalDate date);
        String getTimeGap(List<Remedio.DataMedicacao> datas);
    }
}
