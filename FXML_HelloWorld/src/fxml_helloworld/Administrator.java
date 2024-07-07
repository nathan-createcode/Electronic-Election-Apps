package fxml_helloworld;

public class Administrator {
    private String Nama;
    private String NIK;

    public Administrator(String Nama, String NIK) {
        this.Nama = Nama;
        this.NIK = NIK;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String nik) {
        NIK = nik;
    }
}
