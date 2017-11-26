
package mashup.game;

import java.util.Arrays;

/**
 * This program was based on:
 *
 * the Wikipedia entry for the Damerau–Levenshtein distance
 * (https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance);
 *
 * and the video "ADS1: Using dynamic programming for edit distance" by Ben
 * Langmead (https://youtu.be/eAVGRWSryGo)
 *
 * @author Elizabeth
 */
final class EditDistanceCalculator {

    int findDistance(String startText, String endText) {
        EditMatrix matrix = new EditMatrix(startText, endText);
        return matrix.finalDist;
    }

    //inner class wrapped in outer class for thread safety/cleaner usage
    private class EditMatrix {

        private final String startText;
        private final String endText;
        private int finalDist;

        private EditMatrix(String startText, String endText) {
            this.startText = startText;
            this.endText = endText;
            int[][] matrix = createMatrix();
            this.finalDist = finalDistance(matrix);
        }

        private int[][] createMatrix() {
            int[][] initializedMatrix = initializeMatrix();
            int[][] filledMatrix = fillEditMatrix(initializedMatrix);
            return filledMatrix;
        }

        private int[][] initializeMatrix() {
            int rows = startText.length() + 1;
            int columns = endText.length() + 1;
            int[][] matrix = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                matrix[i][0] = i;
            }
            for (int j = 0; j < columns; j++) {
                matrix[0][j] = j;
            }
            return matrix;
        }

        private int[][] fillEditMatrix(int[][] matrix) {
            for (int i = 1; i < matrix.length; i++) {
                for (int j = 1; j < matrix[i].length; j++) {
                    matrix[i][j] = getDistance(matrix, i, j);
                }
            }
            return matrix;
        }

        private int getDistance(int[][] matrix, int row, int column) {
            int minDistance = minDistWithoutTranspose(matrix, row, column);
            if (canTranspose(row, column)) {
                int distIfTranspose = matrix[row - 2][column - 2];
                minDistance = minValue(minDistance, distIfTranspose + 1);
            }
            return minDistance;
        }

        private int minDistWithoutTranspose(int[][] matrix, int row, int column) {
            int distLocal = getLocalDistance(row, column);
            int distPreceding = matrix[row - 1][column - 1];
            int distIfSubtract = matrix[row - 1][column];
            int distIfAdd = matrix[row][column - 1];
            return minValue(distPreceding + distLocal, distIfSubtract + 1, distIfAdd + 1);
        }

        private boolean canTranspose(int row, int column) {
            return row > 1 && column > 1 && startText.charAt(row - 1) == endText.charAt(column - 2) && startText.charAt(row - 1) == endText.charAt(column - 2);
        }

        private int getLocalDistance(int row, int column) {
            if (startText.charAt(row - 1) == endText.charAt(column - 1)) {
                return 0;
            }
            return 1;
        }

        private int finalDistance(int[][] matrix) {
            return matrix[startText.length()][endText.length()];
        }

        private int minValue(int... values) {
            if (values == null || values.length == 0) {
                throw new IllegalStateException("invalid input in min function. you broke something!");
            }
            return Arrays.stream(values).min().getAsInt();
        }
    }
}
