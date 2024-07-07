package fxml_helloworld;

public class dataPemilih {
    private String NamaPemilih;
    private String NIK;
    private String Pekerjaan;

    public dataPemilih (String NamaPemilih, String NIK, String Pekerjaan) {
        this.NamaPemilih = NamaPemilih;
        this.NIK = NIK;
        this.Pekerjaan = Pekerjaan;
    }

    public String getNamaPemilih() {
        return NamaPemilih;
    }

    public void setNamaPemilih(String namaPemilih) {
        NamaPemilih = namaPemilih;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String nIK) {
        NIK = nIK;
    }

    public String getPekerjaan() {
        return Pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        Pekerjaan = pekerjaan;
    }

    
}
