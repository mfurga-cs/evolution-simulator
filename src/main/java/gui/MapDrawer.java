package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.simulation.Simulation;
import model.world.Section;

public class MapDrawer {

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 600;

    private final Simulation simulation;
    private final Canvas canvas;
    private final int blockFactor;

    public MapDrawer(Simulation simulation) {
        this.simulation = simulation;
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        this.blockFactor = CANVAS_WIDTH / this.simulation.getWorld().getWidth();

        this.draw();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void draw() {
        for (int x = 0; x < this.simulation.getWorld().getWidth(); x++) {
            for (int y = 0; y < this.simulation.getWorld().getHeight(); y++) {
                drawSquare(x, y, Color.rgb(171, 230, 108));
            }
        }

        this.simulation.getWorld().getFreePositionsBySection(Section.JUNGLE)
                .forEach(p -> drawSquare(p.getX(), p.getY(), Color.rgb(52, 140, 15)));

        this.simulation.getWorld().getAllEntities().
                forEach(e -> drawSquare(e.getPosition().getX(), e.getPosition().getY(), e.getColor()));

    }


    //    public Canvas getCanvas() {
//        Canvas canvas = new Canvas(500, 500);
//        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//
//        for (int x = 0; x < 30; x++) {
//            for (int y = 0; y < 30; y++) {
//                drawSquare(graphicsContext, x, y, Color.WHITE, Color.SILVER);
//            }
//        }
//
//        return canvas;
//    }


    private void drawSquare(int x, int y, Paint fill) {
        double hFactor = 5;
        double vFactor = 5;

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        gc.setLineWidth(2);
//        gc.setStroke(stroke);
        gc.setFill(fill);
        gc.fillRect(x * this.blockFactor, y * this.blockFactor, this.blockFactor, this.blockFactor);
//        gc.strokeRect(x * this.blockFactor, y * this.blockFactor, this.blockFactor, this.blockFactor);
    }


    public static GridPane start() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);


        int width = 30;
        int height = 30;

        for (int x = 0 ; x < width ; x++) {
            ColumnConstraints cc = new ColumnConstraints(20);
            grid.getColumnConstraints().add(cc);
        }

        for (int y = 0 ; y < height ; y++) {
            RowConstraints rc = new RowConstraints(20);
            grid.getRowConstraints().add(rc);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle(2, 2);
                rectangle.setStroke(Color.YELLOW);
                stackPane.getChildren().add(rectangle);

                grid.add(rectangle, x, y, 1, 1);
            }
        }

        return grid;
    }

}
