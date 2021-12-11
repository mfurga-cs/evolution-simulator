package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.simulation.SimulationConfig;

import java.io.IOException;

public class SettingsController {

    @FXML
    private TextField mapWidth;

    @FXML
    private TextField mapHeight;

    @FXML
    private TextField startAnimals;

    @FXML
    private TextField startGrass;

    @FXML
    private TextField initialAnimalEnergy;

    @FXML
    private TextField initialGrassEnergy;

    @FXML
    private TextField moveEnergy;

    @FXML
    private Slider jungleRatio;

    @FXML
    private void initialize() {
        this.mapWidth.setText("50");
        this.mapHeight.setText("50");
        this.startAnimals.setText("60");
        this.startGrass.setText("200");
        this.initialAnimalEnergy.setText("200");
        this.initialGrassEnergy.setText("80");
        this.moveEnergy.setText("5");
        this.jungleRatio.adjustValue(40);
    }

    public void start(ActionEvent event) throws IOException {
        int mapWidth = Integer.parseInt(this.mapWidth.getText());
        int mapHeight = Integer.parseInt(this.mapHeight.getText());
        int startAnimals = Integer.parseInt(this.startAnimals.getText());
        int startGrass = Integer.parseInt(this.startGrass.getText());
        int initialAnimalEnergy = Integer.parseInt(this.initialAnimalEnergy.getText());
        int initialGrassEnergy = Integer.parseInt(this.initialGrassEnergy.getText());
        int moveEnergy = Integer.parseInt(this.moveEnergy.getText());
        int jungleRatio = (int)this.jungleRatio.getValue();

        SimulationConfig simulationConfig = new SimulationConfig(
            mapWidth, mapHeight, startAnimals, startGrass, initialAnimalEnergy, initialGrassEnergy, moveEnergy, jungleRatio
        );

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Parent root = loader.load();

            SimulationController simulationController = loader.getController();
            simulationController.setSimulationConfig(simulationConfig);
            simulationController.initializeSimulation();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(1000);
            stage.setWidth(1000);

            stage.show();


            simulationController.start();
        } catch (Exception e) {
            System.out.println("Exception!!!!!");
            System.out.println(e.getMessage());
        }

    }

}
