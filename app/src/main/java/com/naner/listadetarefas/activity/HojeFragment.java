package com.naner.listadetarefas.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naner.listadetarefas.R;
import com.naner.listadetarefas.adapter.TarefaAdapter;
import com.naner.listadetarefas.helper.RecyclerItemClickListener;
import com.naner.listadetarefas.helper.TarefaDAO;
import com.naner.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HojeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    public HojeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoje, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Adicionar evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Edição de tarefa
                Tarefa tarefaSelecionada = listaTarefas.get(position);

                Intent intent = new Intent(getActivity(), NovaTarefaActivity.class);
                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Recuperar tarefa a ser excluida
                tarefaSelecionada = listaTarefas.get(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                // Título & mensagem
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir a tarefa " + tarefaSelecionada.getNomeTarefa() + "?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TarefaDAO tarefaDAO = new TarefaDAO(getActivity().getApplicationContext());

                        if (tarefaDAO.deletar(tarefaSelecionada)) {
                            carregarListaDeTarefas();
                            Toast.makeText(getActivity(), "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Erro ao excluir tarefa.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("Não", null);
                // Exibir o dialog:
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        return view;
    }

    public void carregarListaDeTarefas() {

        // Listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getActivity().getApplicationContext());
        listaTarefas = tarefaDAO.listarHoje();

        // Configura o Adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        // Configura o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),));

        recyclerView.setAdapter(tarefaAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarListaDeTarefas();
    }
}
