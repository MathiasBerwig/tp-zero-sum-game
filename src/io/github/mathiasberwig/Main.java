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

        new Game(payoffs, 15, false).play();
    }
}