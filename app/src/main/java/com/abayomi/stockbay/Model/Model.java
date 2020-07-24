package com.abayomi.stockbay.Model;

public class Model {

    String id, Nome, Quantidade, ValoreVenda;

    public Model() {
    }

    public Model(String id, String nome, String quantidade, String valoreVenda) {
        this.id = id;
        Nome = nome;
        Quantidade = quantidade;
        ValoreVenda = valoreVenda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String quantidade) {
        Quantidade = quantidade;
    }

    public String getValoreVenda() {
        return ValoreVenda;
    }

    public void setValoreVenda(String valoreVenda) {
        ValoreVenda = valoreVenda;
    }

}
