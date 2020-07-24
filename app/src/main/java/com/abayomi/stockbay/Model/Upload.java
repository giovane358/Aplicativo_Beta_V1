package com.abayomi.stockbay.Model;

public class Upload {
    private String filePath;
    private String mNome;

    public Upload() {

    }

    public Upload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "sem Name";
        }

        mNome = name;
        filePath = imageUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }
}
