package com.abayomi.stockbay;

public class Model {

    String id, Nome, Quantidade, DataCompra, ValoreVenda, ValorCusto, Descricao,editNome,editFone;


    public Model() {
    }

    public Model(String id, String nome, String quantidade, String dataCompra, String valoreVenda, String valorCusto, String descricao) {
        this.id = id;
        Nome = nome;
        Quantidade = quantidade;
        DataCompra = dataCompra;
        ValoreVenda = valoreVenda;
        ValorCusto = valorCusto;
        Descricao = descricao;
        editNome = editNome;
        editFone = editFone;


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

    public String getDataCompra() {
        return DataCompra;
    }

    public void setDataCompra(String dataCompra) {
        DataCompra = dataCompra;
    }

    public String getValoreVenda() {
        return ValoreVenda;
    }

    public void setValoreVenda(String valoreVenda) {
        ValoreVenda = valoreVenda;
    }

    public String getValorCusto() {
        return ValorCusto;
    }

    public void setValorCusto(String valorCusto) {
        ValorCusto = valorCusto;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getEditNome() {
        return editNome;
    }

    public void setEditNome(String editNome) {
        this.editNome = editNome;
    }

    public String getEditFone() {
        return editFone;
    }

    public void setEditFone(String editFone) {
        this.editFone = editFone;
    }
}
