package io.github.mathiasberwig;

public class Main {

    private static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

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

    private static void printGame(final int[][] payoffs) {
        System.out.printf("Playing with the following game: %n");
        // for each row
        for (int row = -1; row < payoffs.length; row++) {
            // print row header
            if (row == -1) System.out.print(" ");
            else System.out.printf("%s", ALPHABET[row]);

            // for each colum
            for (int column = 0; column < payoffs[0].length; column++) {
                // print column header | payoff
                if (row == -1) System.out.printf("   %s ", ALPHABET[column]);
                else System.out.printf("%+4d ", payoffs[row][column]);
            }
            // new line for next row
            System.out.printf("%n");
        }
        // new line for end of game
        System.out.printf("%n");
    }

    public static void play(final int[][] payoffs, final int numberOfPlays, final boolean startRandom) {
        printGame(payoffs);

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