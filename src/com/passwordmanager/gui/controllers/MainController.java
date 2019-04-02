package com.passwordmanager.gui.controllers;

import com.mysql.cj.log.NullLogger;
import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.accessors.PasswordsDB;
import com.passwordmanager.database.accessors.UserDB;
import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.database.objects.Password;
import com.passwordmanager.database.objects.User;
import com.passwordmanager.gui.base.DialogBox;
import com.passwordmanager.utils.DB;
import com.passwordmanager.utils.Images;
import com.passwordmanager.utils.Layouts;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Platform.exit;

/**
 * @author Justin Scott
 * @author Patrick Kelly
 */
public class MainController
{
    private static User user = null;
    private static Folder selected = null;
    private Password selectedPwd = null;


    @FXML
    private BorderPane borderPane;

    /**
     *  Menu Bar
     */
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem newPasswordMenu;

    /**
     *  Folder Viewer
     */
    @FXML
    private TreeView<Folder> treeView;


    /**
     *  Password Viewer
     */
    @FXML
    private Accordion passwordList;

    /**
     *  Info Panel
     */
    //Title
    @FXML
    private TextField titleField;
    @FXML
    private Button cancelBtnTitle;
    @FXML
    private Button saveBtnTitle;

    //Username
    @FXML
    private TextField usernameField;
    @FXML
    private Button cancelBtnUser;
    @FXML
    private Button saveBtnUser;

    //URL
    @FXML
    private TextField urlField;
    @FXML
    private Button cancelBtnURL;
    @FXML
    private Button saveBtnURL;

    //Notes
    @FXML
    private TextArea notesField;
    @FXML
    private Button cancelBtnNotes;
    @FXML
    private Button saveBtnNotes;

    //Password Field - Top
    @FXML
    private Label passError;
    @FXML
    private PasswordField passHiddenFld;
    @FXML
    private PasswordField passShowFld;
    @FXML
    private CheckBox showCheckBox;
    @FXML
    private Button changeBtnPass;

    //Password Field - Bottom
    @FXML
    private PasswordField confirmHiddenFld;
    @FXML
    private PasswordField confirmShowFld;
    @FXML
    private CheckBox confirmCheckBox;
    @FXML
    private Button cancelBtnPass;
    @FXML
    private Button saveBtnPass;

    //Bottom Info
    @FXML
    private Label creatorLbl;
    @FXML
    private Label timeLbl;



