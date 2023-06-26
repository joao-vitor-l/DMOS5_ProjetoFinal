package com.example.dmos5_projetofinal.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmos5_projetofinal.R;
import com.example.dmos5_projetofinal.model.entities.Remedio;
import com.example.dmos5_projetofinal.presentation.mvp.RemedioEditingMVP;
import com.example.dmos5_projetofinal.presentation.presenter.RemedioEditingPresenter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class RemedioEditingActivity extends AppCompatActivity implements RemedioEditingMVP.View, View.OnClickListener{
    private RemedioEditingMVP.Presenter presenter;
    private EditText nomeEditText;
    private EditText descricaoEditText;
    private CheckBox checkboxDomingo;
    private CheckBox checkboxSegunda;
    private CheckBox checkboxTerca;
    private CheckBox checkboxQuarta;
    private CheckBox checkboxQuinta;
    private CheckBox checkboxSexta;
    private CheckBox checkboxSabado;
    private Spinner intervaloSpinner;
    private EditText qtdDosagemEditText;
    private Spinner tipoDosagemSpinner;
    private TimePicker horarioTimePicker;
    private DatePicker dtInicioDatePicker;
    private DatePicker dtFimDatePicker;
    private EditText disponibilidadeEditText;
    private EditText recMedicaEditText;
    private EditText medNomeEditText;
    private EditText medEspecializacaoEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedio_editor);

        presenter = new RemedioEditingPresenter(this, this);
        findViews();
        setListener();
        setToolbar();
        setSpinners();
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
    public void onClick(View v){
        if(v == saveButton){
            try{
                presenter.save(nomeEditText.getText().toString(),
                        descricaoEditText.getText().toString(),
                        presenter.addDaysOfWeek(checkboxDomingo.isChecked(), checkboxSegunda.isChecked(), checkboxTerca.isChecked(),
                                checkboxQuarta.isChecked(), checkboxQuinta.isChecked(), checkboxSexta.isChecked(), checkboxSabado.isChecked()),
                        presenter.convertTimeGap(intervaloSpinner.getSelectedItem().toString()),
                        Integer.parseInt(qtdDosagemEditText.getText().toString()),
                        tipoDosagemSpinner.getSelectedItem().toString(),
                        presenter.convertToLocalTime(horarioTimePicker.getHour(), horarioTimePicker.getMinute()),
                        LocalDate.of(dtInicioDatePicker.getYear(), dtInicioDatePicker.getMonth() + 1, dtInicioDatePicker.getDayOfMonth()),
                        LocalDate.of(dtFimDatePicker.getYear(), dtFimDatePicker.getMonth() + 1, dtFimDatePicker.getDayOfMonth()),
                        Integer.parseInt(disponibilidadeEditText.getText().toString()),
                        recMedicaEditText.getText().toString(),
                        medNomeEditText.getText().toString(),
                        medEspecializacaoEditText.getText().toString());
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI(String nome, String descricao, List<Remedio.DataMedicacao> datas, Remedio.PeriodoTratamento periodo, int disponibilidade, Remedio.Medico medico){
        nomeEditText.setText(nome);
        descricaoEditText.setText(descricao);

        checkboxDomingo.setChecked(false);
        checkboxSegunda.setChecked(false);
        checkboxTerca.setChecked(false);
        checkboxQuarta.setChecked(false);
        checkboxQuinta.setChecked(false);
        checkboxSexta.setChecked(false);
        checkboxSabado.setChecked(false);

        for(Remedio.DataMedicacao data : datas){
            DayOfWeek dayOfWeek = data.getDayOfWeek();
            switch(dayOfWeek){
                case SUNDAY:
                    checkboxDomingo.setChecked(true);
                    break;
                case MONDAY:
                    checkboxSegunda.setChecked(true);
                    break;
                case TUESDAY:
                    checkboxTerca.setChecked(true);
                    break;
                case WEDNESDAY:
                    checkboxQuarta.setChecked(true);
                    break;
                case THURSDAY:
                    checkboxQuinta.setChecked(true);
                    break;
                case FRIDAY:
                    checkboxSexta.setChecked(true);
                    break;
                case SATURDAY:
                    checkboxSabado.setChecked(true);
                    break;
            }
        }

        intervaloSpinner.setSelection(presenter.getTimeGap(datas));
        //qtdDosagemEditText.setText(String.valueOf(datas.get(0).getHorarios().get(0).getDosagem().getQtd()));
        //tipoDosagemSpinner.setSelection(presenter.getTipoDosagem(datas.get(0).getHorarios().get(0).getDosagem().getTipo()));
        //horarioTimePicker.setHour(Remedio.HoraDosagem.getHour());
        //horarioTimePicker.setHour(Remedio.HoraDosagem.getMinute());
        dtInicioDatePicker.updateDate(periodo.getDtInicio().getYear(),
                periodo.getDtInicio().getMonthValue() - 1,
                periodo.getDtInicio().getDayOfMonth());
        dtFimDatePicker.updateDate(periodo.getDtFim().getYear(),
                periodo.getDtFim().getMonthValue() - 1,
                periodo.getDtFim().getDayOfMonth());
        disponibilidadeEditText.setText(String.valueOf(disponibilidade));
        recMedicaEditText.setText(medico.getRecomendacaoMedica());
        medNomeEditText.setText(medico.getNome());
        medEspecializacaoEditText.setText(medico.getEspecializacao());
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(String message){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }

    @Override
    public void close(){
        presenter.detach();
        finish();
    }

    private void findViews(){
        nomeEditText = findViewById(R.id.edittext_nome_details);
        descricaoEditText = findViewById(R.id.edittext_descricao_details);
        checkboxDomingo = findViewById(R.id.checkbox_domingo);
        checkboxSegunda = findViewById(R.id.checkbox_segunda);
        checkboxTerca = findViewById(R.id.checkbox_terca);
        checkboxQuarta = findViewById(R.id.checkbox_quarta);
        checkboxQuinta = findViewById(R.id.checkbox_quinta);
        checkboxSexta = findViewById(R.id.checkbox_sexta);
        checkboxSabado = findViewById(R.id.checkbox_sabado);
        intervaloSpinner = findViewById(R.id.spinner_intervalo_details);
        qtdDosagemEditText = findViewById(R.id.edittext_qtddosagem_details);
        tipoDosagemSpinner = findViewById(R.id.spinner_tipodosagem_details);
        horarioTimePicker = findViewById(R.id.timepicker_horario_details);
        dtInicioDatePicker = findViewById(R.id.datepicker_dtinicio_details);
        dtFimDatePicker = findViewById(R.id.datepicker_dtfim_details);
        disponibilidadeEditText = findViewById(R.id.edittext_disponibilidade_details);
        recMedicaEditText = findViewById(R.id.edittext_recmedica_details);
        medNomeEditText = findViewById(R.id.edittext_mednome_details);
        medEspecializacaoEditText = findViewById(R.id.edittext_medespecializacao_details);
        saveButton = findViewById(R.id.button_save_remedio);
    }

    private void setListener(){
        saveButton.setOnClickListener(this);
    }

    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setSpinners(){
        String[] intervaloOptions = {"Uma vez ao dia", "12 em 12h", "8 em 8h", "4 em 4h", "2 em 2h"};
        ArrayAdapter<String> intervaloAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, intervaloOptions);
        intervaloAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervaloSpinner.setAdapter(intervaloAdapter);

        String[] tipoDosagemOptions = {"Cápsula(s)", "Comprimido(s)", "Gota(s)"};
        ArrayAdapter<String> tipoDosagemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoDosagemOptions);
        intervaloAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDosagemSpinner.setAdapter(tipoDosagemAdapter);
    }
}
