import model.simulation.Simulation;
import model.world.impl.BoundaryWord;

public class App {
    public static void main(String[] args) {

        Simulation engine = new Simulation(new BoundaryWord());
        engine.run();

    }
}
