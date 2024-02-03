package lk.ijse.controller;

import Sound.PlayMusic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class ScoreListFormController {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblHighestScore;

    @FXML
    private Label lblhighestScoreName;

    @FXML
    private AnchorPane scorePnae;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vbox;

    @FXML
    ImageView backgroundImage;

    MainFormController mainFormController = new MainFormController();

    PlayMusic playMusic;

    public void initialize(){



       new Thread(()->{
           backgroundAnimation();
       }).start();

    }

    private void backgroundAnimation() {

            String videoPath = "file:///D:/GDSE68/PROJECTS/CourseWorks/New%20folder/Snake-Game/src/main/resources/video/Jungle%20Forest%20-%20Free%20Cartoon%20Background%20Loop.mp4";
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        playMusic = new PlayMusic();
        playMusic.stopAllBackgroundMusic();
        navigateToMainForm();

    }

    void navigateToMainForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
        Parent root = loader.load();
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage stage = (Stage) this.scorePnae.getScene().getWindow();
            stage.setScene(subScene);


            TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
            tt.setFromX(-subScene.getWidth());
            tt.setToX(0);
            tt.play();
            stage.setScene(subScene);
            stage.setResizable(false);
            stage.show();
        }
    }

}
