package fxml_helloworld;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class FXMLpetugasPengawasDashboardController {

    @FXML
    private Label namaPetugasPemilih;

    @FXML
    private Button LogOutPetugasPemilih;

    @FXML
    private Label TotalPemilihNumber;

    @FXML
    private Label JumlahyangSudahMemilihNumber;

    @FXML
    private Label SuaraTidakSahNumber;

    @FXML
    private BarChart<String, Number> PaslonBarChart;

    @FXML
    private Hyperlink pekerjaanPemilihLink;

    @FXML
    private Hyperlink TotalPemilihLink;

    @FXML
    private Button DatapemilihButton;

    private int totalPemilih = 0;
    private int jumlahYangSudahMemilih = 0;
    private int suaraTidakSah = 0;
    private int countPaslon01 = 0;
    private int countPaslon02 = 0;
    private int countPaslon03 = 0;

    @FXML
    public void initialize() {
        try {
            URL cssUrl = getClass().getResource("/fxml_helloworld/FXMLpengawasPetugas.css");
            if (cssUrl != null) {
                String css = cssUrl.toExternalForm();
                PaslonBarChart.getScene().getStylesheets().add(css);
            } else {
                System.err.println("CSS file not found: /FXMLpengawasPetugas.css");
            }
        } catch (Exception e) {
            System.err.println("Error loading CSS: " + e.getMessage());
        }
        
        loadTotalPemilihFromXML();
        setupFileWatcher();
        javafx.application.Platform.runLater(this::updateUI);

        pekerjaanPemilihLink.setOnAction(event -> openPekerjaanPemilih());
        TotalPemilihLink.setOnAction(event -> openTotalPemilih());

        DatapemilihButton.setOnAction(event -> handleDatapemilihButton());
    }

    @FXML
    private void handleTotalPemilihLinkClick(ActionEvent event) {
        openTotalPemilih();
    }

    @FXML
    private void handlePekerjaanPemilihLinkClick(ActionEvent event) {
        openPekerjaanPemilih();
    }

    private void handleDatapemilihButton() {
        openDataPemilih();
    }

    private void loadTotalPemilihFromXML() {
        try {
            String xmlPath = "C:\\Users\\HP\\OneDrive\\Documents\\FXML_HelloWorld\\FXML_HelloWorld\\src\\fxml_helloworld\\pemilih.xml";
            File xmlFile = new File(xmlPath);
            
            if (!xmlFile.exists()) {
                System.err.println("File XML tidak ditemukan: " + xmlPath);
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pemilih");
            totalPemilih = nList.getLength();

            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupFileWatcher() {
        try {
            Path path = Paths.get("C:\\Users\\HP\\OneDrive\\Documents\\FXML_HelloWorld\\FXML_HelloWorld\\src\\fxml_helloworld\\pemilih.xml");
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            Thread watcherThread = new Thread(() -> {
                try {
                    while (true) {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.context().toString().equals(path.getFileName().toString())) {
                                javafx.application.Platform.runLater(this::loadTotalPemilihFromXML);
                            }
                        }
                        key.reset();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            watcherThread.setDaemon(true);
            watcherThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPekerjaanPemilih() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLpekerjaanPemilih.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pekerjaan Pemilih");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openTotalPemilih() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLTotalPemilih.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Total Pemilih");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDataPemilih() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dataPemilih.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Data Pemilih");
            stage.show();

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("FXMLTotalPemilih.fxml"));
            Parent root2 = loader2.load();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root2));
            stage2.setTitle("Total Pemilih");
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStats(int newTotalPemilih, int newJumlahYangSudahMemilih, int newSuaraTidakSah) {
        this.jumlahYangSudahMemilih += newJumlahYangSudahMemilih;
        this.suaraTidakSah += newSuaraTidakSah;
        javafx.application.Platform.runLater(this::updateUI);
    }

    public void updatePaslonChart(int newCountPaslon01, int newCountPaslon02, int newCountPaslon03) {
        this.countPaslon01 += newCountPaslon01;
        this.countPaslon02 += newCountPaslon02;
        this.countPaslon03 += newCountPaslon03;
        javafx.application.Platform.runLater(this::updateUI);
    }

    private void updateUI() {
        if (TotalPemilihNumber != null) {
            TotalPemilihNumber.setText(String.valueOf(totalPemilih));
        }
        if (JumlahyangSudahMemilihNumber != null) {
            JumlahyangSudahMemilihNumber.setText(String.valueOf(jumlahYangSudahMemilih));
        }
        if (SuaraTidakSahNumber != null) {
            SuaraTidakSahNumber.setText(String.valueOf(suaraTidakSah));
        }

        if (PaslonBarChart != null) {
            PaslonBarChart.getData().clear();
            XYChart.Series<String, Number> series01 = new XYChart.Series<>();
            XYChart.Series<String, Number> series02 = new XYChart.Series<>();
            XYChart.Series<String, Number> series03 = new XYChart.Series<>();

            series01.getData().add(new XYChart.Data<>("Paslon 01", countPaslon01));
            series02.getData().add(new XYChart.Data<>("Paslon 02", countPaslon02));
            series03.getData().add(new XYChart.Data<>("Paslon 03", countPaslon03));

            PaslonBarChart.getData().addAll(series01, series02, series03);
        }
    }

    @FXML
    private void handleLogout() {
        // Implement logout functionality
    }

    public static void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLpetugasPengawasDashboardController.class.getResource("FXMLpetugasPengawasDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Pengawas Dashboard");
            stage.setResizable(true);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
