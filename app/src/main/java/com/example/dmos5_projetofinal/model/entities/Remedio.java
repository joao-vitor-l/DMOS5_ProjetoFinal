package com.example.dmos5_projetofinal.model.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Remedio{
    private static int lastId = 0;

    private int id;
    private String nome;
    private String descricao;
    private List<DataMedicacao> datas;
    private PeriodoTratamento periodo;
    private int disponibilidade;
    private Medico medico;

    public Remedio(String nome, String descricao, PeriodoTratamento periodo, int disponibilidade, Medico medico){
        this.id = ++lastId;
        this.nome = nome;
        this.descricao = descricao;
        this.datas = new ArrayList<>();
        this.periodo = periodo;
        this.disponibilidade = disponibilidade;
        this.medico = medico;
    }

    public void addDataMedicacao(List<DayOfWeek> daysOfWeek, int timeGap, int qtdDosagem, String tipoDosagem, LocalTime initialTime){
        datas.clear();

        LocalDate dt = periodo.getDtInicio();

        // Enquanto a data for menor ou igual à data de fim do tratamento
        while(!dt.isAfter(periodo.getDtFim())){
            // Verifica quando os dias da semana aparecem na lista de dias do mês
            if(daysOfWeek.contains(dt.getDayOfWeek())){
                DataMedicacao dataMedicacao = new DataMedicacao(dt);
                datas.add(dataMedicacao);
                //addHoraDosagem(dataMedicacao, initialTime, timeGap, qtdDosagem, tipoDosagem);
                System.out.println(dataMedicacao.horarios);
            }
            // Avança para o próximo dia
            dt = dt.plusDays(1);
        }
    }

    private void addHoraDosagem(DataMedicacao dataMedicacao, LocalTime initialTime, int timeGap, int qtdDosagem, String tipoDosagem){
        LocalTime horario = initialTime;
        while(horario.isBefore(LocalTime.MAX)){
            dataMedicacao.getHorarios().add(new HoraDosagem(horario, new Dosagem(qtdDosagem, tipoDosagem)));
            horario = horario.plusHours(timeGap);
        }
    }

    // Getters e Setters

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public List<DataMedicacao> getDatas(){
        return datas;
    }

    public void setDatas(List<DataMedicacao> datas){
        this.datas = datas;
    }

    public PeriodoTratamento getPeriodo(){
        return periodo;
    }

    public void setPeriodo(PeriodoTratamento periodo){
        this.periodo = periodo;
    }

    public int getDisponibilidade(){
        return disponibilidade;
    }

    public void setDisponibilidade(int disponibilidade){
        this.disponibilidade = disponibilidade;
    }

    public Medico getMedico(){
        return medico;
    }

    public void setMedico(Medico medico){
        this.medico = medico;
    }

    // Classes internas

    public static class DataMedicacao{
        private LocalDate data;
        private List<HoraDosagem> horarios;

        public DataMedicacao(LocalDate data){
            this.data = data;
            this.horarios = new ArrayList<>();
        }

        public DayOfWeek getDayOfWeek(){
            return data.getDayOfWeek();
        }

        // Getters e Setters

        public LocalDate getData(){
            return data;
        }

        public void setData(LocalDate data){
            this.data = data;
        }

        public List<HoraDosagem> getHorarios(){
            return horarios;
        }

        public void setHorarios(List<HoraDosagem> horarios){
            this.horarios = horarios;
        }
    }

    public static class HoraDosagem{
        private LocalTime horario;
        private Dosagem dosagem;

        public HoraDosagem(LocalTime horario, Dosagem dosagem){
            this.horario = horario;
            this.dosagem = dosagem;
        }

        public int getHour(){
            return horario.getHour();
        }

        public int getMinute(){
            return horario.getMinute();
        }

        // Getters e Setters

        public LocalTime getHorario(){
            return horario;
        }

        public void setHorario(LocalTime horario){
            this.horario = horario;
        }

        public Dosagem getDosagem(){
            return dosagem;
        }

        public void setDosagem(Dosagem dosagem){
            this.dosagem = dosagem;
        }
    }

    public static class Dosagem{
        private float qtd;
        private String tipo;

        public Dosagem(float qtd, String tipo){
            this.qtd = qtd;
            this.tipo = tipo;
        }

        // Getters e Setters

        public float getQtd(){
            return qtd;
        }

        public void setQtd(float qtd){
            this.qtd = qtd;
        }

        public String getTipo(){
            return tipo;
        }

        public void setTipo(String tipo){
            this.tipo = tipo;
        }
    }

    public static class PeriodoTratamento{
        private LocalDate dtInicio;
        private LocalDate dtFim;

        public PeriodoTratamento(LocalDate dtInicio, LocalDate dtFim){
            this.dtInicio = dtInicio;
            this.dtFim = dtFim;
        }

        // Getters e Setters

        public LocalDate getDtInicio(){
            return dtInicio;
        }

        public void setDtInicio(LocalDate dtInicio){
            this.dtInicio = dtInicio;
        }

        public LocalDate getDtFim(){
            return dtFim;
        }

        public void setDtFim(LocalDate dtFim){
            this.dtFim = dtFim;
        }
    }

    public static class Medico{
        private String recomendacaoMedica;
        private String nome;
        private String especializacao;

        public Medico(String recomendacaoMedica, String nome, String especializacao){
            this.recomendacaoMedica = recomendacaoMedica;
            this.nome = nome;
            this.especializacao = especializacao;
        }

        // Getters e Setters

        public String getRecomendacaoMedica() { return recomendacaoMedica; }

        public void setRecomendacaoMedica(String recomendacaoMedica) { this.recomendacaoMedica = recomendacaoMedica; }

        public String getNome(){
            return nome;
        }

        public void setNome(String nome){
            this.nome = nome;
        }

        public String getEspecializacao(){
            return especializacao;
        }

        public void setEspecializacao(String especializacao){ this.especializacao = especializacao; }
    }
}
