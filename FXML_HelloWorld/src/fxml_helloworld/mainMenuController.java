package fxml_helloworld;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class mainMenuController implements Initializable {

    @FXML
    private Label labelMainMenu1;

    @FXML
    private Label labelMainMenu3;

    @FXML
    private Label labelMainMenu2;

    @FXML
    private Label labelPemilih;

    @FXML
    private Button pemilihButton;

    @FXML
    private ImageView pemilihIcon;

    @FXML
    private Label labelPetugasatauPengawas;

    @FXML
    private Button PetugasatauPengawasButton;

    @FXML
    private ImageView PetugasatauPengawasIcon;

    @FXML
    private Button AdministratorButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleAdministratorButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Administrator.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Tidak menggunakan showAndWait() untuk mencegah freezing mainMenu
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePemilihButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Pemilih.fxml"));
            Parent root = loader.load();
            Stage pemilihStage = new Stage();
            Scene pemilihScene = new Scene(root);
            pemilihStage.setScene(pemilihScene);

            // Tidak menggunakan showAndWait() untuk mencegah freezing mainMenu
            pemilihStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePetugasatauPengawasButtonAction(ActionEvent event) {
        try {
            // Menggunakan nama file FXML yang benar
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = loader.load();
            Stage pengawasStage = new Stage();
            Scene pengawasScene = new Scene(root);
            pengawasStage.setScene(pengawasScene);

            // Tidak menggunakan showAndWait() untuk mencegah freezing mainMenu
            pengawasStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
