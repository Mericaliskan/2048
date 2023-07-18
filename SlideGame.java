import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The SlideGame class is a JavaFX application that implements the popular 2048
 * game.
 * It provides a graphical user interface for the game and allows players to
 * slide tiles
 * in four directions (up, down, left, right) to merge them and reach the 2048
 * tile.
 * 
 * @author Merve Nur Çalýþkan
 */
public class SlideGame extends Application {
    private static final int BOARD_SIZE = 4;
    private static final int TILE_SIZE = 100;
    private static final int SPACING = 10;
    private static final Color BOARD_COLOR = Color.rgb(187, 173, 160);
    private static final Color EMPTY_TILE_COLOR = Color.rgb(205, 192, 180);
    private static final Color TEXT_COLOR = Color.rgb(119, 110, 101);
    private static final Color[] TILE_COLORS = {
            Color.rgb(238, 228, 218), Color.rgb(237, 224, 200), Color.rgb(242, 177, 121),
            Color.rgb(245, 149, 99), Color.rgb(246, 124, 95), Color.rgb(246, 94, 59),
            Color.rgb(237, 207, 114), Color.rgb(237, 204, 97), Color.rgb(237, 200, 80),
            Color.rgb(237, 197, 63), Color.rgb(237, 194, 46), Color.rgb(60, 58, 50)
    };

    private int[][] board;
    private GridPane gridPane;
    private Label[][] labels;
    // Constants and member variables omitted for brevity

    /**
     * Starts the JavaFX application and initializes the game board.
     *
     * @param primaryStage the primary stage for the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        gridPane = new GridPane();
        labels = new Label[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle tile = createTile(EMPTY_TILE_COLOR);
                gridPane.add(tile, col, row);

                Label label = createLabel("", TEXT_COLOR);
                gridPane.add(label, col, row);

                labels[row][col] = label;
            }
        }

        generateNewTile();

        StackPane root = new StackPane(gridPane);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(SPACING));
        root.setStyle("-fx-background-color: " + toRGBCode(BOARD_COLOR));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        primaryStage.setTitle("2048 Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Handles the key press event and performs the corresponding action.
     *
     * @param keyCode the code representing the pressed key
     */
    private void handleKeyPress(KeyCode keyCode) {
        if (keyCode == KeyCode.UP) {
            moveTilesUp();
        } else if (keyCode == KeyCode.DOWN) {
            moveTilesDown();
        } else if (keyCode == KeyCode.LEFT) {
            moveTilesLeft();
        } else if (keyCode == KeyCode.RIGHT) {
            moveTilesRight();
        }
    }

    /**
     * Moves the tiles upwards on the game board.
     *
     * @return true if any tile was moved, false otherwise
     */

    private void moveTilesUp() {
        boolean moved = false;

        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 1; row < BOARD_SIZE; row++) {
                if (board[row][col] != 0) {
                    int currentRow = row;
                    while (currentRow > 0 && board[currentRow - 1][col] == 0) {
                        board[currentRow - 1][col] = board[currentRow][col];
                        board[currentRow][col] = 0;
                        currentRow--;
                        moved = true;
                    }
                    if (currentRow > 0 && board[currentRow - 1][col] == board[currentRow][col]) {
                        board[currentRow - 1][col] *= 2;
                        board[currentRow][col] = 0;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            generateNewTile();
            updateBoard();
        }
    }

    /**
     * Moves the tiles downwards on the game board.
     *
     * @return true if any tile was moved, false otherwise
     */

    private void moveTilesDown() {
        boolean moved = false;

        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = BOARD_SIZE - 2; row >= 0; row--) {
                if (board[row][col] != 0) {
                    int currentRow = row;
                    while (currentRow < BOARD_SIZE - 1 && board[currentRow + 1][col] == 0) {
                        board[currentRow + 1][col] = board[currentRow][col];
                        board[currentRow][col] = 0;
                        currentRow++;
                        moved = true;
                    }
                    if (currentRow < BOARD_SIZE - 1 && board[currentRow + 1][col] == board[currentRow][col]) {
                        board[currentRow + 1][col] *= 2;
                        board[currentRow][col] = 0;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            generateNewTile();
            updateBoard();
        }
    }

    /**
     * Moves the tiles to the left on the game board.
     *
     * @return true if any tile was moved, false otherwise
     */
    private void moveTilesLeft() {
        boolean moved = false;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 1; col < BOARD_SIZE; col++) {
                if (board[row][col] != 0) {
                    int currentCol = col;
                    while (currentCol > 0 && board[row][currentCol - 1] == 0) {
                        board[row][currentCol - 1] = board[row][currentCol];
                        board[row][currentCol] = 0;
                        currentCol--;
                        moved = true;
                    }
                    if (currentCol > 0 && board[row][currentCol - 1] == board[row][currentCol]) {
                        board[row][currentCol - 1] *= 2;
                        board[row][currentCol] = 0;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            generateNewTile();
            updateBoard();
        }
    }

    /**
     * Moves the tiles to the right on the game board.
     *
     * @return true if any tile was moved, false otherwise
     */
    private void moveTilesRight() {
        boolean moved = false;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = BOARD_SIZE - 2; col >= 0; col--) {
                if (board[row][col] != 0) {
                    int currentCol = col;
                    while (currentCol < BOARD_SIZE - 1 && board[row][currentCol + 1] == 0) {
                        board[row][currentCol + 1] = board[row][currentCol];
                        board[row][currentCol] = 0;
                        currentCol++;
                        moved = true;
                    }
                    if (currentCol < BOARD_SIZE - 1 && board[row][currentCol + 1] == board[row][currentCol]) {
                        board[row][currentCol + 1] *= 2;
                        board[row][currentCol] = 0;
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            generateNewTile();
            updateBoard();
        }
    }

    /**
     * Generates a new tile with a value of 1 at a random empty position on the game
     * board.
     *
     * @return true if a new tile was generated, false if the game board is full
     */

    private void generateNewTile() {
        int emptyTiles = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) {
                    emptyTiles++;
                }
            }
        }

        if (emptyTiles > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyTiles) + 1;

            int count = 0;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == 0) {
                        count++;
                        if (count == randomIndex) {
                            board[row][col] = 1;
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the graphical representation of the game board based on the values in
     * the board array.
     */

    private void updateBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = board[row][col];
                Rectangle tile = createTile(
                        value == 0 ? EMPTY_TILE_COLOR : TILE_COLORS[(int) (Math.log(value) / Math.log(2))]);

                gridPane.add(tile, col, row);

                Label label = createLabel(value == 0 ? "" : String.valueOf(value), TEXT_COLOR);

                gridPane.add(label, col, row);

                labels[row][col] = label;
            }
        }
    }

    /**
     * Creates a rectangle (tile) with the specified color.
     *
     * @param color the color of the tile
     * @return the created rectangle (tile)
     */

    private Rectangle createTile(Color color) {
        Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
        tile.setFill(color);
        tile.setArcWidth(15);
        tile.setArcHeight(15);
        return tile;
    }

    /**
     * Creates a label with the specified text and color.
     *
     * @param text  the text to be displayed on the label
     * @param color the color of the label
     * @return the created label
     */
    private Label createLabel(String text, Color color) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        label.setTextFill(color);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    /**
     * Converts a Color object to its corresponding RGB code.
     *
     * @param color the Color object to be converted
     * @return the RGB code of the color
     */
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
