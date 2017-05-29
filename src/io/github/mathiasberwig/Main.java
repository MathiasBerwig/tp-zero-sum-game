package io.github.mathiasberwig;

public class Main {

    public static void main(String... args) {
        final double[][] payoffs = {
              /*          Colin        */
              /* A   B   C   D   E   F */
/*       A */  { 4, -4,  3,  2, -3,  3},
/* Rose  B */  {-1, -1, -2,  0,  0,  4},
/*       C */  {-1,  2,  1, -1,  2, -3}
        };

        new Game.Builder()
                .payoffs(payoffs)
                .startRandom(true)
                .printIndividualChoices(false)
                .numberOfPlays(100)
                .createGame()
                .start();
    }
}