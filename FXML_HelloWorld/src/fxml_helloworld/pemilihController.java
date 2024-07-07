package fxml_helloworld;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class pemilihController implements Initializable {

    @FXML
    private TextField loginNamaPemilih;

    @FXML
    private PasswordField loginNIKPemilih;

    @FXML
    private Button pemilihMasukButton;

    private Pemilih[] pemilih;

    private double x = 0;
    protected double y = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Membuat objek Pemilih
        pemilih = new Pemilih[3];
        pemilih[0] = new Pemilih(000, "agus");
        pemilih[1] = new Pemilih(111, "cecep");
        pemilih[2] = new Pemilih(222, "bambang");

        // Membuat objek dari XStream
        XStream xstream = new XStream(new StaxDriver());

        // Menentukan alias untuk kelas Pemilih
        xstream.alias("pemilih", Pemilih.class);

        // Mengubah array Pemilih menjadi string dengan format XML
        String xml = xstream.toXML(pemilih);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("pemilih.xml");
            // Mengubah karakter penyusun string "xml" menjadi bytes (berbentuk nomor kode ASCII)
            byte[] bytes = xml.getBytes(StandardCharsets.UTF_8);

            // Menyimpan file dari bytes ke dalam file "pemilih.xml"
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            System.err.println("Perhatian: " + e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void pemilihLogin(ActionEvent event) {
        Integer nIK = null;
        String namaPemilih = null;

        try {
            nIK = Integer.parseInt(loginNIKPemilih.getText());
            namaPemilih = loginNamaPemilih.getText();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input for NIK. Please enter a valid number.");
            alert.showAndWait();
            return; // Exit method if parsing fails
        }

        Alert alert;

        Pemilih pemilih = findPemilih(nIK, namaPemilih);
        if (pemilih != null) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Login!");
            alert.showAndWait();

            pemilihMasukButton.getScene().getWindow().hide();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_helloworld/pemilihDashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {
                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);
                });

                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Wrong NIK/Nama Pemilih");
            alert.showAndWait();
        }
    }

    private Pemilih findPemilih(Integer nIK, String namaPemilih) {
        for (Pemilih pemilih : pemilih) {
            if (pemilih.getNIK().equals(nIK) && pemilih.getNamaPemilih().equals(namaPemilih)) {
                return pemilih;
            }
        }
        return null;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
}
