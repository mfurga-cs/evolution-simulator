package model.simulation;

public class SimulationConfig {
    public final int mapWidth;
    public final int mapHeight;
    public final int startAnimals;
    public final int startGrass;
    public final int initialAnimalEnergy;
    public final int initialGrassEnergy;
    public final int moveEnergy;
    public final int jungleRatio;

    public SimulationConfig(int mapWidth, int mapHeight, int startAnimals, int startGrass,
                            int initialAnimalEnergy, int initialGrassEnergy, int moveEnergy, int jungleRatio) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.startAnimals = startAnimals;
        this.startGrass = startGrass;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.initialGrassEnergy = initialGrassEnergy;
        this.moveEnergy = moveEnergy;
        this.jungleRatio = jungleRatio;
    }
}
