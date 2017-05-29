package io.github.mathiasberwig;

import java.util.Random;

public class Player {

    // Name the player
    private final String name;

    // The payoffs for this player
    private final double [][] payoffs;

    // All possible strategies
    private final String strategies;

    // How many times each strategy was played
    private int[] strategyCount;

    // The history of strategies chosen
    private String playHistory;

    // Indicates if first strategy choose is random
    private boolean startRandom;

    // Track the number of victories and defeats of this player
    private int victories, defeats;

    // Chooses the first strategy
    private static Random random;

    public Player(String name, boolean playRows, double[][] payoffs, String strategies, boolean startRandom) {
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

    public double[] calculateMeanGains(Player opponent) {
        final double[] meanGains = new double[strategies.length()];
        for (int s = 0; s < strategies.length(); s++) {
            for (int os = 0; os < opponent.strategies.length(); os++) {
                meanGains[s] += (double) payoffs[s][os] * opponent.strategyCount[os] / opponent.playHistory.length();
            }
        }
        return meanGains;
    }

    public double calculateMeanGameResult(Player opponent) {
        final double[] meanGains = calculateMeanGains(opponent);
        double meanGame = 0;
        for (int s = 0; s < strategies.length(); s++) {
            meanGame += meanGains[s] * strategyCount[s] / playHistory.length();
        }
        return meanGame;
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

    public int getVictories() {
        return victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void increaseVictories() {
        victories++;
    }

    public void increaseDefeats() {
        defeats++;
    }

    @Override
    public String toString() {
        return name;
    }
}
