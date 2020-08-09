package com.abayomi.stockbay.Model;

public class ModelHistoric {

    String id, Nome, Quantidade, ValorVenda, ValorTotal;

    public ModelHistoric(String id, String nome, String quantidade, String valorVenda, String valorTotal) {
        this.id = id;
        Nome = nome;
        Quantidade = quantidade;
        ValorVenda = valorVenda;
        ValorTotal = valorTotal;
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

    public String getValorVenda() {
        return ValorVenda;
    }

    public void setValorVenda(String valorVenda) {
        ValorVenda = valorVenda;
    }

    public String getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(String valorTotal) {
        ValorTotal = valorTotal;
    }
}
