package fxml_helloworld;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.Optional;

public class dataPemilihController {

    @FXML
    private Label namaPetugasPemilih;

    @FXML
    private TableView<dataPemilih> TablePetugasPengawas;

    @FXML
    private TableColumn<dataPemilih, String> KolomNamaPemilih;

    @FXML
    private TableColumn<dataPemilih, String> KolomNIKPemilih;

    @FXML
    private TableColumn<dataPemilih, String> KolomPekerjaanPemilih;

    @FXML
    private TextField DeleteNamaPemilih;

    @FXML
    private TextField DeleteNIKPemilih;

    @FXML
    private TextField DeletePekerjaanPemilih;

    @FXML
    private Button DeleteButtonPemilih;

    @FXML
    private TextField TambahNamaPemilih;

    @FXML
    private TextField TambahNIKpemilih;

    @FXML
    private TextField TambahPekerjaanPemilih;

    @FXML
    private Button TambahButtonPemilih;

    @FXML
    private TextField EditNamaPemilih;

    @FXML
    private TextField EditNIKPemilih;

    @FXML
    private TextField EditPekerjaanPemilih;

    @FXML
    private Button EditButtonPemilih;

    private ObservableList<dataPemilih> pemilihList = FXCollections.observableArrayList();
    private String xmlFilePath = "C:\\Users\\HP\\OneDrive\\Documents\\FXML_HelloWorld\\FXML_HelloWorld\\src\\fxml_helloworld\\pemilih.xml";

    @FXML
    public void initialize() {
        KolomNamaPemilih.setCellValueFactory(new PropertyValueFactory<>("NamaPemilih"));
        KolomNIKPemilih.setCellValueFactory(new PropertyValueFactory<>("NIK"));
        KolomPekerjaanPemilih.setCellValueFactory(new PropertyValueFactory<>("Pekerjaan"));

        loadPemilihFromXML();

        TablePetugasPengawas.setItems(pemilihList);

        setUpButtonListeners();
    }

    private void loadPemilihFromXML() {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pemilih");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nama = eElement.getElementsByTagName("NamaPemilih").item(0).getTextContent();
                    String nik = eElement.getElementsByTagName("NIK").item(0).getTextContent();
                    String pekerjaan = eElement.getElementsByTagName("Pekerjaan").item(0).getTextContent();
                    pemilihList.add(new dataPemilih(nama, nik, pekerjaan));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpButtonListeners() {
        TambahButtonPemilih.setOnAction(event -> tambahPemilih());
        DeleteButtonPemilih.setOnAction(event -> deletePemilih());
        EditButtonPemilih.setOnAction(event -> editPemilih());
    }

    private void tambahPemilih() {
        String nama = TambahNamaPemilih.getText();
        String nik = TambahNIKpemilih.getText();
        String pekerjaan = TambahPekerjaanPemilih.getText();
        if (!nama.isEmpty() && !nik.isEmpty() && !pekerjaan.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Penambahan Data");
            alert.setHeaderText("Apakah kamu ingin memasukkan data pemilih ini?");
            alert.setContentText("Nama: " + nama + "\nNIK: " + nik + "\nPekerjaan: " + pekerjaan);

            ButtonType simpanButton = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(simpanButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == simpanButton) {
                pemilihList.add(new dataPemilih(nama, nik, pekerjaan));
                TambahNamaPemilih.clear();
                TambahNIKpemilih.clear();
                TambahPekerjaanPemilih.clear();
                savePemilihToXML();
                TablePetugasPengawas.refresh();
            }
        }
    }

    private void deletePemilih() {
        String nama = DeleteNamaPemilih.getText();
        String nik = DeleteNIKPemilih.getText();
        String pekerjaan = DeletePekerjaanPemilih.getText();
        
        dataPemilih pemilihToDelete = pemilihList.stream()
            .filter(pemilih -> pemilih.getNamaPemilih().equals(nama) && pemilih.getNIK().equals(nik) && pemilih.getPekerjaan().equals(pekerjaan))
            .findFirst()
            .orElse(null);
        
        if (pemilihToDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Penghapusan Data");
            alert.setHeaderText("Apakah kamu ingin menghapus data pemilih ini?");
            alert.setContentText("Nama: " + nama + "\nNIK: " + nik + "\nPekerjaan: " + pekerjaan);
    
            ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(deleteButton, cancelButton);
    
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == deleteButton) {
                pemilihList.remove(pemilihToDelete);
                savePemilihToXML();
                TablePetugasPengawas.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Pemilih dengan data tersebut tidak ditemukan.");
            alert.showAndWait();
        }
    
        DeleteNamaPemilih.clear();
        DeleteNIKPemilih.clear();
        DeletePekerjaanPemilih.clear();
    }

    private void editPemilih() {
        String nama = EditNamaPemilih.getText();
        String nik = EditNIKPemilih.getText();
        String pekerjaan = EditPekerjaanPemilih.getText();

        dataPemilih pemilihToEdit = pemilihList.stream()
            .filter(pemilih -> pemilih.getNIK().equals(nik))
            .findFirst()
            .orElse(null);

        if (pemilihToEdit != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Edit Data");
            alert.setHeaderText("Apakah kamu ingin mengedit data pemilih ini?");
            alert.setContentText("Nama: " + nama + "\nNIK: " + nik + "\nPekerjaan: " + pekerjaan);

            ButtonType editButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(editButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == editButton) {
                pemilihToEdit.setNamaPemilih(nama);
                pemilihToEdit.setPekerjaan(pekerjaan);
                savePemilihToXML();
                TablePetugasPengawas.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Pemilih dengan NIK tersebut tidak ditemukan.");
            alert.showAndWait();
        }

        EditNamaPemilih.clear();
        EditNIKPemilih.clear();
        EditPekerjaanPemilih.clear();
    }

    private void savePemilihToXML() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("pemilih-array");
            doc.appendChild(rootElement);

            for (dataPemilih pemilih : pemilihList) {
                Element pemilihElement = doc.createElement("pemilih");
                rootElement.appendChild(pemilihElement);

                Element nama = doc.createElement("NamaPemilih");
                nama.appendChild(doc.createTextNode(pemilih.getNamaPemilih()));
                pemilihElement.appendChild(nama);

                Element nik = doc.createElement("NIK");
                nik.appendChild(doc.createTextNode(pemilih.getNIK()));
                pemilihElement.appendChild(nik);

                Element pekerjaan = doc.createElement("Pekerjaan");
                pekerjaan.appendChild(doc.createTextNode(pemilih.getPekerjaan()));
                pemilihElement.appendChild(pekerjaan);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}