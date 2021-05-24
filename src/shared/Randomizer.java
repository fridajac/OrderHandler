package shared;

import java.util.Random;

public class Randomizer {

    private static Random random = new Random (3);

    public static int getRandom() {
        return random.nextInt();
    }
}
