package fxml_helloworld;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.Optional;

public class AdministratorController {

    @FXML
    private Label namaPetugasPemilih;

    @FXML
    private Button LogOutPetugasPemilih;

    @FXML
    private TableView<Administrator> TableAdministrator;

    @FXML
    private TableColumn<Administrator, String> KolomNamaAdministrator;

    @FXML
    private TableColumn<Administrator, String> KolomNIKAdministrator;

    @FXML
    private TextField DeleteNamaPengawas;

    @FXML
    private TextField DeleteNIKPengawas;

    @FXML
    private Button DeleteButtonPengawas;

    @FXML
    private TextField TambahNamaPengawas;

    @FXML
    private TextField TambahNIKpengawas;

    @FXML
    private Button TambahButtonPengawas;

    @FXML
    private TextField CariNIKpengawas;

    @FXML
    private Button CariButtonPengawas;

    @FXML
    private Button KembaliKeDaftarPemilihButtonTable;

    private ObservableList<Administrator> administratorList = FXCollections.observableArrayList();
    private ObservableList<Administrator> fullAdministratorList;
    private String xmlFilePath = "C:\\Users\\HP\\OneDrive\\Documents\\FXML_HelloWorld\\FXML_HelloWorld\\src\\fxml_helloworld\\users.xml";

    @FXML
    public void initialize() {
        KolomNamaAdministrator.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        KolomNIKAdministrator.setCellValueFactory(new PropertyValueFactory<>("NIK"));

        loadAdministratorsFromXML();

        TableAdministrator.setItems(administratorList);

        KembaliKeDaftarPemilihButtonTable.setDisable(false);  // Pastikan tombol selalu aktif

        setUpButtonListeners();
    }

    private void loadAdministratorsFromXML() {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nama = eElement.getElementsByTagName("Nama").item(0).getTextContent();
                    String nik = eElement.getElementsByTagName("NIK").item(0).getTextContent();
                    administratorList.add(new Administrator(nama, nik));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpButtonListeners() {
        if (TambahButtonPengawas != null) {
            TambahButtonPengawas.setOnAction(event -> tambahAdministrator());
        }
        if (DeleteButtonPengawas != null) {
            DeleteButtonPengawas.setOnAction(event -> deleteAdministrator());
        }
        if (CariButtonPengawas != null) {
            CariButtonPengawas.setOnAction(event -> cariAdministrator());
        }
        if (LogOutPetugasPemilih != null) {
            LogOutPetugasPemilih.setOnAction(event -> handleLogOut());
        }
        if (KembaliKeDaftarPemilihButtonTable != null) {
            KembaliKeDaftarPemilihButtonTable.setOnAction(event -> {
                System.out.println("Tombol Kembali Ke Daftar Pemilih ditekan");
                kembaliKeDaftarPemilih();
            });
        }
    }

    private void tambahAdministrator() {
        String nama = TambahNamaPengawas.getText();
        String nik = TambahNIKpengawas.getText();
        if (!nama.isEmpty() && !nik.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Penambahan Data");
            alert.setHeaderText("Apakah kamu ingin memasukkan data petugas/pengawas ini?");
            alert.setContentText("Nama: " + nama + "\nNIK: " + nik);

            ButtonType simpanButton = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(simpanButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == simpanButton) {
                administratorList.add(new Administrator(nama, nik));
                TambahNamaPengawas.clear();
                TambahNIKpengawas.clear();
                System.out.println("Administrator baru ditambahkan: " + nama + " (NIK: " + nik + ")");
                saveAdministratorsToXML();
            } else {
                System.out.println("Data yang kamu berikan tidak tersimpan.");
            }
        }
    }

    private void deleteAdministrator() {
        String nama = DeleteNamaPengawas.getText();
        String nik = DeleteNIKPengawas.getText();
        
        Administrator adminToDelete = administratorList.stream()
            .filter(admin -> admin.getNama().equals(nama) && admin.getNIK().equals(nik))
            .findFirst()
            .orElse(null);
        
        if (adminToDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Penghapusan Data");
            alert.setHeaderText("Apakah kamu ingin menghapus data petugas/pengawas ini?");
            alert.setContentText("Nama: " + nama + "\nNIK: " + nik);
    
            ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(deleteButton, cancelButton);
    
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == deleteButton) {
                administratorList.remove(adminToDelete);
                System.out.println("Administrator dihapus: " + nama + " (NIK: " + nik + ")");
                saveAdministratorsToXML();
                TableAdministrator.refresh();
            } else {
                System.out.println("Penghapusan dibatalkan untuk: " + nama + " (NIK: " + nik + ")");
            }
        } else {
            System.out.println("Administrator tidak ditemukan: " + nama + " (NIK: " + nik + ")");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Administrator dengan nama " + nama + " dan NIK " + nik + " tidak ditemukan.");
            alert.showAndWait();
        }
    
        DeleteNamaPengawas.clear();
        DeleteNIKPengawas.clear();
    }

    private void cariAdministrator() {
        String nik = CariNIKpengawas.getText();
        if (!nik.isEmpty()) {
            if (fullAdministratorList == null) {
                fullAdministratorList = FXCollections.observableArrayList(TableAdministrator.getItems());
            }
            ObservableList<Administrator> filteredList = fullAdministratorList.filtered(administrator -> administrator.getNIK().equals(nik));
            TableAdministrator.setItems(filteredList);
            System.out.println("Pencarian untuk NIK: " + nik + " menampilkan " + filteredList.size() + " hasil");
        } else {
            kembaliKeDaftarPemilih();
        }
    }

    private void kembaliKeDaftarPemilih() {
        try {
            if (fullAdministratorList != null) {
                TableAdministrator.setItems(fullAdministratorList);
                fullAdministratorList = null;
            } else {
                TableAdministrator.setItems(administratorList);
            }
            CariNIKpengawas.clear();
            System.out.println("Kembali ke daftar lengkap pengawas");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Terjadi kesalahan saat kembali ke daftar pemilih: " + e.getMessage());
        }
    }

    private void saveAdministratorsToXML() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("user-array");
            doc.appendChild(rootElement);

            for (Administrator administrator : administratorList) {
                Element userElement = doc.createElement("user");
                rootElement.appendChild(userElement);

                Element nama = doc.createElement("Nama");
                nama.appendChild(doc.createTextNode(administrator.getNama()));
                userElement.appendChild(nama);

                Element nik = doc.createElement("NIK");
                nik.appendChild(doc.createTextNode(administrator.getNIK()));
                userElement.appendChild(nik);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Data berhasil disimpan ke XML.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal menyimpan data ke XML: " + e.getMessage());
        }
    }

    private void handleLogOut() {
        Stage stage = (Stage) LogOutPetugasPemilih.getScene().getWindow();
        stage.close();
    }
}