package gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.entity.impl.Animal;
import model.entity.impl.Grass;
import model.genotype.Genotype;
import model.simulation.Simulation;
import model.simulation.SimulationConfig;
import model.world.impl.BoundaryWord;
import model.world.impl.InfiniteWorld;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimulationController extends AnimationTimer {

    private SimulationConfig simulationConfig;

    private Simulation simulationInfinite;
    private MapDrawer mapDrawerInfinite;

    private Simulation simulationBoundary;
    private MapDrawer mapDrawerBoundary;

    private Label evolutionInfiniteDay;
    private Label evolutionInfiniteAnimals;
    private Label evolutionInfiniteGrass;
    private Label evolutionInfiniteEnergyAvg;
    private Label evolutionInfiniteGenotype;

    private Label evolutionBoundaryDay;
    private Label evolutionBoundaryAnimals;
    private Label evolutionBoundaryGrass;
    private Label evolutionBoundaryEnergyAvg;
    private Label evolutionBoundaryGenotype;

    private boolean running = false;
    private long lastUpdate = 0;

    @FXML
    private HBox container;

    @FXML
    private VBox infiniteBox;

    @FXML
    private VBox boundaryBox;

    public void initializeSimulation() {
        this.simulationInfinite = new Simulation(this.simulationConfig, InfiniteWorld.class);
        this.mapDrawerInfinite = new MapDrawer(simulationInfinite);

        this.simulationBoundary = new Simulation(this.simulationConfig, BoundaryWord.class);
        this.mapDrawerBoundary = new MapDrawer(simulationBoundary);

        this.infiniteBox.getChildren().add(mapDrawerInfinite.getCanvas());
        this.boundaryBox.getChildren().add(mapDrawerBoundary.getCanvas());


        Genotype infiniteGenotype = this.simulationInfinite.getWorld().getEntitiesByType(Animal.class)
                .stream()
                .map(a -> a.getGenotype())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();

        Genotype boundaryGenotype = this.simulationBoundary.getWorld().getEntitiesByType(Animal.class)
                .stream()
                .map(a -> a.getGenotype())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();

        this.evolutionInfiniteDay = new Label("Evolution day: 0");
        this.evolutionInfiniteAnimals = new Label("Number of animals: " + this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).size());
        this.evolutionInfiniteGrass = new Label("Number of grass: " + this.simulationInfinite.getWorld().getEntitiesByType(Grass.class).size());
        this.evolutionInfiniteEnergyAvg = new Label("Energy avg: " + this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage());
        this.evolutionInfiniteGenotype = new Label("Common genotype: " + infiniteGenotype);

        this.evolutionBoundaryDay = new Label("Evolution day: 0");
        this.evolutionBoundaryAnimals = new Label("Number of animals: " + this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).size());
        this.evolutionBoundaryGrass = new Label("Number of grass: " + this.simulationInfinite.getWorld().getEntitiesByType(Grass.class).size());
        this.evolutionBoundaryEnergyAvg = new Label("Energy avg: " + this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage());
        this.evolutionBoundaryGenotype = new Label("Common genotype: " + boundaryGenotype);

        this.infiniteBox.getChildren().add(this.evolutionInfiniteDay);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteAnimals);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteGrass);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteEnergyAvg);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteGenotype);


        this.boundaryBox.getChildren().add(this.evolutionBoundaryDay);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryAnimals);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryGrass);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryEnergyAvg);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryGenotype);
    }

    private void updateStats() {

        Genotype infiniteGenotype = this.simulationInfinite.getWorld().getEntitiesByType(Animal.class)
                .stream()
                .map(a -> a.getGenotype())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();

        Genotype boundaryGenotype = this.simulationBoundary.getWorld().getEntitiesByType(Animal.class)
                .stream()
                .map(a -> a.getGenotype())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();


        this.evolutionInfiniteDay.setText("Evolution day: " + this.simulationInfinite.getDay());
        this.evolutionInfiniteAnimals.setText("Number of animals: " + this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).size());
        this.evolutionInfiniteGrass.setText("Number of grass: " + this.simulationInfinite.getWorld().getEntitiesByType(Grass.class).size());
        this.evolutionInfiniteEnergyAvg.setText("Energy avg: " + this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage());
        this.evolutionInfiniteGenotype.setText("Common genotype: " + infiniteGenotype);

        this.evolutionBoundaryDay.setText("Evolution day: " + this.simulationBoundary.getDay());
        this.evolutionBoundaryAnimals.setText("Number of animals: " + this.simulationBoundary.getWorld().getEntitiesByType(Animal.class).size());
        this.evolutionBoundaryGrass.setText("Number of grass: " + this.simulationBoundary.getWorld().getEntitiesByType(Grass.class).size());
        this.evolutionBoundaryEnergyAvg.setText("Energy avg: " + this.simulationBoundary.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage());
        this.evolutionBoundaryGenotype.setText("Common genotype: " + boundaryGenotype);
    }

    public void setSimulationConfig(SimulationConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
    }

    public void toggle(ActionEvent event) {
        if (this.running) {
            this.stop();
        } else {
            this.start();
        }
    }

    @Override
    public void start() {
        super.start();
        this.running = true;
    }

    @Override
    public void stop() {
        super.stop();
        this.running = false;
    }

    @Override
    public void handle(long now) {
        if (now - this.lastUpdate >= 30_000_000) {
            this.simulationInfinite.nextPeriod();
            this.mapDrawerInfinite.draw();

            this.simulationBoundary.nextPeriod();
            this.mapDrawerBoundary.draw();

            this.updateStats();
            this.lastUpdate = now;
        }
    }
}
