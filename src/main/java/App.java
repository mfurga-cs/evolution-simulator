import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.simulation.Simulation;
import model.simulation.SimulationConfig;

public class App extends Application {

    public static void main(String[] args) {

        SimulationConfig config = new SimulationConfig(
                30,
                30,
                50,
                30,
                100,
                20,
                50,
                30
        );

//        Simulation engine = new Simulation(config);
//        engine.run();


        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Evolution Generator");

        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());


//        primaryStage.setWidth(420);
//        primaryStage.setHeight(420);
//        primaryStage.setResizable(true);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
