package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int width;
    private final int height;
    private final List<Move> zeroes = new ArrayList<>();
    private final List<Move> ones = new ArrayList<>();
    private boolean nextMoveZero = true;

    public Board(int width, int height) {
        if (width <= 2 || height <= 2) {
            throw new IllegalArgumentException("Both dimensions must be more than 2");
        }
        this.width = width;
        this.height = height;
    }

    public boolean nextMove(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            throw new IllegalArgumentException("Move must be within the board");
        }

        Move m = new Move(x, y);
        if (zeroes.contains(m) || ones.contains(m)) {
            throw new IllegalArgumentException("Square is not free");
        }

        if (isGameOver()) {
            throw new IllegalStateException("Game is over");
        }

        if (nextMoveZero) {
            zeroes.add(m);
        } else {
            ones.add(m);
        }

        nextMoveZero = !nextMoveZero;

        return isGameOver(nextMoveZero ? ones : zeroes);
    }

    public boolean isGameOver() {
        return isGameOver(zeroes) || isGameOver(ones);
    }

    public boolean isZeroWinner() {
        return isGameOver(zeroes);
    }

    public boolean isOneWinner() {
        return isGameOver(ones);
    }

    private boolean isGameOver(List<Move> moves) {
        for (Move move : moves) {
            // Imagine as if the square is in the middle of the board
            // Even if the square is on the edge - at least one of the neighbours will be missing
            if (
                    // 001
                    // 010
                    // 100
                    moves.contains(new Move(move.getX() - 1, move.getY() - 1))
                    && moves.contains(new Move(move.getX() + 1, move.getY() + 1))
                    // 000
                    // 111
                    // 000
                    || moves.contains(new Move(move.getX() - 1, move.getY()))
                    && moves.contains(new Move(move.getX() + 1, move.getY()))
                    // 100
                    // 010
                    // 001
                    || moves.contains(new Move(move.getX() - 1, move.getY() + 1))
                    && moves.contains(new Move(move.getX() + 1, move.getY() - 1))
                    // 010
                    // 010
                    // 010
                    || moves.contains(new Move(move.getX(), move.getY() - 1))
                    && moves.contains(new Move(move.getX(), move.getY() + 1))) {
                return true;
            }
        }
        return false;
    }
}
