package gui;

public class Log {

    private String word;
    private int day;
    private int animals;
    private int grass;
    private double avgEnergy;
    private String genotype;

    public Log(String word, int day, int animals, int grass, double avgEnergy, String genotype) {
        this.word = word;
        this.day = day;
        this.animals = animals;
        this.grass = grass;
        this.avgEnergy = avgEnergy;
        this.genotype = genotype;
    }

    @Override
    public String toString() {
        return this.word + ", " + this.day + ", " + this.animals + ", " + this.grass + ", " + this.avgEnergy + ", " + this.genotype;
    }
}
