package com.passwordmanager.gui.controllers;

import com.mysql.cj.log.NullLogger;
import com.passwordmanager.database.accessors.AccessLevelDB;
import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.accessors.PasswordsDB;
import com.passwordmanager.database.accessors.UserDB;
import com.passwordmanager.database.objects.AccessLevel;
import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.database.objects.Password;
import com.passwordmanager.database.objects.User;
import com.passwordmanager.gui.base.DialogBox;
import com.passwordmanager.utils.DB;
import com.passwordmanager.utils.HashPassword;
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
import javafx.scene.layout.*;
import javafx.scene.control.MenuBar;
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
    private User selectedUser = null;
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
    @FXML
    private MenuItem viewProfile;

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
     *  Account Panel
     */
    @FXML
    private Pane profilePane;
    @FXML
    private Label accountTitle;
    @FXML
    private Label infoFirstname;
    @FXML
    private Label infoLastname;
    @FXML
    private Label infoAccessLevel;
    @FXML
    private Button newPassword;
    @FXML
    private Button deleteUser;
    @FXML
    private Accordion userList;

    @FXML
    public void viewMyProfile(ActionEvent actionEvent) {
        if (!profilePane.isVisible()) {
            profilePane.setVisible(true);
            profilePane.setPrefWidth(300);
            ((MenuItem)actionEvent.getSource()).setText("Hide Profile");
            accountTitle.setText(user.getUser_username() + ":" + user.getUser_ID());
            infoFirstname.setText(user.getUser_first_name());
            infoLastname.setText(user.getUser_last_name());
            infoAccessLevel.setText(AccessLevelDB.getAccessLevel(DB.ACCESS_ID, user.getAccess_level()).getAccess_title());

            ArrayList<User> userDB = UserDB.getUsersChildren(user.getAccess_level());
            ArrayList<AccessLevel> levels = AccessLevelDB.getAccessLevelsChildren(user.getAccess_level());
            deleteUser.setOnAction(event -> {
                UserDB.deleteFromDB(user.getUser_ID());
                logout();
            });
            userList.getPanes().clear();
            for (User usr : userDB) {
                TitledPane pane = new TitledPane();
                Button delete = new Button();
                Button save = new Button();
                HBox content = new HBox();
                ComboBox dropdown = new ComboBox();
                dropdown.setValue(AccessLevelDB.getAccessLevel(DB.ACCESS_ID, usr.getAccess_level()).getAccess_title());
                for (AccessLevel lvl : levels) {
                    dropdown.getItems().add(lvl.getAccess_title());
                }
                pane.setText(usr.getUser_username() + ":" + usr.getUser_ID());
                delete.setText("Delete");
                delete.getStylesheets().add(Layouts.DELETE_CSS);
                delete.setPrefWidth(50);
                delete.setPrefHeight(25);
                delete.setOnAction(event -> {
                    selectedUser = usr;
                    UserDB.deleteFromDB(usr.getUser_ID());
                    viewMyProfile(event);
                });
                save.setText("Save");
                save.getStylesheets().add(Layouts.GENERAL_CSS);
                save.setPrefWidth(50);
                save.setPrefHeight(25);
                save.setOnAction(event -> {
                    selectedUser = usr;
                    selectedUser.setAccess_level(AccessLevelDB.getAccessLevel(DB.ACCESS_TITLE, dropdown.getValue()).getAccess_ID());
                    UserDB.updateUser(selectedUser);
                    viewMyProfile(event);
                });
                content.setSpacing(5);
                content.setPrefWidth(295);
                content.getChildren().addAll(dropdown, save, delete);
                pane.setContent(content);
                userList.getPanes().add(pane);
            }
        }else {
            hideProfile();
            ((MenuItem)actionEvent.getSource()).setText("View Profile");
        }
    }

    public void hideProfile() {
        profilePane.setVisible(false);
        profilePane.setPrefWidth(0);
    }

    @FXML
    public void resetPassword() {
        try {

            URL loc = getClass().getClassLoader().getResource(Layouts.RESETPASSWORD_FXML);
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root2 = loader.load();
            Stage stage3 = new Stage();
            stage3.setTitle("Reset Password");
            stage3.setScene(new Scene(root2));
            stage3.initModality(Modality.APPLICATION_MODAL);
            stage3.showAndWait();
            PasswordResetController prc = loader.getController();
            if (!prc.getPasswordNew().isEmpty()) {
                user.setUser_password(prc.getPasswordNew());
                UserDB.updateUser(user);
                Logger.getLogger(PasswordResetController.class.getName()).log(Level.INFO, "Password Reset");
            } else {
                Logger.getLogger(PasswordResetController.class.getName()).log(Level.INFO, "Password Not Reset");
            }

        } catch (Exception e) {
            DialogBox.showError("Error", "There was a problem opening the desired window");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "ERROR OPENING PASSWORD BOX", e);
        }
    }

    /**
     *  Info Panel
     */
    @FXML
    private AnchorPane bottomInformationPane;
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
    private AnchorPane passFields;
    @FXML
    private Label passError;
    @FXML
    private PasswordField passHiddenFld;
    @FXML
    private TextField passShowFld;
    @FXML
    private CheckBox showCheckBox;
    @FXML
    private Button changeBtnPass;

    //Password Field - Bottom
    @FXML
    private AnchorPane confirmFields;
    @FXML
    private PasswordField confirmHiddenFld;
    @FXML
    private TextField confirmShowFld;
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
    @FXML
    private Button deleteBtn;

    //help button top panel
    @FXML
    private MenuItem aboutMenu;

    @FXML
    private void deletePassword(ActionEvent actionEvent) {
        PasswordsDB.deletePassword(DB.PASSWORD_ID,selectedPwd.getPassword_ID());
        showPasswords();
        if (bottomInformationPane.isVisible())
            toggleInfoPane();
    }

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
        passwordList.getPanes().clear();
        ArrayList<Password> pwdsDB = PasswordsDB.getPasswords(DB.FOLDER_ID, selected.getFolder_ID());

        for (Password pwds: pwdsDB) {
            TitledPane pane = new TitledPane();
            VBox content = new VBox();
            content.setMinWidth(670);
            content.setSpacing(5);
            HBox contentA = new HBox();
            contentA.setSpacing(75);
            VBox contentB = new VBox();
            VBox contentC = new VBox();
            VBox contentD = new VBox();
            HBox buttonBox = new HBox();
            pane.setText(pwds.getPassword_title());
            contentB.getChildren().addAll(
                    new Label("Creator: " + UserDB.getUser(DB.USER_ID, pwds.getPassword_owner()).getUser_username()),
                    new Label("Username: " + pwds.getPassword_username())
            );
            contentC.getChildren().addAll(
                    new Label("Password: " + pwds.getPassword()),
                    new Label("Last Edited: " + pwds.getPassword_timestamp().toString())
                    );
            contentD.getChildren().addAll(
                    new Label("URL: " + pwds.getPassword_url()),
                    new Label("Notes: " + pwds.getPassword_notes())
            );

            Button edit = new Button();
            edit.setText("Edit");
            edit.setPrefWidth(75);
            edit.setPrefHeight(30);
            edit.getStylesheets().add(Layouts.GENERAL_CSS);
            edit.setOnAction(event -> {
                selectedPwd = pwds;
                populateInfoPane(pwds);
            });

            Button delete = new Button();
            delete.setText("Delete");
            delete.setPrefWidth(75);
            delete.setPrefHeight(30);
            delete.getStylesheets().add(Layouts.DELETE_CSS);
            delete.setOnAction(event -> {
                selectedPwd = pwds;
                deletePassword(event);
            });
            buttonBox.getChildren().addAll(edit,delete);
            contentA.getChildren().addAll(contentB, contentC, buttonBox);
            content.getChildren().add(contentA);
            content.getChildren().add(contentD);
            pane.setContent(content);
            passwordList.getPanes().add(pane);

        }

    }

    public void populateInfoPane(Password pwd) {
        bottomInformationPane.setVisible(true);
        bottomInformationPane.setDisable(false);
        bottomInformationPane.setPrefHeight(275);
        titleField.setText(pwd.getPassword_title());
        urlField.setText(pwd.getPassword_url());
        notesField.setText(pwd.getPassword_notes());
        timeLbl.setText(pwd.getPassword_timestamp().toString());
        creatorLbl.setText(UserDB.getUser(DB.USER_ID,pwd.getPassword_owner()).getUser_username());
        usernameField.setText(pwd.getPassword_username());
        passHiddenFld.setText(pwd.getPassword());
        passShowFld.setText(pwd.getPassword());
        confirmHiddenFld.setText(pwd.getPassword());
        confirmShowFld.setText(pwd.getPassword());
    }

    @FXML
    public void toggleInfoPane() {
        bottomInformationPane.setVisible(false);
        bottomInformationPane.setDisable(true);
        bottomInformationPane.setPrefHeight(0);
    }

    @FXML
    public void change(ActionEvent actionEvent) {
        passFields.setDisable(!passFields.isDisabled());
        confirmFields.setDisable(!confirmFields.isDisabled());
        passShowFld.setVisible(!passShowFld.isVisible());
        confirmShowFld.setVisible(!confirmShowFld.isVisible());
        saveBtnPass.setVisible(!saveBtnPass.isVisible());
        cancelBtnPass.setVisible(!cancelBtnPass.isVisible());
        changeBtnPass.setVisible(!changeBtnPass.isVisible());
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelBtnTitle) {
            titleField.setText(selectedPwd.getPassword_title());
        } else if (actionEvent.getSource() == cancelBtnUser) {
            usernameField.setText(selectedPwd.getPassword_username());
        } else if (actionEvent.getSource() == cancelBtnPass) {
            passHiddenFld.setText(selectedPwd.getPassword());
            passShowFld.setText(selectedPwd.getPassword());
            confirmHiddenFld.setText(selectedPwd.getPassword());
            confirmShowFld.setText(selectedPwd.getPassword());
            change(actionEvent);
        } else if (actionEvent.getSource() == cancelBtnURL) {
            urlField.setText(selectedPwd.getPassword_url());
        } else if (actionEvent.getSource() == cancelBtnNotes) {
            notesField.setText(selectedPwd.getPassword_notes());
        } else {
            Logger.getLogger(MainController.class.getName()).log(Level.WARNING, null, actionEvent.getSource() + " is invalid button.");
        }

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
        selectedPwd = PasswordsDB.getPassword(DB.PASSWORD_ID, selectedPwd.getPassword_ID());
        showPasswords();
        populateInfoPane(selectedPwd);
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
                if (!newPassword.isEmpty())
                    wasInserted = PasswordsDB.insertPassword(new Password(newPassword, pbController.getTitle(), treeView.getSelectionModel().getSelectedItem().getValue().getFolder_ID(), user.getUser_ID()));
                showPasswords();
                if (wasInserted)
                    Logger.getLogger(MainController.class.getName()).log(Level.INFO, "INSERTED");

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
        confirmShowFld.setText(confirmHiddenFld.getText());
        confirmHiddenFld.setVisible(!confirmHiddenFld.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            confirmCheckBox.setSelected(true);
        }
    }

    public void showAbout(ActionEvent actionEvent)
    {
        DialogBox.showInformation("About KeyCrypt",
                 "Welcome to KeyCrypt Password Manager.\n\n" +
                          "To enter a new password, select the file option and click new password. Then enter\n" +
                          "the desired information relating to that password. Once you have entered a password,\n" +
                          "you may add additional information regarding that password, such as the login, associated URL,\n" +
                          "and additional notes which could be helpful to the team using the password.\n\n" +
                          "To logout, select the File option and click logout.\n\n" +
                          "to view information a specific profile, select the file option and select view profile.");
    }
}