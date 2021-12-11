package gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.simulation.Simulation;
import model.simulation.SimulationConfig;
import model.world.impl.BoundaryWord;
import model.world.impl.InfiniteWorld;

public class SimulationController extends AnimationTimer {

    private SimulationConfig simulationConfig;
    private Simulation simulationInfinite;
    private MapDrawer mapDrawerInfinite;

    private Simulation simulationBoundary;
    private MapDrawer mapDrawerBoundary;


    private long lastUpdate = 0;

    @FXML
    private HBox container;

    public void initializeSimulation() {
        this.simulationInfinite = new Simulation(this.simulationConfig, InfiniteWorld.class);
        this.mapDrawerInfinite = new MapDrawer(simulationInfinite);

        this.simulationBoundary = new Simulation(this.simulationConfig, BoundaryWord.class);
        this.mapDrawerBoundary = new MapDrawer(simulationBoundary);

        this.container.getChildren().add(mapDrawerInfinite.getCanvas());
        this.container.getChildren().add(mapDrawerBoundary.getCanvas());

    }

    public void setSimulationConfig(SimulationConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
    }

    @Override
    public void handle(long now) {
        if (now - this.lastUpdate >= 30_000_000) {

            this.simulationInfinite.nextPeriod();
            this.mapDrawerInfinite.draw();

            this.simulationBoundary.nextPeriod();
            this.mapDrawerBoundary.draw();

            this.lastUpdate = now;
        }
    }
}
