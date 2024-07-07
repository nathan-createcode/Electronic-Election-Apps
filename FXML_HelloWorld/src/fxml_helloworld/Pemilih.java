package fxml_helloworld;

public class Pemilih {
    private Integer NIK;
    private String namaPemilih;

    // Konstruktor default
    public Pemilih() {}

    // Konstruktor dengan parameter
    public Pemilih(Integer NIK, String namaPemilih) {
        this.NIK = NIK;
        this.namaPemilih = namaPemilih;
    }

    // Getter dan Setter
    public Integer getNIK() {
        return NIK;
    }

    public void setNIK(Integer NIK) {
        this.NIK = NIK;
    }

    public String getNamaPemilih() {
        return namaPemilih;
    }

    public void setNamaPemilih(String namaPemilih) {
        this.namaPemilih = namaPemilih;
    }
}