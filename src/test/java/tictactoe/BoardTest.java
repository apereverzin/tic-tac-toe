package tictactoe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BoardTest {

    Board board;

    static Stream<Arguments> wrongDimensionsProvider() {
        return Stream.of(
                arguments(-1, 3),
                arguments(2, 3),
                arguments(3, -1),
                arguments(3, 0)
        );
    }

    static Stream<Arguments> wrongMovesProvider() {
        return Stream.of(
                arguments(-1, 1),
                arguments(3, 1),
                arguments(1, -1),
                arguments(1, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("wrongDimensionsProvider")
    void shouldThrowIfWrongDimension(int width, int height) {
        try {
            new Board(width, height);
            fail("Shold throw exception");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("Both dimensions must be more than 2");
        }
    }

    @ParameterizedTest
    @MethodSource("wrongMovesProvider")
    void shouldThrowIfWrongMove(int x, int y) {
        try {
            Board board = new Board(3, 3);
            board.nextMove(x, y);
            fail("Shold throw exception");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("Move must be within the board");
        }
    }

    @Test
    void shouldThrowIfSquareNotFree() {
        try {
            Board board = new Board(3, 3);
            board.nextMove(0, 0);
            board.nextMove(0, 0);
            fail("Shold throw exception");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("Square is not free");
        }
    }

    @Test
    void shouldFinishTheGame() {
        Board board = new Board(3, 3);

        assertThat(board.isGameOver()).isFalse();
        assertThat(board.isZeroWinner()).isFalse();
        assertThat(board.isOneWinner()).isFalse();

        assertThat(board.nextMove(1, 1)).isFalse();
        assertThat(board.isGameOver()).isFalse();
        assertThat(board.isZeroWinner()).isFalse();
        assertThat(board.isOneWinner()).isFalse();

        assertThat(board.nextMove(0, 0)).isFalse();
        assertThat(board.isGameOver()).isFalse();
        assertThat(board.isZeroWinner()).isFalse();
        assertThat(board.isOneWinner()).isFalse();

        assertThat(board.nextMove(0, 1)).isFalse();
        assertThat(board.isGameOver()).isFalse();
        assertThat(board.isZeroWinner()).isFalse();
        assertThat(board.isOneWinner()).isFalse();

        assertThat(board.nextMove(2, 2)).isFalse();
        assertThat(board.isGameOver()).isFalse();
        assertThat(board.isZeroWinner()).isFalse();
        assertThat(board.isOneWinner()).isFalse();

        assertThat(board.nextMove(2, 1)).isTrue();
        assertThat(board.isGameOver()).isTrue();
        assertThat(board.isZeroWinner()).isTrue();
        assertThat(board.isOneWinner()).isFalse();
    }

    @Test
    void shouldThrowIfGameIsOver() {
        Board board = new Board(3, 3);
        board.nextMove(1, 1);
        board.nextMove(0, 0);
        board.nextMove(0, 1);
        board.nextMove(2, 2);
        board.nextMove(2, 1);
        try {
            board.nextMove(1, 2);
        } catch (IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("Game is over");
        }
    }
}
