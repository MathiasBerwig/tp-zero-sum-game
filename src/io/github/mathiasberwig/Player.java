package io.github.mathiasberwig;

import java.util.Random;

public class Player {

    private final String name;
    private final boolean playRows;
    private final String strategies;
    private String playHistory;
    private boolean startRandom;

    private static Random random;

    public Player(String name, boolean playRows, String strategies, boolean startRandom) {
        this.name = name;
        this.playRows = playRows;
        this.strategies = strategies;
        this.playHistory = "";
        this.startRandom = startRandom;
        if (startRandom) random = new Random();
    }

    public char getStrategyName(int index) {
        return strategies.charAt(index);
    }

    public int play(int[][] payoffs, Player opponent) {

        // Play first strategy at the begin of the game
        if (playHistory.isEmpty()) {
            int firstStrategy = startRandom ? random.nextInt(strategies.length()) : 0;
            playHistory = String.valueOf(strategies.charAt(firstStrategy));
            return firstStrategy;
        }

        // Transpose and change sign of values of the matrix if user play columns
        final int[][] mPayoffs = playRows ? payoffs : Utils.trasposeAndChangeSignMatrix(payoffs);

        // Count how many times the opponent has played each of his strategies
        final int[] strategyCount = opponent.countStrategiesPlayed();

        // Calculate the gain of each strategy if played
        final double[] strategiesGain = new double[strategies.length()];
        for (int i = 0; i < strategies.length(); i++) {
            for (int j = 0; j < opponent.strategies.length(); j++) {

                // If opponent has not played that strategy, we don't consider it
                if (strategyCount[j] == 0) {
                    continue;
                }

                // Gain for row is: payoff * how many times opponent played that strategy
                strategiesGain[i] += mPayoffs[i][j] * strategyCount[j];
            }
        }

        // Get the index of strategy with the biggest gain
        final int chosenStrategy = chooseBestGain(strategiesGain);

        // Add chosen strategy to plays history
        playHistory = playHistory.concat(String.valueOf(strategies.charAt(chosenStrategy)));

        return chosenStrategy;
    }

    /**
     * @return the number of times this player has played each of his {@link #strategies}. The resultant array has the
     * same ordering than the strategies array.
     */
    private int[] countStrategiesPlayed() {
        final int[] result = new int[strategies.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Utils.countOccurrences(playHistory, strategies.charAt(i));
        }
        return result;
    }

    private int chooseBestGain(double[] gains) {
        Double best = null;
        int idxOfBest = -1;
        for (int i = 0; i < gains.length; i++) {
            if (best == null || gains[i] > best) {
                idxOfBest = i;
                best = gains[i];
            }
        }
        return idxOfBest;
    }

    @Override
    public String toString() {
        return name;
    }
}
