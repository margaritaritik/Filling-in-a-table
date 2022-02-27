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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//класс для функционала (мозг приложения)
public class HelloController {
    Path path;
    private ObservableList<User> usersData = FXCollections.observableArrayList();//список для хранения данных при записе из файла в  таблицу

    @FXML
    private TextField nameFile;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> textColumn;

    @FXML
    private TableColumn<User, ImageView> pictureColumn;

    @FXML
    private  ImageView pictureInColumn;

    public HelloController() {
    }

    // инициализируем форму данными
    @FXML
    private void initialize() {
        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        textColumn.setCellValueFactory(new PropertyValueFactory<User, String>("text"));
        pictureColumn.setCellValueFactory(new PropertyValueFactory<User, ImageView>("picture"));
        // заполняем таблицу данными
        tableUsers.setItems(usersData);
    }


    //Если нажата кнопка "Очистить", то очищается таблица и поле ввода названия файла
    public void OnButtonDeleteClick(ActionEvent actionEvent) {
        tableUsers.getItems().clear();
        nameFile.setText("");
    }

   // чтение из файла в список UserData (заполнение таблицы)
    public void writingToTheTable(Path path)
    {
        try {
            Scanner sc = new Scanner(path);
            while (sc.hasNextLine()) {
                String id = sc.nextLine();
                String text = sc.nextLine();
                String picture = sc.nextLine();
                Image image=new Image(new FileInputStream(picture));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                usersData.add(new User(Integer.parseInt(id), text,imageView));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Results:");
            alert.setContentText("Че ты ввел?!?");

            alert.showAndWait();
        }
    }

    //Если нажата кнопка "Заполнить", то вывести в таблицу содержимое файла(название которого вводит пользователь в TextField)
    public void OnButtonInsertClick(ActionEvent actionEvent) {
        String fileName=nameFile.getText()+".txt";
        path= Paths.get(fileName);
        writingToTheTable(path);
    }

    public void OnButtonFileClick(ActionEvent actionEvent) {
        FileChooser file=new FileChooser();
        File selectedFile=file.showOpenDialog(null);
        if(selectedFile!=null)
        {
            path= Paths.get(selectedFile.getName());
            writingToTheTable(path);
        }
    }
}