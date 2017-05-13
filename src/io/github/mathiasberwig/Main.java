package io.github.mathiasberwig;

public class Main {

    public static void main(String... args) {
        final int[][] payoffs = {
              /*          Colin        */
              /* A   B   C   D   E   F */
/*       A */  { 4, -4,  3,  2, -3,  3},
/* Rose  B */  {-1, -1, -2,  0,  0,  4},
/*       C */  {-1,  2,  1, -1,  2, -3}
        };

        play(payoffs, 15, true);
    }

    public static void play(final int[][] payoffs, final int numberOfPlays, final boolean startRandom) {
        System.out.printf("Running with the following game:%n%n" +
                        "                    Colin%n" +
                        "        |   A    B    C    D    E    F %n" +
                        "       -|------------------------------%n" +
                        "       A| %+3d  %+3d  %+3d  %+3d  %+3d  %+3d %n" +
                        "Rose   B| %+3d  %+3d  %+3d  %+3d  %+3d  %+3d %n" +
                        "       C| %+3d  %+3d  %+3d  %+3d  %+3d  %+3d %n%n",
                payoffs[0][0], payoffs[0][1], payoffs[0][2], payoffs[0][3], payoffs[0][4], payoffs[0][5],
                payoffs[1][0], payoffs[1][1], payoffs[1][2], payoffs[1][3], payoffs[1][4], payoffs[1][5],
                payoffs[2][0], payoffs[2][1], payoffs[2][2], payoffs[2][3], payoffs[2][4], payoffs[2][5]);

        final Player rose = new Player("Rose", true, "ABC", startRandom);
        final Player colin = new Player("Colin", false, "ABCDEF", startRandom);

        int roseVictories = 0, colinVictories = 0, draws = 0;

        for (int i = 0; i < numberOfPlays; i++) {
            // Get the chosen strategy for each player
            final int roseStrategy = rose.play(payoffs, colin);
            final int colinStrategy = colin.play(payoffs, rose);

            // Print the played strategy
            System.out.printf(
                    "%d) Rose played %s and Colin played %s. ",
                    i + 1, rose.getStrategyName(roseStrategy), colin.getStrategyName(colinStrategy)
            );

            // Check who won
            final int payoff = payoffs[roseStrategy][colinStrategy];
            if (payoff > 0) {
                roseVictories++;
                System.out.println("Rose won.");
            }
            else if (payoff == 0) {
                draws++;
                System.out.println("It was a draw.");
            }
            else {
                colinVictories++;
                System.out.println("Colin won.");
            }
        }

        // Print the statistics of the game
        System.out.printf("Rose won %d times. Colin won %d times. They tied %d times.", roseVictories, colinVictories, draws);
    }
}