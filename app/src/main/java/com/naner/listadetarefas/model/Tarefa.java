package com.naner.listadetarefas.model;

import java.io.Serializable;
import java.util.Date;

public class Tarefa implements Serializable {

    private Long id;
    private String nomeTarefa;
    private long data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
