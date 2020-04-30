package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    Pane pane;
    private final int TILE_SIZE = 200;
    boolean turn = true;
    boolean gameOn = true;
    String[][] board = new String[3][3];
    private final List<Rectangle> tiles = new ArrayList<>();
    private final List<Text> pieces = new ArrayList<>();

    public Controller() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
    }

    @FXML
    public void onMouseClicked(MouseEvent e) {
        if (e.getSource() instanceof Rectangle) {
            Rectangle tile = (Rectangle) e.getSource();
            if (!gameOn)
                return;
            placePiece(tile);
            checkWinner();
        }
    }

    @FXML
    public void onMouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Rectangle && gameOn) {
            Rectangle tile = (Rectangle) e.getSource();
            if (tile.getFill() != Color.TOMATO)
                tile.setFill(Color.SEAGREEN);
        }
    }

    @FXML
    private void onMouseExited(MouseEvent e) {
        if (e.getSource() instanceof Rectangle) {
            Rectangle tile = (Rectangle) e.getSource();
            if (tile.getFill() != Color.TOMATO)
                tile.setFill(Color.WHITE);
        }
    }

    private void placePiece(Rectangle tile) {
        if (tile.getFill() == Color.TOMATO)
            return;
        tile.setFill(Color.TOMATO);
        tiles.add(tile);
        Text piece = createPiece(tile);
        int x = (int) tile.getX() / TILE_SIZE;
        int y = (int) tile.getY() / TILE_SIZE;
        board[x][y] = piece.getText();
        pane.getChildren().add(piece);
    }

    private Text createPiece(Rectangle tile) {
        Text text = new Text(" ");
        text.setFont(new Font(70));
        text.setText(turn ? "X" : "O");
        turn = !turn;
        text.setX(tile.getX() + TILE_SIZE / 2f - 25);
        text.setY(tile.getY() + TILE_SIZE / 2f + 25);
        pieces.add(text);
        return text;
    }

    private void checkWinner() {
        boolean flag = false;
        if (tiles.size() < 3)
            return;
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals(" ") && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                announceWinner(board[i][0]);
                flag = true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!board[0][i].equals(" ") && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                announceWinner(board[0][i]);
                flag = true;
            }
        }
        if (!board[0][0].equals(" ") && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            announceWinner(board[0][0]);
            flag = true;
        }
        if (!board[0][2].equals(" ") && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            announceWinner(board[0][2]);
            flag = true;
        }
        if (tiles.size() == 9 && !flag)
            announceWinner("TIE");
    }

    private void announceWinner(String winner) {
        Text win = new Text(winner.equals("TIE") ? winner : "THE WINNER IS " + winner);
        win.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 50));
        win.setFill(Color.DODGERBLUE);
        win.setStrokeWidth(3);
        win.setStroke(Color.BLACK);
        win.setX(win.getText().equals("TIE") ? 250 : 30);
        win.setY(250);
        gameOn = false;
        Button replay = createButton();
        replay.setOnAction(e ->
        {
            replay.setVisible(false);
            pane.getChildren().remove(win);
            clearBoard();
            gameOn = true;
        });
        pane.getChildren().add(win);
        pane.getChildren().add(replay);
    }

    private Button createButton() {
        Button button = new Button("NEW GAME");
        button.setTranslateX(180);
        button.setTranslateY(350);
        //button.setPrefSize(120,60);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setStyle("-fx-font-size: 300%; -fx-background-color: linear-gradient(dodgerblue, black); -fx-text-fill: white");
        return button;
    }

    private void clearBoard() {
        for (Rectangle tile :
                tiles) {
            tile.setFill(Color.WHITE);
        }
        pane.getChildren().removeAll(pieces);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }

        }
        pieces.clear();
        tiles.clear();


    }

}
