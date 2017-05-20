package io.github.mathiasberwig;

public class Game {

    private static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final int[][] payoffs;
    private final int numberOfPlays;
    private final boolean startRandom;
    private final boolean printIndividualChoices;

    public Game(int[][] payoffs, int numberOfPlays, boolean startRandom, boolean printIndividualChoices) {
        this.payoffs = payoffs;
        this.numberOfPlays = numberOfPlays;
        this.startRandom = startRandom;
        this.printIndividualChoices = printIndividualChoices;
    }

    public void start() {
        printPayoffs(payoffs);

        final Player rose = new Player("Rose", true, payoffs, "ABC", startRandom);
        final Player colin = new Player("Colin", false, payoffs, "ABCDEF", startRandom);

        // Play n times
        for (int i = 0; i < numberOfPlays; i++) {
            // Get the chosen strategy for each player
            final int roseStrategy = rose.play(colin);
            final int colinStrategy = colin.play(rose);

            // Get the payoff for the chosen strategies
            final int payoff = payoffs[roseStrategy][colinStrategy];

            // Increase the statistics
            if (payoff > 0) {
                rose.increaseVictories();
                colin.increaseDefeats();
            } else if (payoff < 0) {
                colin.increaseVictories();
                rose.increaseDefeats();
            }

            if (printIndividualChoices) {
                // Print the played strategy
                System.out.printf(
                        "%d) Rose played %s and Colin played %s.%n",
                        i + 1, rose.getStrategyName(roseStrategy), colin.getStrategyName(colinStrategy)
                );
                // Print the payoff
                if (payoff > 0) System.out.printf("\tRose won %d, Colin lost %d.%n", payoff, payoff);
                else if (payoff == 0) System.out.println("\tIt was a draw, nobody won or lost anything.");
                else System.out.printf("\tColin won %d, Rose lost %d.%n", payoff, payoff);

                System.out.println();
            }
        }

        // Print the statistics of the game
        printStrategyStatsFor(rose);
        printStrategyStatsFor(colin);
        printGameTotals(rose, colin);
    }

    private void printGameTotals(Player... players) {
        System.out.printf("For %d plays:%n", numberOfPlays);

        int draws = numberOfPlays;
        for (Player player : players) {
            draws -= player.getVictories();
            final float percentOfVictories = (float) player.getVictories() / numberOfPlays * 100;
            final float percentOfDefeats = (float) player.getDefeats() / numberOfPlays * 100;
            // print the name of player and his stats
            System.out.printf("  %s won %d times (%.2f%%) and lost %d times (%.2f%%).%n",
                    player.getName(), player.getVictories(), percentOfVictories, player.getDefeats(), percentOfDefeats);
        }
        final float percentOfDraws = (float) draws / numberOfPlays * 100;
        System.out.printf("  Players tied %d times (%.2f%%).%n%n", draws, percentOfDraws);
    }

    private static void printStrategyStatsFor(Player player) {
        // print the name of the player
        System.out.printf("%s statistics:%n", player.getName());
        // print the header of table
        System.out.printf("Strategy - %% of time %n");
        for (int s = 0; s < player.getStrategies().length(); s++) {
            // calculate the percent of time (strategy count / total plays * 100)
            final float percentOfTime = (float) player.getStrategyCount()[s] / player.getPlayHistory().length() * 100;
            // print the value for strategy
            System.out.printf("%4s %10.0f%%%n", player.getStrategies().charAt(s), percentOfTime);
        }
        System.out.println();
    }

    private static void printPayoffs(final int[][] payoffs) {
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

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public static class Builder {
        private int[][] payoffs;
        private int numberOfPlays = 10;
        private boolean startRandom = false;
        private boolean printIndividualChoices = true;

        public Builder payoffs(int[][] payoffs) {
            this.payoffs = payoffs;
            return this;
        }

        public Builder numberOfPlays(int numberOfPlays) {
            this.numberOfPlays = numberOfPlays;
            return this;
        }

        public Builder startRandom(boolean startRandom) {
            this.startRandom = startRandom;
            return this;
        }

        public Builder printIndividualChoices(boolean printIndividualChoices) {
            this.printIndividualChoices = printIndividualChoices;
            return this;
        }

        public Game createGame() {
            return new Game(payoffs, numberOfPlays, startRandom, printIndividualChoices);
        }
    }
}