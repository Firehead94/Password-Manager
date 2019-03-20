package com.passwordmanager;

import java.net.URL;
import com.passwordmanager.utils.Layouts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application {

    public static void main(String[] args) {
        //launch the GUI
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        URL loc = getClass().getClassLoader().getResource(Layouts.LOGINSCREEN_FXML);
        FXMLLoader loader = new FXMLLoader(loc);
        Parent rootPane = loader.load();
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to Keycrypt");
        primaryStage.show();

    }
}