package com.abayomi.stockbay;

public class ModelContact {

    String Id, Nome, Fone;

    public ModelContact() {

    }

    public ModelContact(String id, String nome, String fone) {
        this.Id = id;
        Nome = nome;
        Fone = fone;
    }

    public String getId() { return Id; }
    public void setId(String id) { Id = id; }

    public String getNome() { return Nome; }
    public void setNome(String nome) { Nome = nome; }

    public String getFone() { return Fone; }
    public void setFone(String fone) { Fone = fone; }

}
