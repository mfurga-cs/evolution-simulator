package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private void drawSquare(int x, int y, Paint fill) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setFill(fill);
        gc.fillRect(x * this.blockFactor, y * this.blockFactor, this.blockFactor, this.blockFactor);
    }
}