    private void buildFolders()
    {
        treeView.setRoot(FolderBuilder.buildTreeView(FoldersDB.getFoldersByAL(user.getAccess_level())));
        treeView.setShowRoot(false);
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener) (observable, oldValue, newValue) -> {
                    TreeItem<Folder> selectedItem = (TreeItem<Folder>) newValue;
                    selected = selectedItem.getValue();

                showPasswords();
                });

    }

    public void showPasswords() {
        selected = treeView.getSelectionModel().getSelectedItem().getValue();
        //list.getItems().clear();
        ArrayList<Password> pwdsDB = PasswordsDB.getPasswords(DB.FOLDER_ID, selected.getFolder_ID());
        //for (Password pwds : pwdsDB) {
        //    list.getItems().add(pwds);
        //}
        //Accordion passwordList = new Accordion();
        for (Password pwds: pwdsDB) {
            TitledPane pane = new TitledPane();
            VBox content = new VBox();
            pane.setText(pwds.getPassword_title());
            content.getChildren().addAll(
                    new Label(pwds.getPassword_creatorid()),
                    new Label(pwds.getPassword_username()),
                    new Label(pwds.getPassword()),
                    new Label(pwds.getPassword_url()),
                    new Label(pwds.getPassword_notes()),
                    new Label(pwds.getPassword_timestamp())
                    );
            HBox contentP = new HBox();
            contentP.getChildren().add(content);
            Button edit = new Button();
            edit.setText("Edit");
            edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    populateInfoPane(pwds);
                    System.out.println(pwds.getPassword_title()); //Need to test
                    selectedPwd = pwds;
                }
            });
            contentP.getChildren().add(edit);
            passwordList.getPanes().add(pane);

        }

    }

    public void populateInfoPane(Password pwd) {
        titleField.setText(pwd.getPassword_title());
        urlField.setText(pwd.getPassword_url());
        notesField.setText(pwd.getPassword_notes());
        timeLbl.setText(pwd.getPassword_timestamp());
        creatorLbl.setText(pwd.getPassword_creatorid());
        usernameField.setText(pwd.getPassword_username());
        passHiddenFld.setText(pwd.getPassword());
        passShowFld.setText(pwd.getPassword());
        confirmHiddenFld.setText(pwd.getPassword());
        confirmShowFld.setText(pwd.getPassword());
    }

    @FXML
    public void change(ActionEvent actionEvent) {}

    @FXML
    public void cancel(ActionEvent actionEvent) {
        String name = ((Button)actionEvent.getSource()).getId();

    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (actionEvent.getSource() == saveBtnTitle) {
            selectedPwd.setPassword_title(titleField.getText());
        } else if (actionEvent.getSource() == saveBtnUser){
            selectedPwd.setPassword_username(usernameField.getText());
        } else if (actionEvent.getSource() == saveBtnPass){
            if (passHiddenFld.getText().equals(confirmHiddenFld.getText())) {
                selectedPwd.setPassword(passHiddenFld.getText());
                passError.setText("");
            }else {
                passError.setText("Passwords do not match");
            }
        } else if (actionEvent.getSource() == saveBtnURL){
            selectedPwd.setPassword_url(urlField.getText());
        } else if (actionEvent.getSource() == saveBtnNotes){
            selectedPwd.setPassword_notes(notesField.getText());
        } else {
            Logger.getLogger(MainController.class.getName()).log(Level.WARNING, null, actionEvent.getSource() + " is invalid button.");
        }
        PasswordsDB.updatePassword(selectedPwd);
    }

    public void setUser(User userLoggedIn) {
        user = userLoggedIn;
        buildFolders();
    }

    @FXML
    public void logout() {
        try {
        URL loc = getClass().getClassLoader().getResource(Layouts.LOGINSCREEN_FXML);
        FXMLLoader loader = new FXMLLoader(loc);
        Parent root1 = loader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        user = null;
        menuBar.getScene().getWindow().hide();
        stage2.show();
        } catch (Exception e) {
            DialogBox.showError("Fatal Error",
                "Error switching scenes. Please try again.");
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, "ERROR OPENING USER CREATION WINDOW", e);
        }
    }

    @FXML
    private void closeApp(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    public void createNewPassword(ActionEvent actionEvent) throws Exception
    {
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            try {
                boolean wasInserted = false;
                URL loc = getClass().getClassLoader().getResource(Layouts.PASSWORDBOX_FXML);
                FXMLLoader loader = new FXMLLoader(loc);
                Parent root2 = loader.load();
                Stage stage3 = new Stage();
                stage3.setTitle("New Password");
                stage3.setScene(new Scene(root2));
                stage3.initModality(Modality.APPLICATION_MODAL);
                stage3.showAndWait();
                PasswordBoxController pbController = loader.getController();
                //testing, just making sure the value is getting passed correctly
                //we can take the value stored in newPassword and save it into the DB
                String newPassword = pbController.getPass();
                System.out.println(newPassword);
                if (!newPassword.isEmpty())
                    wasInserted = PasswordsDB.insertPassword(new Password(newPassword, pbController.getTitle(), treeView.getSelectionModel().getSelectedItem().getValue().getFolder_ID()));
                showPasswords();
                if (wasInserted)
                    System.out.println("INSERTED");

            } catch (Exception e) {
                DialogBox.showError("Error", "There was a problem opening the desired window");
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "ERROR OPENING PASSWORD BOX", e);
            }
        } else {
            DialogBox.showError("Error",
                    "Error no folder selected, please select a folder.");
            Logger.getLogger(NewUserController.class.getName()).log(Level.WARNING, "NO FOLDER SELECTED");
        }

    }

    @FXML
    public void toggleVisiblePassword(MouseEvent actionEvent) {
        passShowFld.setText(passHiddenFld.getText());
        passHiddenFld.setVisible(!passHiddenFld.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            showCheckBox.setSelected(true);
        }
    }

    @FXML
    public void toggleVisibleConfirm(MouseEvent actionEvent) {
        passShowFld2.setText(passHiddenFld2.getText());
        passHiddenFld2.setVisible(!passHiddenFld2.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            showCheckBox2.setSelected(true);
        }
    }
}