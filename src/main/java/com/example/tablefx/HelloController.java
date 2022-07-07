package com.example.tablefx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

//класс для функционала (мозг приложения)
public class HelloController {
    private ObservableList<User> usersData = FXCollections.observableArrayList();//список для хранения данных при записе из файла в  таблицу

    @FXML
    private TextField nameFile;

    @FXML
    private TableView<User> tableUsers = new TableView<User>();

    @FXML
    private TableColumn<User, String> Extension;

    @FXML
    private TableColumn<User, String> SizeFile;

    @FXML
    private TableColumn<User, String> Content;
    @FXML
    private TableColumn<User, String> HeightImg;
    @FXML
    private TableColumn<User, String> WidthImg;

    @FXML
    private TableColumn<User, String>  Countbit;

    // инициализируем форму данными
    @FXML
    private void initialize() {
        // устанавливаем тип и значение которое должно хранится в колонке
        Extension.setCellValueFactory(new PropertyValueFactory<>("Extension"));
        SizeFile.setCellValueFactory(new PropertyValueFactory<>("SizeFile"));
        Content.setCellValueFactory(new PropertyValueFactory<>("Content"));
        HeightImg.setCellValueFactory(new PropertyValueFactory<>("HeightImg"));
        WidthImg.setCellValueFactory(new PropertyValueFactory<>("WidthImg"));
        Countbit.setCellValueFactory(new PropertyValueFactory<>("Countbit"));
        tableUsers.setItems(usersData);
    }


    //Если нажата кнопка "Очистить", то очищается таблица и поле ввода названия файла
    public void OnButtonDeleteClick(ActionEvent actionEvent) {
        tableUsers.getItems().clear();
        nameFile.setText("");
    }

    // чтение из файла в список UserData (заполнение таблицы)
    public void writingToTheTable(File file) {
        try {
            String ext = getFileExtension(file);
            String size = getFileSizeBytes(file);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            StringBuilder cntnt = new StringBuilder();
            cntnt.append(line).append("\n");
            while (line != null) {
                line = reader.readLine();
                cntnt.append(line).append("\n");
            }
            usersData.add(new User(ext, size, cntnt.toString(), 10, 10,10));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("File not found");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    @FXML
    private static String getFileSizeBytes(File file) {
        return (double) file.length() + " bytes";
    }

    @FXML
    private void FillListImage(File file) throws IOException {
        String sizeImage = getFileSizeBytes(file);
        String extImage = getFileExtension(file);
        String pathFile = file.getPath();
        ImageView image = new ImageView(pathFile);
        image.setFitHeight(100);
        image.setFitWidth(100);
        String height = getHeightWidthImg(file, "height");
        String width = getHeightWidthImg(file, "width");
        String countbit=getHeightWidthImg(file, "countbyte");
        usersData.add(new User(extImage, sizeImage, image, height,width,countbit));
    }

    private void FillListImagePngJpg(File file) throws IOException {
        String sizeImage = getFileSizeBytes(file);
        String extImage = getFileExtension(file);
        String countbit="";
        BufferedImage img = javax.imageio.ImageIO.read(new File(file.getPath()));
        String width = String.valueOf(img.getWidth());
        String height= String.valueOf(img.getHeight());
        String pathFile = file.getPath();
        ImageView image = new ImageView(pathFile);
        image.setFitHeight(100);
        image.setFitWidth(100);
        usersData.add(new User(extImage, sizeImage, image, height,width,countbit));
    }


    ///считывание картинки
    private static String getHeightWidthImg(File file, String type) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        int[] fileContentInt = new int[fileContent.length];
        var a = (byte)fileContent[22];
        for(int i=0;i<fileContent.length;i++)
        {

            if(fileContent[i]<0)
            {
                fileContentInt[i]=(int)(256+fileContent[i]);
            }
           else {
                fileContentInt[i]=(int)fileContent[i];
            }
        }
        int heightB=fileContentInt[22]+fileContentInt[23]*256+fileContentInt[24]*256*256+fileContentInt[25]*256*256*256;
        String height=(Integer.toString(heightB));
        int widthB=fileContentInt[18]+fileContentInt[19]*256+fileContentInt[20]*256*256+fileContentInt[21]*256*256*256;
        String width=(Integer.toString(widthB));
        int countB=fileContentInt[28]+fileContentInt[29]*256;
        String count=(Integer.toString(countB));
        if (Objects.equals(type, "height")) {
             return height;
        }
        if (Objects.equals(type, "width")) {
            return width;
        }
        if (Objects.equals(type, "countbyte")) {
            return count;
        }
        return "no";
    }

    private void InitFile(File file) throws IOException {
        String strFile = getFileExtension(file);
        switch (strFile) {
            case ("txt"), ("docx") -> {
                writingToTheTable(file);
                initialize();
            }
            case ("bmp") -> {
                FillListImage(file);
                initialize();
            }
            case ("jpg"), ("png") -> {
                FillListImagePngJpg(file);
                initialize();
            }
        }
    }

    public void OnButtonInsertClick(ActionEvent actionEvent) {

    }

    public void OnButtonFileClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            InitFile(selectedFile);
        }
    }
}