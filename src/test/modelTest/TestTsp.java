package test.modelTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.computer.Computer;
import model.computer.tsp.TSP;
import model.computer.tsp.TSP1;
/**
 * Test de la class TSP
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class TestTsp {

	@Test
	/**
	 * This is a test to get to know the functionality of the provided TSP Class
	 * better.
	 */
	public void firstTest() throws Exception {
		TSP tsp = new TSP1();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 5, 1, 10, infinite }, { 4, infinite, 3, infinite, 8 },
				{ 4, infinite, infinite, 8, infinite }, { infinite, infinite, 2, infinite, 3 },
				{ infinite, infinite, 1, 4, infinite } };
		int durations[] = { 0, 0, 0, 0, 0 };
		int biginWindow[] = { 0, 0, 0, 0, 0 };
		int endWindow[] = { 200000, 200000, 200000, 200000, 200000 };

		tsp.chercheSolution(10000, durations.length, costs, durations, biginWindow, endWindow);
		assertEquals(tsp.getCoutMeilleureSolution(), 23);
		assertEquals((int) tsp.getMeilleureSolution(0), 0);
		assertEquals((int) tsp.getMeilleureSolution(1), 1);
		assertEquals((int) tsp.getMeilleureSolution(2), 4);
		assertEquals((int) tsp.getMeilleureSolution(3), 3);
		assertEquals((int) tsp.getMeilleureSolution(4), 2);

	}

	@Test
	/**
	 * Performance test of ChercheSolution with time windos
	 */
	public void ChercheSolutionPerformanceTest() {

		TSP tsp = new TSP1();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 9, 5, 1, 5, 6, 9, 3, 9, 2, 5 }, { 4, infinite, 2, 2, 5, 9, 9, 9, 6, 9, 4 },
				{ 5, 1, infinite, 4, 9, 3, 2, 7, 2, 9, 5 }, { 3, 7, 6, infinite, 9, 5, 7, 2, 4, 2, 8 },
				{ 9, 6, 5, 2, infinite, 9, 6, 4, 3, 2, 9 }, { 9, 7, 8, 1, 9, infinite, 1, 7, 4, 8, 6 },
				{ 1, 7, 4, 8, 6, 8, infinite, 3, 9, 7, 5 }, { 9, 5, 7, 2, 4, 2, 8, infinite, 2, 5, 7 },
				{ 1, 3, 5, 6, 8, 4, 2, 4, infinite, 5, 7 }, { 5, 4, 6, 8, 2, 3, 7, 9, 9, infinite, 2 },
				{ 8, 7, 3, 6, 4, 7, 8, 6, 3, 7, infinite } };
		int durations[] = { 0, 2, 3, 1, 5, 6, 4, 8, 4, 3, 2 };
		int biginWindow[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int endWindow[] = { infinite, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200 };
		tsp.chercheSolution(10000, durations.length, costs, durations, biginWindow, endWindow);

		assertEquals(62, tsp.getCoutMeilleureSolution());
		assertEquals(0, (int) tsp.getMeilleureSolution(0));
		assertEquals(9, (int) tsp.getMeilleureSolution(1));
		assertEquals(10, (int) tsp.getMeilleureSolution(2));
		assertEquals(4, (int) tsp.getMeilleureSolution(3));
		assertEquals(3, (int) tsp.getMeilleureSolution(4));
		assertEquals(7, (int) tsp.getMeilleureSolution(5));
		assertEquals(8, (int) tsp.getMeilleureSolution(6));
		assertEquals(1, (int) tsp.getMeilleureSolution(7));
		assertEquals(2, (int) tsp.getMeilleureSolution(8));
		assertEquals(5, (int) tsp.getMeilleureSolution(9));
		assertEquals(6, (int) tsp.getMeilleureSolution(10));

	}

}