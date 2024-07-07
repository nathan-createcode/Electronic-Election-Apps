package fxml_helloworld;

public class totalPemilih{
    private String NIK;
    private String Nama;

    public totalPemilih(String NIK, String Nama) {
        this.NIK = NIK;
        this.Nama = Nama;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }
}