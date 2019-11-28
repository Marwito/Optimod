package test.modelTest;

import static org.junit.Assert.*;

import org.junit.Test;

import model.graph.Node;
import model.graph.NodeI;

/**
 * 
 * @author Arthur
 * @see model.graph.Node Tests for the Node class
 */
public class TestNode {

	@Test
	/**
	 * Testing if getX() returns the right value x value of a Node.
	 */
	public void getXGood() {
		NodeI node = new Node(1, 2, 3);
		assertEquals(node.getX(), 2);
	}

	@Test
	/**
	 * Testing if the x-value and the given value are not the same,
	 * then this function returns true.
	 */
	public void getXBad() {
		NodeI node = new Node(1, 2, 3);
		assertNotEquals(node.getX(), 1);
	}

	@Test
	/**
	 * Testing if getY() returns the right value.
	 */
	public void getYGood() {
		NodeI node = new Node(1, 2, 3);
		assertEquals(node.getY(), 3);
	}

	@Test
	/**
	 * Testing if the y-value and the given value are not the same,
	 * then this function returns true.
	 */
	public void getYBad() {
		NodeI node = new Node(1, 2, 3);
		assertNotEquals(node.getY(), 2);
	}

	@Test
	/**
	 * Testing if getId() returns the right value.
	 */
	public void getIdGood() {
		NodeI node = new Node(1, 2, 3);
		assertEquals(node.getId(), 1);
	}

	@Test
	/**
	 * Testing if the id and the given value are not the same,
	 * then this function returns true.
	 */
	public void getIdBad() {
		NodeI node = new Node(1, 2, 3);
		assertNotEquals(node.getId(), 2);
	}

	@Test
	/**
	 * Testing that equals() return true if two nodes are equals.
	 */
	public void testEqualsOverrideGood() {
		NodeI nodeA = new Node(1, 2, 3);
		NodeI nodeB = new Node(1, 2, 3);
		assertTrue(nodeA.equals(nodeB));
	}

}
