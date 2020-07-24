package com.abayomi.stockbay.Model;

public class ModelNotification {

    String id, Nome, Quantidade,DtIncluso,HrInclusao;

    public ModelNotification() {

    }

    public ModelNotification(String id, String nome, String quantidade, String dtIncluso, String hrInclusao) {
        this.id = id;
        Nome = nome;
        Quantidade = quantidade;
        DtIncluso = dtIncluso;
        HrInclusao = hrInclusao;
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

    public String getDtIncluso() {
        return DtIncluso;
    }

    public void setDtIncluso(String dtIncluso) {
        DtIncluso = dtIncluso;
    }

    public String getHrInclusao() {
        return HrInclusao;
    }

    public void setHrInclusao(String hrInclusao) {
        HrInclusao = hrInclusao;
    }
}
