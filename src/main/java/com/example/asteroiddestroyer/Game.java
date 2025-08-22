package com.example.asteroiddestroyer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Game extends Application {

    private Pane root = new Pane();
    private double time = 0.0;

    private Sprite player = new Sprite(300, 750, 40, 40, "player", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\Spaceship-removebg-preview.png" );
    private Sprite background = new Sprite(0, 0, 600, 800, "background", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\wallpaperflare.com_wallpaper (45).jpg");
    private Sprite newplayer = new Sprite(300, 750, 40, 40, "player", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\Spaceship-removebg-preview.png" );

    private Parent content() {
        root.setPrefSize(600, 800);
        root.getChildren().add(background);
        root.getChildren().add(player);
        Button restartGame = new Button("Restart");
        Button exitButton = new Button("Exit");

        restartGame.setLayoutX(253);
        restartGame.setLayoutY(400);
        restartGame.setFont(new Font("Broadway", 18));
//        restartGame.setBackground(Background.fill(Color.WHITE));

        exitButton.setLayoutX(283);
        exitButton.setLayoutY(474);
        exitButton.setFont(new Font("Broadway", 12));

            AnimationTimer game = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    update();
                    if (didplayerWin()) {
                        stop();
                        playVictorySong();
                        Stage winStage = new Stage();
                        Pane pane = new Pane();
                        pane.setPrefSize(600, 800);
                        Scene scene = new Scene(pane);

                        Label winMsg = new Label("YOU WIN!!!");
                        winMsg.setFont(new Font("Broadway", 34));
                        winMsg.setTextFill(Color.WHITE);
                        winMsg.setLayoutX(-60);
                        winMsg.setLayoutY(50);
                        winMsg.setTranslateX(260);
                        winMsg.setTranslateY(100);
                        winMsg.setPrefSize(200, 79);

                        pane.getChildren().add(background);
                        pane.getChildren().add(winMsg);
                        pane.getChildren().add(restartGame);
                        restartGame.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                pane.getChildren().clear();
                                winStage.close();
                                restartGame();
                                start();
                            }
                        });

                        pane.getChildren().add(exitButton);
                        exitButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                exit(0);
                            }
                        });

                        winStage.setScene(scene);
                        winStage.show();
                    }

                    if (isGameOver()) {
                        System.out.println("GAMEOVER :(");
                        stop();
                        playGameOverSound();

                        Stage loesrStage = new Stage();
                        Pane pane = new Pane();
                        pane.setPrefSize(600, 800);
                        Scene scene = new Scene(pane);
                        pane.getChildren().add(background);

                        Label gameOver = new Label("GAMEOVER");
                        gameOver.setFont(new Font("Broadway",34));
                        gameOver.setTextFill(Color.WHITE);
                        gameOver.setLayoutX(-73);
                        gameOver.setLayoutY(28);
                        gameOver.setTranslateX(260);
                        gameOver.setTranslateY(100);

                        Button exit = new Button("EXIT");
                        exit.setLayoutX(265);
                        exit.setLayoutY(450);
                        exit.setFont(new Font("Broadway",18));

                        pane.getChildren().addAll(gameOver, exit, restartGame);

                        restartGame.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                pane.getChildren().clear();
                                loesrStage.close();
                                restartGame();
                                start();
                            }
                        });

                        exit.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                exit(0);
                            }
                        });

                        loesrStage.setScene(scene);
                        loesrStage.show();
                    }

                    if (outOfBounds()) {
                        if (player.getTranslateX() < 0)
                            player.setTranslateX(590);
                        if (player.getTranslateX() > 600)
                            player.setTranslateX(0);
                    }
                }
            };

            game.start();

            nextLevel();

            return root;
    }
    private void playGameOverSound(){
        String path = "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\AudioClips\\gameover.wav";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer gameOverSound = new MediaPlayer(media);
        gameOverSound.play();
    }

    private void playVictorySong(){
        String audioPath = "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\AudioClips\\victory.mp3";
        Media media = new Media(new File(audioPath).toURI().toString());
        MediaPlayer victorySound = new MediaPlayer(media);
        victorySound.play();
    }

    private void nextLevel(){

        for(int i=0; i<5; i++){
//            Sprite asteroid = new Sprite(90+ i*100, 150, 60, 60, "asteroid", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\Asteroid_model_1.png");
//            root.getChildren().add(asteroid);
            Sprite alien = new Sprite(90+ i*100, 150, 30, 30, "alien", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\alien.png");
            root.getChildren().add(alien);
        }
    }

    private List<Sprite> sprites(){
        return root.getChildren().stream().map(node -> (Sprite)node).collect(Collectors.toList());
    }
    private void update(){
        time += 0.016;

        sprites().forEach(Sprite -> {
            switch (Sprite.type){
                case "alien":
                    for (int i = 0; i <= 1; i++) {
                        if (time >= i && time < i + 0.016) {
                            Sprite.moveDown();
                            break;
                        }
                    }

                    if (time >= 2) {
                        if (Math.random() < 0.3)
                            alienShoot(Sprite);
                        }
                    break;

                case "asteroid":

                    for (int i = 0; i <= 1; i++) {
                        if (time >= i && time < i + 0.016) {
                            Sprite.moveDown();
                            break;
                        }
                    }

                    if(Sprite.getBoundsInParent().intersects(player.getBoundsInParent())){

                        player.dead = true;
                        Sprite.dead = true;
                    }
                    break;

                case "alienbullet":
                    Sprite.moveDown();

                    if(Sprite.getBoundsInParent().intersects(player.getBoundsInParent())){
                        player.dead = true;
                        Sprite.dead = true;
                    }
                    break;

                case "playerbullet":
                    Sprite.moveUp();
                    sprites().stream().filter(e -> e.type.equals("alien")).forEach(alien -> {
                        if(Sprite.getBoundsInParent().intersects(alien.getBoundsInParent())){
                            alien.dead = true;
                            Sprite.dead = true;
                        }
                    });
                    break;
            }
        });

        root.getChildren().removeIf(node -> {
            Sprite s = (Sprite) node;
            return s.dead;
        });
        if(time > 2)
            time=0;

    }

    private boolean outOfBounds(){
        if(player.getTranslateX()+30 >= 0 && player.getTranslateX()+10 <= 600 ){
            return false;
        }
        return true;
    }

    private boolean isGameOver() {
        for (Sprite s : sprites()) {

            if (!(sprites().stream().anyMatch(sprite -> sprite.type.equals("player"))))
                return true;
        }
        return false;
    }

    private boolean didplayerWin(){
        for (Sprite s : sprites()) {
            if (!(sprites().stream().anyMatch(sprite -> sprite.type.equals("alien"))))
                return true;
//            if (!(sprites().stream().anyMatch(sprite -> sprite.type.equals("asteroid"))))
//                return true;
        }
        return false;
    }

    private void restartGame(){
        root.getChildren().clear();
        root.getChildren().add(background);
        System.out.println("Restarting the GAME!!!!");
        player =  new Sprite(300, 750, 40, 40, "player", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\Spaceship-removebg-preview.png" );
        time=0;
        player.setTranslateX(300);
        player.setTranslateY(750);
        nextLevel();
        root.getChildren().add(player);
    }

//    private void resetGame(){
//        root.getChildren().clear();
//        root.getChildren().add(background);
//        root.getChildren().add(player);
//        player.setTranslateX(300);
//        player.setTranslateY(750);
//        nextLevel();
//        System.out.println("Reseting....");
//    }
    private void playerShoot(Sprite cc){
        Sprite playerBullet = new Sprite((int)cc.getTranslateX()+10, (int)cc.getTranslateY(), 20,40, "playerbullet", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\playerlaser.png");

        root.getChildren().add(playerBullet);
        String shootPath = "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\AudioClips\\shoot.wav";
        Media shootingEffect = new Media(new File(shootPath).toURI().toString());
        MediaPlayer shoot = new MediaPlayer(shootingEffect);
        shoot.play();
    }

    private void alienShoot(Sprite cc){
        Sprite alienBullet = new Sprite((int)cc.getTranslateX()+10, (int)cc.getTranslateY(),20, 40, "alienbullet", "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\Images\\alienLaser.png" );
        root.getChildren().add(alienBullet);
        String shootPath = "D:\\Programing\\JavaFx\\AsteroidDestroyer\\src\\main\\resources\\com\\example\\asteroiddestroyer\\AudioClips\\shoot.wav";
        Media shootingEffect = new Media(new File(shootPath).toURI().toString());
        MediaPlayer shoot = new MediaPlayer(shootingEffect);
        shoot.play();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(content());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case W:
                    player.moveUp();
                    break;
                case DOWN:
                    player.moveDown();
                    break;
                case SPACE:
                    playerShoot(player);
                    break;
            }
        });
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            event.consume();
            quit(stage);
        });
    }
    public void quit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("QUIT DESTROYING ASTEROIDS");
        alert.setHeaderText("Are You Sure?!");
        alert.setContentText("Did you enjoy??");

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You're OUT OF THE GAME NOW!!!");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
