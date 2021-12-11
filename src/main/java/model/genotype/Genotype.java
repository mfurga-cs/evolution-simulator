package model.genotype;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Genotype {

    public static final int GENOTYPE_LENGTH = 32;
    public static final int GENOTYPE_MAX_GEN_VALUE = 7;

    private final int[] genes = new int[GENOTYPE_LENGTH];

    private Genotype() {}

    public static Genotype mix(Genotype a, Genotype b, int ratio) {

        assert ratio >= 0 && ratio <= GENOTYPE_LENGTH;

        Genotype c = new Genotype();
        int side = ThreadLocalRandom.current().nextInt(0, 2);

        int i = 0;
        if (side == 0) {
            for (; i < ratio; i++) {
                c.genes[i] = a.getAt(i);
            }
            for (; i < GENOTYPE_LENGTH; i++) {
                c.genes[i] = b.getAt(i);
            }
        } else {
            for (; i < GENOTYPE_LENGTH - ratio; i++) {
                c.genes[i] = b.getAt(i);
            }
            for (; i < GENOTYPE_LENGTH; i++) {
                c.genes[i] = a.getAt(i);
            }
        }

        Arrays.sort(c.genes);
        return c;
    }

    public static Genotype random() {
        Genotype genotype = new Genotype();
        for (int i = 0; i < GENOTYPE_LENGTH; i++) {
            int gene = ThreadLocalRandom.current().nextInt(0, GENOTYPE_MAX_GEN_VALUE + 1);
            genotype.genes[i]= gene;
        }
        Arrays.sort(genotype.genes);
        return genotype;
    }

    public int getAt(int index) {
        if (index < 0 || index >= GENOTYPE_LENGTH) {
            throw new IllegalArgumentException("Index must be between 0 and " + (GENOTYPE_LENGTH - 1));
        }
        return this.genes[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genotype genotype = (Genotype) o;
        return Arrays.equals(this.genes, genotype.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.genes);
    }

    @Override
    public String toString() {
        return Arrays.stream(this.genes).mapToObj(String::valueOf).collect(Collectors.joining(""));
    }
}
