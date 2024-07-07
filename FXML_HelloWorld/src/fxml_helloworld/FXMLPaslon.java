package fxml_helloworld;

public class FXMLPaslon {
    private int totalPemilih;
    private int jumlahYangSudahMemilih;
    private int suaraTidakSah;

    public FXMLPaslon() {
        // Default constructor needed for any frameworks or serializers
    }

    public FXMLPaslon(int totalPemilih, int jumlahYangSudahMemilih, int suaraTidakSah) {
        this.totalPemilih = totalPemilih;
        this.jumlahYangSudahMemilih = jumlahYangSudahMemilih;
        this.suaraTidakSah = suaraTidakSah;
    }

    public int getTotalPemilih() {
        return totalPemilih;
    }

    public void setTotalPemilih(int totalPemilih) {
        this.totalPemilih = totalPemilih;
    }

    public int getJumlahYangSudahMemilih() {
        return jumlahYangSudahMemilih;
    }

    public void setJumlahYangSudahMemilih(int jumlahYangSudahMemilih) {
        this.jumlahYangSudahMemilih = jumlahYangSudahMemilih;
    }

    public int getSuaraTidakSah() {
        return suaraTidakSah;
    }

    public void setSuaraTidakSah(int suaraTidakSah) {
        this.suaraTidakSah = suaraTidakSah;
    }
}
