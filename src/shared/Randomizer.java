package shared;

import java.util.Random;

public class Randomizer {

    private static Random random = new Random ();

    public static int getRandom() {
        return random.nextInt((6-4) +1) +4;
    }
}
