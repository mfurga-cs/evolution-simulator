import model.entity.impl.Animal;
import model.world.World;
import model.world.impl.BoundaryWord;

public class App {
    public static void main(String[] args) {

        World world = new BoundaryWord();

        world.place(new Animal(world));

        System.out.println(world.getAllEntities());
    }
}
