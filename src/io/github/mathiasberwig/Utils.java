package io.github.mathiasberwig;

public class Utils {

    /**
     * Transposes the matrix and inverts the signal of each number.
     * @param matrix input matrix.
     * @return transposed with signal-inverted values matrix.
     */
    public static int[][] trasposeAndInvertMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] trasposedMatrix = new int[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                trasposedMatrix[x][y] = matrix[y][x] * -1;
            }
        }

        return trasposedMatrix;
    }

    /**
     * Count how many times the {@code character} appears in the {@code string}.
     */
    public static int countOccurrences(String string, char character) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }
}
