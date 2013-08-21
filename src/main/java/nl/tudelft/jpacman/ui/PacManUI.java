package nl.tudelft.jpacman.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Occupant;
import nl.tudelft.jpacman.board.Pellet;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.Sprite;

public class PacManUI extends JFrame {

	private static final int CELL_HEIGHT = 32;

	private static final int CELL_WIDTH = 32;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Map<Player, JLabel> scoreLabels;

	private JPanel boardPanel;

	/**
	 * Creates a new UI for a JPac-Man game.
	 * 
	 * @param game
	 *            The game to play.
	 * @param buttons
	 *            The map of caption-to-action entries that will appear as
	 *            buttons on the interface.
	 * @param keyMappings
	 *            The map of keyCode-to-action entries that will be added as key
	 *            listeners to the interface.
	 */
	public PacManUI(final Game game, final Map<String, Action> buttons,
			final Map<Integer, Action> keyMappings) {
		// TODO Auto-generated constructor stub

		super("JPac-Man");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				Action action = keyMappings.get(e.getKeyCode());
				if (action != null) {
					action.doAction();
				}
			}
		});

		final JFrame uiFrame = this;

		JPanel buttonPanel = new JPanel();

		for (final String caption : buttons.keySet()) {
			JButton button = new JButton(caption);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					buttons.get(caption).doAction();
					uiFrame.requestFocusInWindow();
				}
			});
			buttonPanel.add(button);
		}

		List<Player> players = game.getPlayers();
		JPanel scorePanel = new JPanel(new GridLayout(2, players.size()));
		for (int i = 1; i <= players.size(); i++) {
			scorePanel.add(new JLabel("Player " + 1, JLabel.CENTER));
		}
		scoreLabels = new LinkedHashMap<>();
		for (Player p : players) {
			JLabel scoreLabel = new JLabel("0", JLabel.CENTER);
			scoreLabels.put(p, scoreLabel);
			scorePanel.add(scoreLabel);
		}

		boardPanel = new BoardPanel(game);

		Container contentPanel = getContentPane();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		contentPanel.add(scorePanel, BorderLayout.NORTH);
		contentPanel.add(boardPanel, BorderLayout.CENTER);

		pack();
	}

	class BoardPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Game game;

		BoardPanel(Game game) {
			assert game != null;

			this.game = game;

			Level level = game.getLevel();
			Board board = level.getBoard();

			int w = board.getWidth() * CELL_WIDTH;
			int h = board.getHeight() * CELL_HEIGHT;

			Dimension initSize = new Dimension(w, h);
			setMinimumSize(initSize);
			setPreferredSize(initSize);
		}

		@Override
		public void paint(Graphics g) {
			render(game.getLevel().getBoard(), g, getSize());
		}

		private void render(Board board, Graphics g, Dimension window) {

			int cellW = window.width / board.getWidth();
			int cellH = window.height / board.getHeight();

			for (int y = 0; y < board.getHeight(); y++) {
				for (int x = 0; x < board.getWidth(); x++) {
					int cellX = x * cellW;
					int cellY = y * cellH;
					Square square = board.squareAt(x, y);

					render(square, g, cellX, cellY, cellW, cellH);
				}
			}
		}

		private void render(Square square, Graphics g, int x, int y, int w,
				int h) {

			Sprite squareSprite = square.getSprite();
			squareSprite.draw(g, x, y, w, h);

			Pellet p = square.getPellet();

			if (p != null) {
				Sprite pelletSprite = p.getSprite();
				pelletSprite.draw(g, x, y, w, h);
			}

			for (Occupant o : square.getOccupants()) {
				Sprite occupantSprite = o.getSprite();
				occupantSprite.draw(g, x, y, w, h);
			}
		}
	}

	public void start() {
		setVisible(true);

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				nextFrame();
			}
		}, 0, 40, TimeUnit.MILLISECONDS); // 25 fps

	}

	private void nextFrame() {
		boardPanel.repaint();

		refreshScores();
	}

	private void refreshScores() {
		for (Player p : scoreLabels.keySet()) {
			scoreLabels.get(p).setText(String.valueOf(p.getScore()));
		}
	}

	public void stop() {

	}

}
