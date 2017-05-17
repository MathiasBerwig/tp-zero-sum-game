package io.github.mathiasberwig;

import java.util.Random;

public class Player {

    // Name the player
    private final String name;

    // The payoffs for this player
    private final int [][] payoffs;

    // All possible strategies
    private final String strategies;

    // How many times each strategy was played
    private int[] strategyCount;

    // The history of strategies chosen
    private String playHistory;

    // Indicates if first strategy choose is random
    private boolean startRandom;

    // Chooses the first strategy
    private static Random random;

    public Player(String name, boolean playRows, int[][] payoffs, String strategies, boolean startRandom) {
        this.name = name;
        this.payoffs = playRows ? payoffs : Utils.trasposeAndChangeSignMatrix(payoffs);
        this.strategies = strategies;
        this.strategyCount = new int[strategies.length()];
        this.playHistory = "";
        this.startRandom = startRandom;
        if (startRandom) random = new Random();
    }

    public char getStrategyName(int index) {
        return strategies.charAt(index);
    }

    public int play(Player opponent) {
        // Play first strategy at the begin of the game
        if (playHistory.isEmpty()) {
            int firstStrategy = startRandom ? random.nextInt(strategies.length()) : 0;
            playHistory = String.valueOf(strategies.charAt(firstStrategy));
            strategyCount[firstStrategy]++;
            return firstStrategy;
        }

        // Calculate the gain of each strategy if played
        final double[] strategiesGain = new double[strategies.length()];
        for (int i = 0; i < strategies.length(); i++) {
            for (int j = 0; j < opponent.strategies.length(); j++) {

                // If opponent has not played that strategy, we don't consider it
                if (opponent.strategyCount[j] == 0) {
                    continue;
                }

                // Gain for row is: payoff * how many times opponent played that strategy
                strategiesGain[i] += payoffs[i][j] * opponent.strategyCount[j];
            }
        }

        // Get the index of strategy with the biggest gain
        final int chosenStrategy = chooseBestGain(strategiesGain);

        // Add chosen strategy to statistics
        playHistory = playHistory.concat(String.valueOf(strategies.charAt(chosenStrategy)));
        strategyCount[chosenStrategy]++;

        return chosenStrategy;
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

    public String getName() {
        return name;
    }

    public String getStrategies() {
        return strategies;
    }

    public int[] getStrategyCount() {
        return strategyCount;
    }

    public String getPlayHistory() {
        return playHistory;
    }

    @Override
    public String toString() {
        return name;
    }
}
