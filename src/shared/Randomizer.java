package shared;

import java.util.Random;

public class Randomizer {

    private static Random random = new Random ();

    public static int getRandom() {
        return random.nextInt((6000-4000) +1000) +4000;
    }
}
