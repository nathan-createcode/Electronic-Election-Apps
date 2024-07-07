package fxml_helloworld;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class pekerjaanPemilih {
    private StringProperty NIK;
    private StringProperty Nama;
    private StringProperty Pekerjaan;

    public pekerjaanPemilih(String NIK, String Nama, String Pekerjaan) {
        this.NIK = new SimpleStringProperty(NIK);
        this.Nama = new SimpleStringProperty(Nama);
        this.Pekerjaan = new SimpleStringProperty(Pekerjaan);
    }

    public String getNIK() {
        return NIK.get();
    }

    public void setNIK(String NIK) {
        this.NIK.set(NIK);
    }

    public String getNama() {
        return Nama.get();
    }

    public void setNama(String Nama) {
        this.Nama.set(Nama);
    }

    public String getPekerjaan() {
        return Pekerjaan.get();
    }

    public void setPekerjaan(String Pekerjaan) {
        this.Pekerjaan.set(Pekerjaan);
    }

    public StringProperty NIKProperty() {
        return NIK;
    }

    public StringProperty NamaProperty() {
        return Nama;
    }

    public StringProperty PekerjaanProperty() {
        return Pekerjaan;
    }
}