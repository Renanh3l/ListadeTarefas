package com.naner.listadetarefas.activity;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.naner.listadetarefas.R;
import com.naner.listadetarefas.helper.TarefaDAO;
import com.naner.listadetarefas.model.Tarefa;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NovaTarefaActivity extends AppCompatActivity {

    private EditText editTarefa;
    private Tarefa tarefaAtual;
    private RadioGroup radioGroupData;
    private RadioButton rbHoje;
    private RadioButton rbAmanha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        editTarefa = findViewById(R.id.etNomeNovaTarefa);
        radioGroupData = findViewById(R.id.radioGroupData);
        rbHoje = findViewById(R.id.rbHoje);
        rbAmanha = findViewById(R.id.rbAmanha);

        // Recuperar tarefa em caso de edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nova_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.itemSalvar:

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null) {
                    // Edição
                    if (!editTarefa.getText().toString().isEmpty()) {

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(editTarefa.getText().toString());
                        tarefa.setId(tarefaAtual.getId());
                        tarefa.setData(tarefaAtual.getData());

                        // Atualizar no banco de dados:
                        if (tarefaDAO.atualizar(tarefa)) {
                            finish();
                            Toast.makeText(this, "Tarefa alterada com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao salvar tarefa.", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    if (!editTarefa.getText().toString().isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(editTarefa.getText().toString());

                        if (radioGroupData.getCheckedRadioButtonId() == rbHoje.getId()) {
                            // Hoje:
                            tarefa.setData(System.currentTimeMillis());

                        } else if (radioGroupData.getCheckedRadioButtonId() == rbAmanha.getId()) {

                            // Amanhã:
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.add(Calendar.DATE, 1);
                            long amanha = calendar.getTimeInMillis();

                            tarefa.setData(amanha);
                        } else {
                            // Outro dia:
                            tarefa.setData(0);
                        }


                        if (tarefaDAO.salvar(tarefa)) {
                            Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    }
                }

        }
        return super.onOptionsItemSelected(item);
    }
}
