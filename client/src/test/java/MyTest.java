import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {

//	@BeforeEach
//	void resetClass() {
//		playerMoves checker = new playerMoves();
//	}

	@Test
	void horzwin() {

//		ArrayList<String> check1 = new ArrayList<>();
		playerMoves checker = new playerMoves();
		for (int i = 1; i < 5; i++) {
			String el = "(4, " + i + ")";
			checker.p1Move.add(el);
		}
		assertTrue(checker.HorzwinCheck(checker.p1Move, 4, "[(, )]+"));
	}


	@Test
	void vertwin() {
		playerMoves checker = new playerMoves();
		for (int i = 1; i < 5; i++) {
			String el = "(" + i + ", 3)";
			checker.p2Move.add(el);
		}

		assertTrue(checker.VertwinCheck(checker.p2Move, 3, "[(, )]+"));
	}

	@Test
	void diagwin() {
		playerMoves checker = new playerMoves();
		for (int i = 1; i < 5; i++) {
			String el = "(" + i + ", " + i + ")";
			checker.p1Move.add(el);
		}

		assertTrue(checker.DiagwinCheck(checker.p1Move, 1, 1));
	}

	@Test
	void wincheck1() {
		playerMoves checker = new playerMoves();
		for (int i = 1; i < 5; i++) {
			String el = "(1, " + i + ")";
			checker.p1Move.add(el);
		}
		assertTrue(checker.winCheck(checker.p1Move));
	}

	@Test
	void wincheck2() {
		playerMoves checker = new playerMoves();
		for (int i = 1; i < 5; i++) {
			String el = "(" + i + ", 7)";
			checker.p2Move.add(el);
		}

		assertTrue(checker.winCheck(checker.p2Move));
	}

	@Test
	void wincheck3() {
		playerMoves checker = new playerMoves();
		for (int i = 3; i < 7; i++) {
			String el = "(" + i + ", " + i + ")";
			checker.p1Move.add(el);
		}

		assertTrue(checker.DiagwinCheck(checker.p1Move, 3, 3));
	}

}
