import java.util.Random;

public class Randomizer {

    private static Random random = new Random (10);

    public static int getRandom() {
        return random.nextInt();
    }
}
