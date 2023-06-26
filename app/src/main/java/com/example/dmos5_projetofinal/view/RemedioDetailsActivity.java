package com.example.dmos5_projetofinal.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dmos5_projetofinal.R;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.RemedioDetailsMVP;
import com.example.dmos5_projetofinal.presentation.presenter.RemedioDetailsPresenter;

import java.util.List;

public class RemedioDetailsActivity extends AppCompatActivity implements RemedioDetailsMVP.View, View.OnClickListener{
    private RemedioDetailsMVP.Presenter presenter;
    private TextView nomeTextView;
    private TextView descricaoTextView;
    private TextView diasSemanaTextView;
    private TextView intervaloTextView;
    private TextView qtdDosagemTextView;
    private TextView tipoDosagemTextView;
    private TextView horarioTextView;
    private TextView dtInicioTextView;
    private TextView dtFimTextView;
    private TextView disponibilidadeTextView;
    private TextView recMedicaTextView;
    private TextView medNomeTextView;
    private TextView medEspecializacaoTextView;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedio_details);

        presenter = new RemedioDetailsPresenter(this, this);
        findViews();
        setListener();
        setToolbar();
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.verifyUpdate();
    }

    @Override
    protected void onDestroy(){
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v){ if(v == returnButton) close(); }

    public void updateUI(String nome, String descricao, List<Remedio.DataMedicacao> datas, Remedio.PeriodoTratamento periodo, int disponibilidade, Remedio.Medico medico){
        nomeTextView.setText(nome);
        descricaoTextView.setText(descricao);
        diasSemanaTextView.setText(presenter.concatenateDaysOfWeek(datas));
        intervaloTextView.setText(presenter.getTimeGap(datas));
        //qtdDosagemTextView.setText(String.valueOf(datas.get(0).getHorarios().get(0).getDosagem().getQtd()));
        //tipoDosagemTextView.setText(datas.get(0).getHorarios().get(0).getDosagem().getTipo());
        //horarioTextView.setText(String.valueOf(datas.get(0).getHorarios()));
        //horarioTextView.append(String.valueOf(datas.get(0).getHorarios()));
        dtInicioTextView.setText(presenter.formatDate(periodo.getDtInicio()));
        dtFimTextView.setText(presenter.formatDate(periodo.getDtFim()));
        disponibilidadeTextView.setText(String.valueOf(disponibilidade));
        recMedicaTextView.setText(medico.getRecomendacaoMedica());
        medNomeTextView.setText(medico.getNome());
        medEspecializacaoTextView.setText(medico.getEspecializacao());
    }

    @Override
    public Bundle getBundle(){
        return getIntent().getExtras();
    }

    @Override
    public void close(){
        presenter.detach();
        finish();
    }

    private void findViews(){
        nomeTextView = findViewById(R.id.textview_nome_details);
        descricaoTextView = findViewById(R.id.textview_descricao_details);
        diasSemanaTextView = findViewById(R.id.textview_diassemana_details);
        intervaloTextView = findViewById(R.id.textview_intervalo_details);
        qtdDosagemTextView = findViewById(R.id.edittext_qtddosagem_details);
        tipoDosagemTextView = findViewById(R.id.textview_tipodosagem_details);
        horarioTextView = findViewById(R.id.textview_horario_details);
        dtInicioTextView = findViewById(R.id.textview_dtinicio_details);
        dtFimTextView = findViewById(R.id.textview_dtfim_details);
        disponibilidadeTextView = findViewById(R.id.textview_disponibilidade_details);
        recMedicaTextView = findViewById(R.id.textview_recmedica_details);
        medNomeTextView = findViewById(R.id.textview_mednome_details);
        medEspecializacaoTextView = findViewById(R.id.textview_medespecializacao_details);
        returnButton = findViewById(R.id.button_return);
    }

    private void setListener(){
        returnButton.setOnClickListener(this);
    }

    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
