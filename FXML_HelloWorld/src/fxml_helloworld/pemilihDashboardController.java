package fxml_helloworld;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class pemilihDashboardController {

    @FXML
    private ToggleButton Paslon01;

    @FXML
    private ToggleButton Paslon02;

    @FXML
    private ToggleButton Paslon03;

    @FXML
    private ImageView imgGenta;

    @FXML
    private ImageView imgTio;

    @FXML
    private ImageView imgSulthan;

    private Map<String, Boolean> pemilihStatus = new HashMap<>();
    private static FXMLpetugasPengawasDashboardController pengawasController;
    private Stage pengawasStage;

    @FXML
    private void initialize() {
        pemilihStatus.put("000", false);
        pemilihStatus.put("111", false);
        pemilihStatus.put("222", false);

        if (pengawasController == null) {
            boolean initialized = initializePengawasController();
            if (!initialized) {
                System.err.println("Gagal menginisialisasi pengawas controller pada saat inisialisasi.");
                // Tambahkan penanganan error yang sesuai di sini
            }
        }
    }

    private boolean initializePengawasController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLpetugasPengawasDashboard.fxml"));
        try {
            Parent root = loader.load();
            pengawasController = loader.getController();
            
            // Kode yang ditambahkan sesuai permintaan
            pengawasStage = new Stage();
            Scene scene = new Scene(root);
            pengawasStage.setScene(scene);
            pengawasStage.setTitle("Pengawas Dashboard");
            pengawasStage.setResizable(true);
            pengawasStage.show();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handlePaslon01() {
        handlePaslonSelection("Paslon 01");
    }

    @FXML
    private void handlePaslon02() {
        handlePaslonSelection("Paslon 02");
    }

    @FXML
    private void handlePaslon03() {
        handlePaslonSelection("Paslon 03");
    }

    private void handlePaslonSelection(String paslon) {
        if (pemilihStatus.containsValue(true)) {
            showAlreadyVotedAlert();
        } else {
            if (confirmSelection()) {
                updatePemilihStatus(true);
                updateUI(paslon);
                updateStatisticsAndChart(paslon);
                closeStage();
            }
        }
    }

    private void updatePemilihStatus(boolean status) {
        for (String key : pemilihStatus.keySet()) {
            pemilihStatus.put(key, status);
        }
    }

    private void updateUI(String paslon) {
        switch (paslon) {
            case "Paslon 01":
                Paslon01.setSelected(true);
                break;
            case "Paslon 02":
                Paslon02.setSelected(true);
                break;
            case "Paslon 03":
                Paslon03.setSelected(true);
                break;
        }
    }

    private void updateStatisticsAndChart(String selectedPaslon) {
        if (pengawasController == null) {
            initializePengawasController();
        }
        
        if (pengawasController != null) {
            pengawasController.updateStats(1, 1, 0); // Mengupdate statistik di dashboard pengawas

            int countPaslon01 = selectedPaslon.equals("Paslon 01") ? 1 : 0;
            int countPaslon02 = selectedPaslon.equals("Paslon 02") ? 1 : 0;
            int countPaslon03 = selectedPaslon.equals("Paslon 03") ? 1 : 0;
            pengawasController.updatePaslonChart(countPaslon01, countPaslon02, countPaslon03);
        } else {
            System.err.println("Pengawas controller tidak dapat diinisialisasi.");
            // Tambahkan penanganan error yang sesuai di sini, misalnya menampilkan pesan error ke user
        }
    }

    private void closeStage() {
        Stage stage = (Stage) Paslon01.getScene().getWindow();
        stage.close();
    }

    private void showAlreadyVotedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Already Voted");
        alert.setHeaderText(null);
        alert.setContentText("You have already voted. Each voter can only vote once per session.");
        alert.showAndWait();
    }

    private boolean confirmSelection() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure with your selection?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}