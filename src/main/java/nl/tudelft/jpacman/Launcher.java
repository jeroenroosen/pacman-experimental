package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.MapParser;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.SimpleGame;
import nl.tudelft.jpacman.level.BoardLevel;
import nl.tudelft.jpacman.level.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

public class Launcher {

	public static void main(String[] args) {

		final Player p = new Player(new PacManSprites().getPacmanSprites(), new PacManSprites().getPacManDeathAnimation());
		final SimpleGame game = newGame(p);

		Action revive = new Action() {
			@Override
			public void doAction() {
				Square squareAt = p.getSquare().getSquareAt(Direction.WEST);
				p.setAlive(true);
				p.occupy(squareAt);
			}
		};
		
		Action playPauseAction = new Action() {

			@Override
			public void doAction() {
				if (game.isInProgress()) {
					game.stop();
				} else {
					game.start();
				}
			}
		};

		new PacManUiBuilder().withDefaultButtons()
				.addKey(KeyEvent.VK_SPACE, playPauseAction)
				.addKey(KeyEvent.VK_UP, new Action() {
					
					@Override
					public void doAction() {
						game.up();
					}
				})
				.addKey(KeyEvent.VK_DOWN, new Action() {
					
					@Override
					public void doAction() {
						game.down();
					}
				})
				.addKey(KeyEvent.VK_LEFT, new Action() {
					
					@Override
					public void doAction() {
						game.left();
					}
				})
				.addKey(KeyEvent.VK_RIGHT, new Action() {
					
					@Override
					public void doAction() {
						game.right();
					}
				})
				.addButton("Revive", revive).build(game).start();
	}

	private static SimpleGame newGame(Player p) {
		BoardFactory boardFactory = new DefaultBoardFactory(new PacManSprites());
		Board board = new MapParser(boardFactory).parseMap(Launcher.class.getResourceAsStream("/board.txt"));
		Level theLevel = new BoardLevel(board);
		return new SimpleGame(p, theLevel);
	}
}
