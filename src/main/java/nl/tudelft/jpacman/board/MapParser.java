package nl.tudelft.jpacman.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses text representations of {@link Board}s.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class MapParser {

	/**
	 * The factory to create squares and the board.
	 */
	private final BoardFactory factory;

	/**
	 * Creates a new map parser.
	 * 
	 * @param pFactory
	 */
	public MapParser(BoardFactory boardFactory) {
		this.factory = boardFactory;
	}

	/**
	 * Parse a text representation of a {@link Board}. Each line of text is a
	 * row on the board, each character a square.
	 * 
	 * @param map
	 *            The grid of characters that make up the map. The char at
	 *            map[x][y] should correspond with the x,y coordinate of the
	 *            grid.
	 * @return The parsed board.
	 */
	public Board parseMap(char[][] map) {
		assert map != null;

		int width = map.length;
		if (width == 0) {
			throw new MapParserException(
					"Invalid source: a map must have at least 1 column.");
		}

		int height = map[0].length;
		if (height == 0) {
			throwNoRowsException();
		}

		Square[][] grid = new Square[width][height];

		List<Square> ghostSpawnPoints = new ArrayList<>();
		List<Square> playerSpawnPoints = new ArrayList<>();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				try {
					char c = map[x][y];
					grid[x][y] = parseSquare(c, playerSpawnPoints,
							ghostSpawnPoints);
				} catch (IndexOutOfBoundsException e) {
					throw new MapParserException(
							"Invalid source: a map should be rectangular.", e);
				}
			}
		}

		Board board = factory.newBoard(grid, playerSpawnPoints, ghostSpawnPoints);
		board.connectGrid();
		return board;
	}

	/**
	 * Returns a square represented by a character and may or may not add it to
	 * the list of starting positions.
	 * 
	 * @param c
	 *            The character representing the square.
	 * @param playerSpawnPoints
	 *            A mutable list of player starting positions, this square may
	 *            or may not be added to this list.
	 * @param ghostSpawnPoints
	 *            A mutable list of ghost starting positions, this square may or
	 *            may not be added to this list.
	 * @return A new square as represented by the character.
	 */
	protected Square parseSquare(char c, List<Square> playerSpawnPoints,
			List<Square> ghostSpawnPoints) {

		switch (c) {
		case '#':
			return factory.newWall();
		case ' ':
			return factory.newEmptySquare();
		case '.':
			Square s = factory.newEmptySquare();
			factory.newPellet().occupy(s);
			return s;
		case 'G':
			Square ghostSquare = factory.newEmptySquare();
			ghostSpawnPoints.add(ghostSquare);
			return ghostSquare;
		case 'P':
			Square playerSquare = factory.newEmptySquare();
			playerSpawnPoints.add(playerSquare);
			return playerSquare;
		default:
			throw new MapParserException("Invalid source: invalid character: ["
					+ c + "]");
		}
	}

	/**
	 * Parse a text representation of a {@link Board}. Each line of text is a
	 * row on the board, each character a square.
	 * 
	 * @param map
	 *            The list of rows that make up the board.
	 * @return The parsed board.
	 */
	public Board parseMap(List<String> map) {
		assert map != null;

		if (map.isEmpty()) {
			throwNoRowsException();
		}
		String firstRow = map.get(0);
		int cols = firstRow.length();

		char[][] grid = new char[cols][map.size()];
		for (int y = 0; y < map.size(); y++) {
			String row = map.get(y);
			for (int x = 0; x < row.length(); x++) {
				grid[x][y] = row.charAt(x);
			}
		}

		return parseMap(grid);
	}

	/**
	 * Throw an exception with a message clarifying that a map should have at
	 * least a single row.
	 */
	private void throwNoRowsException() {
		throw new MapParserException(
				"Invalid source: a map must have at least 1 row.");
	}

	/**
	 * Parse a text representation of a {@link Board}. Each line of text is a
	 * row on the board, each character a square.
	 * 
	 * @param in
	 *            The input stream that provides the data that is to be parsed.
	 * @return The parsed board.
	 */
	public Board parseMap(InputStream in) {
		assert in != null;

		List<String> map = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				in))) {
			while (reader.ready()) {
				map.add(reader.readLine());
			}
		} catch (IOException e) {
			throw new MapParserException("Unable to read input.", e);
		}

		return parseMap(map);
	}

	/**
	 * Exception thrown when a map source is invalid.
	 * 
	 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
	 */
	protected static class MapParserException extends RuntimeException {

		/**
		 * Generated id.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Create a new exception.
		 * 
		 * @param message
		 *            The detail message.
		 */
		protected MapParserException(String message) {
			super(message);
		}

		/**
		 * Create a new exception.
		 * 
		 * @param message
		 *            The detail message.
		 * @param cause
		 *            The cause of this exception.
		 */
		protected MapParserException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
