package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginFormController {

    @FXML
    private Button btnClose;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnScoreList;

    void initialize(){

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void btnMinimizeOnAction(ActionEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void btnPlayOnAction(ActionEvent event) {

    }

    @FXML
    void btnScoreListOnAction(ActionEvent event) {

    }
}
