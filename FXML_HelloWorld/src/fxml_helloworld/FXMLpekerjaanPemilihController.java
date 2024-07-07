package fxml_helloworld;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

public class FXMLpekerjaanPemilihController implements Initializable {

    @FXML
    private TableView<pekerjaanPemilih> TablePemilih;

    @FXML
    private TableColumn<pekerjaanPemilih, String> TableNama;

    @FXML
    private TableColumn<pekerjaanPemilih, String> TableNIK;

    @FXML
    private TableColumn<pekerjaanPemilih, String> TablePekerjaan;

    private ObservableList<pekerjaanPemilih> pemilihList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        TableNIK.setCellValueFactory(new PropertyValueFactory<>("NIK"));
        TablePekerjaan.setCellValueFactory(new PropertyValueFactory<>("Pekerjaan"));

        pemilihList = FXCollections.observableArrayList();
        TablePemilih.setItems(pemilihList);

        loadDataFromXML();
        setupFileWatcher();
    }

    private void loadDataFromXML() {
        try {
            // Menggunakan path absolut
            String xmlPath = "C:\\Users\\HP\\OneDrive\\Documents\\FXML_HelloWorld\\FXML_HelloWorld\\src\\fxml_helloworld\\pemilih.xml";
            File xmlFile = new File(xmlPath);
            
            if (!xmlFile.exists()) {
                System.err.println("File XML tidak ditemukan: " + xmlPath);
                return;
            }

            System.out.println("XML file path: " + xmlFile.getAbsolutePath());
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
            System.out.println("File exists: " + xmlFile.exists());
            System.out.println("File can read: " + xmlFile.canRead());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pemilih");

            pemilihList.clear();

            for (int i = 0; i < nList.getLength(); i++) {
                Element element = (Element) nList.item(i);

                String NIK = element.getElementsByTagName("NIK").item(0).getTextContent();
                String Nama = element.getElementsByTagName("NamaPemilih").item(0).getTextContent();
                String Pekerjaan = element.getElementsByTagName("Pekerjaan").item(0).getTextContent();

                pemilihList.add(new pekerjaanPemilih(NIK, Nama, Pekerjaan));
                System.out.println("Loaded: " + NIK + ", " + Nama + ", " + Pekerjaan);
            }

            System.out.println("Total data loaded: " + pemilihList.size());
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
                                javafx.application.Platform.runLater(this::loadDataFromXML);
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
}