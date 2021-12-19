package gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entity.impl.Animal;
import model.entity.impl.Grass;
import model.genotype.Genotype;
import model.simulation.Simulation;
import model.simulation.SimulationConfig;
import model.world.impl.BoundaryWord;
import model.world.impl.InfiniteWorld;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimulationController extends AnimationTimer {

    private SimulationConfig simulationConfig;
    private List<Log> logs = new ArrayList<Log>();

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

        int infiniteDay = this.simulationInfinite.getDay();
        int infiniteAnimals = this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).size();
        int infiniteGrass = this.simulationInfinite.getWorld().getEntitiesByType(Grass.class).size();
        double infiniteEvgEnergy = this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage();

        int boundaryDay = this.simulationBoundary.getDay();
        int boundaryAnimals = this.simulationBoundary.getWorld().getEntitiesByType(Animal.class).size();
        int boundaryGrass = this.simulationBoundary.getWorld().getEntitiesByType(Grass.class).size();
        double boundaryEvgEnergy = this.simulationBoundary.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage();

        this.evolutionInfiniteDay = new Label("Evolution day: " + infiniteDay);
        this.evolutionInfiniteAnimals = new Label("Number of animals: " + infiniteAnimals);
        this.evolutionInfiniteGrass = new Label("Number of grass: " + infiniteGrass);
        this.evolutionInfiniteEnergyAvg = new Label("Energy avg: " + infiniteEvgEnergy);
        this.evolutionInfiniteGenotype = new Label("Common genotype: " + infiniteGenotype);

        this.evolutionBoundaryDay = new Label("Evolution day: " + boundaryDay);
        this.evolutionBoundaryAnimals = new Label("Number of animals: " + boundaryAnimals);
        this.evolutionBoundaryGrass = new Label("Number of grass: " + boundaryGrass);
        this.evolutionBoundaryEnergyAvg = new Label("Energy avg: " + boundaryEvgEnergy);
        this.evolutionBoundaryGenotype = new Label("Common genotype: " + boundaryGenotype);

        this.infiniteBox.getChildren().add(this.evolutionInfiniteDay);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteAnimals);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteGrass);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteEnergyAvg);
        this.infiniteBox.getChildren().add(this.evolutionInfiniteGenotype);
        this.logs.add(new Log("Infinite", infiniteDay, infiniteAnimals, infiniteGrass, infiniteEvgEnergy, infiniteGenotype.toString()));

        this.boundaryBox.getChildren().add(this.evolutionBoundaryDay);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryAnimals);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryGrass);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryEnergyAvg);
        this.boundaryBox.getChildren().add(this.evolutionBoundaryGenotype);
        this.logs.add(new Log("Boundary", boundaryDay, boundaryAnimals, boundaryGrass, boundaryEvgEnergy, boundaryGenotype.toString()));
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


        int infiniteDay = this.simulationInfinite.getDay();
        int infiniteAnimals = this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).size();
        int infiniteGrass = this.simulationInfinite.getWorld().getEntitiesByType(Grass.class).size();
        double infiniteEvgEnergy = this.simulationInfinite.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage();

        int boundaryDay = this.simulationBoundary.getDay();
        int boundaryAnimals = this.simulationBoundary.getWorld().getEntitiesByType(Animal.class).size();
        int boundaryGrass = this.simulationBoundary.getWorld().getEntitiesByType(Grass.class).size();
        double boundaryEvgEnergy = this.simulationBoundary.getWorld().getEntitiesByType(Animal.class).stream().map(a -> a.getEnergy()).mapToInt(Integer::intValue).summaryStatistics().getAverage();

        this.evolutionInfiniteDay.setText("Evolution day: " + infiniteDay);
        this.evolutionInfiniteAnimals.setText("Number of animals: " + infiniteAnimals);
        this.evolutionInfiniteGrass.setText("Number of grass: " + infiniteGrass);
        this.evolutionInfiniteEnergyAvg.setText("Energy avg: " + infiniteEvgEnergy);
        this.evolutionInfiniteGenotype.setText("Common genotype: " + infiniteGenotype);
        this.logs.add(new Log("Infinite", infiniteDay, infiniteAnimals, infiniteGrass, infiniteEvgEnergy, infiniteGenotype.toString()));

        this.evolutionBoundaryDay.setText("Evolution day: " + boundaryDay);
        this.evolutionBoundaryAnimals.setText("Number of animals: " + boundaryAnimals);
        this.evolutionBoundaryGrass.setText("Number of grass: " + boundaryGrass);
        this.evolutionBoundaryEnergyAvg.setText("Energy avg: " + boundaryEvgEnergy);
        this.evolutionBoundaryGenotype.setText("Common genotype: " + boundaryGenotype);
        this.logs.add(new Log("Boundary", boundaryDay, boundaryAnimals, boundaryGrass, boundaryEvgEnergy, boundaryGenotype.toString()));
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

    public void saveLogs(ActionEvent event) {
        if (this.running) {
            return;
        }
        try {
            FileWriter writer = new FileWriter("logs.csv");
            String header = "map, day, animals, grass, avgEnergy, genotype\n";
            String data = this.logs.stream().map(l -> l.toString()).collect(Collectors.joining("\n"));
            writer.write(header);
            writer.write(data);
            writer.close();
        } catch (Exception ignored) {}
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
