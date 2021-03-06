package cn.edik;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TaskDemo extends Application {


    public void init(Stage primaryStage) {
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");

        HBox mHbox = new HBox(10);
        ProgressIndicator mBar = new ProgressIndicator(0);
        mBar.setMaxSize(150, 150);
        Label mLabel = new Label("Loading...0%");
        mLabel.setFont(new Font(10));
        mHbox.getChildren().add(mBar);
        mHbox.getChildren().add(mLabel);

        Task<Void> progressTask = new Task<Void>() {

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Succeeded");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                updateMessage("Cancelled");
            }

            @Override
            protected void failed() {
                super.failed();
                updateMessage("Failed");
            }

            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(50);
                    updateProgress(i + 1, 100);
                    updateMessage("Loading..." + (i + 1) + "%");
                }
                updateMessage("Finish");
                return null;
            }
        };

        StackPane root = new StackPane();
        root.getChildren().addAll(veil, mBar, mLabel);
        Scene scene = new Scene(root, 300, 250);
        veil.visibleProperty().bind(progressTask.runningProperty());
        mBar.progressProperty().bind(progressTask.progressProperty());
        mLabel.textProperty().bind(progressTask.messageProperty());

        primaryStage.setTitle("The lesson of Task");
        primaryStage.setScene(scene);

        new Thread(progressTask).start();
    }


    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

