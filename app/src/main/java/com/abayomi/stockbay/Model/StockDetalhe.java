package com.abayomi.stockbay.Model;

public class StockDetalhe {

    String Id, Nome, Quantidade, VlCusto, VlCompra, DtCompra, Desc;

    public StockDetalhe()
    {

    }

    public StockDetalhe(String id, String nome, String quantidade, String vlCusto, String vlCompra, String dtCompra, String desc) {
        Id = id;
        Nome = nome;
        Quantidade = quantidade;
        VlCusto = vlCusto;
        VlCompra = vlCompra;
        DtCompra = dtCompra;
        Desc = desc;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getVlCusto() {
        return VlCusto;
    }

    public void setVlCusto(String vlCusto) {
        VlCusto = vlCusto;
    }

    public String getVlCompra() {
        return VlCompra;
    }

    public void setVlCompra(String vlCompra) {
        VlCompra = vlCompra;
    }

    public String getDtCompra() {
        return DtCompra;
    }

    public void setDtCompra(String dtCompra) {
        DtCompra = dtCompra;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
