package fxml_helloworld;

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
import javafx.stage.Modality;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private TextField loginUserName;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginMasuk;

    private User[] users;

    private double x = 0;
    protected double y = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Membuat objek User
        users = new User[3];
        users[0] = new User("admin", "admin123");
        users[1] = new User("user", "user123");
        users[2] = new User("nathan", "nathan123");

        // Membuat objek dari XStream
        XStream xstream = new XStream(new StaxDriver());

        // Menentukan alias untuk kelas User
        xstream.alias("user", User.class);

        // Mengubah array User menjadi string dengan format XML
        String xml = xstream.toXML(users);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("users.xml");
            // Mengubah karakter penyusun string "xml" menjadi bytes (berbentuk nomor kode ASCII)
            byte[] bytes = xml.getBytes(StandardCharsets.UTF_8);

            // Menyimpan file dari bytes ke dalam file "users.xml"
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
    public void adminLogin(ActionEvent event) {
        String username = loginUserName.getText();
        String password = loginPassword.getText();

        Alert alert;

        if (username.isEmpty() || password.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter username and password");
            alert.showAndWait();
        } else {
            User user = findUser(username, password);
            if (user != null) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login!");
                alert.showAndWait();

                Stage currentStage = (Stage) loginMasuk.getScene().getWindow();
                currentStage.hide();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_helloworld/FXMLpetugasPengawasDashboard.fxml"));
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

                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(currentStage);

                    // Hanya menggunakan showAndWait()
                    stage.showAndWait();

                    // Tampilkan kembali stage utama setelah stage admin ditutup
                    currentStage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username/Password");
                alert.showAndWait();
            }
        }
    }

    private User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
}