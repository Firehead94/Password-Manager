package com.passwordmanager.gui.controllers;


import com.passwordmanager.gui.base.DialogBox;
import com.passwordmanager.Main;
import com.passwordmanager.utils.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController
{
    @FXML
    private AnchorPane login;

    @FXML
    private PasswordField passHiddenFld;

    @FXML
    private TextField passShowFld;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private CheckBox showCheckBox;

    //Button Controllers. Temporary for now

    @FXML
    private void showHelp(ActionEvent actionEvent)
    {
        DialogBox.showInformation("Welcome To Our Password Manager",
                                  "Eventually when you click here, we will give assistance on how " +
                                           "to login. For now enjoy this cool custom dialog box.");

    }

    @FXML
    private void closeApp(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    public void toggleVisiblePassword(ActionEvent actionEvent)
    {
        //if show password checkbox is selected, show password in cleartext
        if (showCheckBox.isSelected())
        {
            passShowFld.setText(passHiddenFld.getText());
            passShowFld.setVisible(true);
            passHiddenFld.setVisible(false);
            return;
        }

        //otherwise don't show the password in cleartext
        passHiddenFld.setText(passShowFld.getText());
        passShowFld.setVisible(false);
        passHiddenFld.setVisible(true);

    }

    public void openMainWindow(ActionEvent actionEvent) throws Exception
    {
        //temporary for now. we will need to validate password, just trying to see if the
        //stage will load properly
        try
        {
            URL loc = getClass().getClassLoader().getResource(Config.PATH_LAYOUTS + "mainwindow/MainWindow.fxml");
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root1 = loader.load();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            okBtn.getScene().getWindow().hide();
            stage2.show();
        }
        catch (Exception e)
        {
            System.out.println("Error loading main scene");
            e.printStackTrace();
        }

    }
}
