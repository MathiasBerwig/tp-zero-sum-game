package io.github.mathiasberwig;

public class Utils {

    /**
     * Transposes the matrix and inverts the signal of each number.
     * @param matrix input matrix.
     * @return transposed with signal-inverted values matrix.
     */
    public static double[][] trasposeAndChangeSignMatrix(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        double[][] trasposedMatrix = new double[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                trasposedMatrix[x][y] = matrix[y][x] * -1;
            }
        }

        return trasposedMatrix;
    }
}
