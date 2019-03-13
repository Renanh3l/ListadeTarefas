package com.naner.listadetarefas.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.naner.listadetarefas.R;
import com.naner.listadetarefas.model.Tarefa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    public TarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_tarefa_adapter, viewGroup, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Tarefa tarefa = listaTarefas.get(i);
        myViewHolder.tarefaNome.setText(tarefa.getNomeTarefa());

        // Converte e escreve a Data da tarefa
        long tarefaData = tarefa.getData();
        Date data = new Date(tarefaData);
        String dataFinal = DateFormat.getTimeInstance(DateFormat.SHORT).format(data);
        myViewHolder.tarefaData.setText(dataFinal);
    }

    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tarefaNome;
        private TextView tarefaData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tarefaNome = itemView.findViewById(R.id.textNomeTarefa);
            tarefaData = itemView.findViewById(R.id.textDataTarefa);

        }


    }

}
