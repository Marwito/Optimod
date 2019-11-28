package test.modelTest;

import static org.junit.Assert.*;

import java.lang.reflect.*;
import java.util.ArrayList;

import org.junit.Test;

import model.computer.Computer;
import model.computer.tsp.TSP;
import model.computer.tsp.TSP5;
/**
 * Test de la class TSP5
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class TestTsp5 {

	@Test
	/**
	 * test of the cost method, test if the cost returned is correct
	 */
	public void costTest() {
		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 5, 1, 10, infinite }, { 4, infinite, 3, infinite, 8 },
				{ 4, infinite, infinite, 8, infinite }, { infinite, infinite, 2, infinite, 3 },
				{ infinite, infinite, 1, 4, infinite } };
		int durations[] = { 2, 7, 4, 0, 0 };
		int biginWindow[] = { 10, 10, 10, 10, 10 };
		int endWindow[] = { 20, 20, 20, 20, 20 };

		try {
			// to acces to the protected methode coutSuivant;
			@SuppressWarnings("rawtypes")
			Class[] classes = { Integer.class, Integer.class, int.class, int[][].class, int[].class, int[].class,
					int[].class };
			Method method;
			method = TSP5.class.getDeclaredMethod("coutSuivant", classes);
			method.setAccessible(true);

			// if the time of arrival and the time of departure is in the time
			// window
			Object[] args1 = { 1, 2, 9, costs, durations, biginWindow, endWindow };
			int result = (int) method.invoke(tsp, args1);
			assertEquals(result, 16);

			// if the time of arrival is not in the time window
			Object[] args2 = { 1, 2, 6, costs, durations, biginWindow, endWindow };
			result = (int) method.invoke(tsp, args2);
			assertEquals(result, 14);

			// if the time of departure is at the limit of the time window
			Object[] args3 = { 1, 2, 13, costs, durations, biginWindow, endWindow };
			result = (int) method.invoke(tsp, args3);
			assertEquals(result, 20);

			// if the time of departure is not in the time window
			Object[] args4 = { 1, 2, 16, costs, durations, biginWindow, endWindow };
			result = (int) method.invoke(tsp, args4);
			assertEquals(result, Integer.MAX_VALUE);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * second test of the cost method, test if the cost returned is correct 
	 */
	public void costTest2() {
		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 5, 1, 10, infinite }, { 4, infinite, 3, infinite, 8 },
				{ 4, infinite, infinite, 8, infinite }, { infinite, infinite, 2, infinite, 3 },
				{ infinite, infinite, 1, 4, infinite } };
		int durations[] = { 0, 0, 0, 0, 0 };
		int biginWindow[] = { 0, 0, 0, 0, 0 };
		int endWindow[] = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MAX_VALUE };

		try {
			// to acces to the protected methode coutSuivant;
			@SuppressWarnings("rawtypes")
			Class[] classes = { Integer.class, Integer.class, int.class, int[][].class, int[].class, int[].class,
					int[].class };
			Method method;
			method = TSP5.class.getDeclaredMethod("coutSuivant", classes);
			method.setAccessible(true);

			Object[] args1 = { 3, 4, 10, costs, durations, biginWindow, endWindow };
			int result = (int) method.invoke(tsp, args1);
			assertEquals(result, 13);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * test the Bound method, test if the bound estimation is corect.
	 */
	public void boundTest() {
		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 5, 1, 10, infinite }, { 4, infinite, 3, infinite, 8 },
				{ 4, infinite, infinite, 8, infinite }, { infinite, infinite, 2, infinite, 3 },
				{ infinite, infinite, 1, 4, infinite } };
		int durations[] = { 2, 7, 4, 0, 0 };

		try {
			// to acces to the protected methode coutSuivant;
			@SuppressWarnings("rawtypes")
			Class[] classes = { Integer.class, ArrayList.class, int[][].class, int[].class };
			Method method;
			method = TSP5.class.getDeclaredMethod("bound", classes);
			method.setAccessible(true);
			ArrayList<Integer> aVoir = new ArrayList<>();
			aVoir.add(2);
			aVoir.add(3);
			aVoir.add(4);
			Object[] args1 = { 1, aVoir, costs, durations };
			double result = (double) method.invoke(tsp, args1);
			assertEquals(result, 11.5, 0.00001);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * second test of the bound methode, test if the bound estimation is corect.
	 */
	public void boundTest2() {
		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 9, 5, 1, 5, 6, 9, 3, 9, 2, 5 }, { 4, infinite, 2, 2, 5, 9, 9, 9, 6, 9, 4 },
				{ 5, 1, infinite, 4, 9, 3, 2, 7, 2, 9, 5 }, { 3, 7, 6, infinite, 9, 5, 7, 2, 4, 2, 8 },
				{ 9, 6, 5, 2, infinite, 9, 6, 4, 3, 2, 9 }, { 9, 7, 8, 1, 9, infinite, 1, 7, 4, 8, 6 },
				{ 1, 7, 4, 8, 6, 8, infinite, 3, 9, 7, 5 }, { 9, 5, 7, 2, 4, 2, 8, infinite, 2, 5, 7 },
				{ 1, 3, 5, 6, 8, 4, 2, 4, infinite, 5, 7 }, { 5, 4, 6, 8, 2, 3, 7, 9, 9, infinite, 2 },
				{ 8, 7, 3, 6, 4, 7, 8, 6, 3, 7, infinite } };
		int durations[] = { 0, 2, 3, 1, 5, 6, 4, 8, 4, 3, 2 };
		try {
			// to acces to the protected methode coutSuivant;
			@SuppressWarnings("rawtypes")
			Class[] classes = { Integer.class, ArrayList.class, int[][].class, int[].class };
			Method method;
			method = TSP5.class.getDeclaredMethod("bound", classes);
			method.setAccessible(true);
			ArrayList<Integer> aVoir = new ArrayList<>();
			aVoir.add(2);
			aVoir.add(4);
			aVoir.add(5);
			aVoir.add(7);
			aVoir.add(10);
			Object[] args1 = { 1, aVoir, costs, durations };
			double result = (double) method.invoke(tsp, args1);
			assertEquals(33.5, result, 0.00001);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}

	@Test
	/**
	 * Test Of the ChercheSolutionTest, test if the methode compiut a delivry wiht 
	 * the time window.
	 */
	public void ChercheSolutionTest() {
		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 5, 1, 10, infinite }, { 4, infinite, 3, infinite, 8 },
				{ 4, infinite, infinite, 8, infinite }, { infinite, infinite, 2, infinite, 3 },
				{ infinite, infinite, 1, 4, infinite } };
		int durations[] = { 0, 0, 0, 0, 0 };
		int biginWindow[] = { 0, 0, 60, 40, 20 };
		int endWindow[] = { Integer.MAX_VALUE, 20, 80, 60, 40 };

		tsp.chercheSolution(10000, durations.length, costs, durations, biginWindow, endWindow);
		assertEquals(64, tsp.getCoutMeilleureSolution());
		assertEquals((int) tsp.getMeilleureSolution(0), 0);
		assertEquals((int) tsp.getMeilleureSolution(1), 1);
		assertEquals((int) tsp.getMeilleureSolution(2), 4);
		assertEquals((int) tsp.getMeilleureSolution(3), 3);
		assertEquals((int) tsp.getMeilleureSolution(4), 2);
	}

	@Test
	/**
	 * second test for the ChercheSolutionTest metohdes, test if the methode compiut a delivry without
	 * the time window. 
	 */
	public void ChercheSolutionTest2() {
		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 9, 5, 1 }, { 4, infinite, 2, 2 }, { 5, 1, infinite, 4 }, { 3, 7, 6, infinite } };
		int durations[] = { 0, 2, 3, 1 };
		int biginWindow[] = { 0, 0, 0, 0, 0 };
		int endWindow[] = { infinite, 30, 30, 30, 30 };
		tsp.chercheSolution(10000, durations.length, costs, durations, biginWindow, endWindow);

		assertEquals(17, tsp.getCoutMeilleureSolution());
		assertEquals(0, (int) tsp.getMeilleureSolution(0));
		assertEquals(2, (int) tsp.getMeilleureSolution(1));
		assertEquals(1, (int) tsp.getMeilleureSolution(2));
		assertEquals(3, (int) tsp.getMeilleureSolution(3));
	}

	@Test
	/**
	 * third test of ChercheSolution, test if the methode compiut a delivry with time windos
	 */
	public void ChercheSolutionTest3() {

		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { { infinite, 9, 5, 1 }, { 4, infinite, 2, 2 }, { 5, 1, infinite, 4 }, { 3, 7, 6, infinite } };
		int durations[] = { 0, 2, 3, 1 };
		int biginWindow[] = { 0, 10, 0, 0, 0 };
		int endWindow[] = { infinite, 30, 30, 30, 30 };
		tsp.chercheSolution(10000, durations.length, costs, durations, biginWindow, endWindow);

		assertEquals(18, tsp.getCoutMeilleureSolution());
		assertEquals(0, (int) tsp.getMeilleureSolution(0));
		assertEquals(3, (int) tsp.getMeilleureSolution(1));
		assertEquals(2, (int) tsp.getMeilleureSolution(2));
		assertEquals(1, (int) tsp.getMeilleureSolution(3));

	}

	@Test
	/**
	 * Performance test of ChercheSolution with time window
	 */
	public void ChercheSolutionPerformanceTest() {


		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;

		int costs[][] = { 	{ infinite, 9, 5, 1, 5, 6, 9, 3, 9, 2, 5 },
							{ 4, infinite, 2, 2, 5, 9, 9, 9, 6, 9, 4 },
							{ 5, 1, infinite, 4, 9, 3, 2, 7, 2, 9, 5 }, 
							{ 3, 7, 6, infinite, 9, 5, 7, 2, 4, 2, 8 },
							{ 9, 6, 5, 2, infinite, 9, 6, 4, 3, 2, 9 },
							{ 9, 7, 8, 1, 9, infinite, 1, 7, 4, 8, 6 },
							{ 1, 7, 4, 8, 6, 8,infinite , 3, 9, 7, 5 },
							{ 9, 5, 7, 2, 4, 2, 8,infinite , 2, 5, 7 },
							{ 1, 3, 5, 6, 8, 4, 2, 4, infinite, 5, 7 },
							{ 5, 4, 6, 8, 2, 3, 7, 9, 9, infinite, 2 },
							{ 8, 7, 3, 6, 4, 7, 8, 6, 3, 7, infinite }};
		int durations[] =  { 0, 2, 3, 1, 5, 6, 4, 8, 4, 3, 2};
		int biginWindow[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int endWindow[] = { infinite, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
		tsp.chercheSolution(10000, durations.length, costs, durations,biginWindow,endWindow);
		System.out.println("nombres de recurtions de BAndB :"+tsp.getNbappel());
		assertEquals(62 ,tsp.getCoutMeilleureSolution());
		
	
	}

	@Test
	/**
	 * Performance test of ChercheSolution with time windos
	 */
	public void ChercheSolutionPerformanceTest2() {

		TSP tsp = new TSP5();
		int infinite = Computer.Infinite;
		int costs[][] = { 	{ infinite, 9, 5, 1, 5, 6, 9, 3, 9, 2, 5 },
							{ 4, infinite, 2, 2, 5, 9, 9, 9, 6, 9, 4 },
							{ 5, 1, infinite, 4, 9, 3, 2, 7, 2, 9, 5 }, 
							{ 3, 7, 6, infinite, 9, 5, 7, 2, 4, 2, 8 },
							{ 9, 6, 5, 2, infinite, 9, 6, 4, 3, 2, 9 },
							{ 9, 7, 8, 1, 9, infinite, 1, 7, 4, 8, 6 },
							{ 1, 7, 4, 8, 6, 8,infinite , 3, 9, 7, 5 },
							{ 9, 5, 7, 2, 4, 2, 8,infinite , 2, 5, 7 },
							{ 1, 3, 5, 6, 8, 4, 2, 4, infinite, 5, 7 },
							{ 5, 4, 6, 8, 2, 3, 7, 9, 9, infinite, 2 },
							{ 8, 7, 3, 6, 4, 7, 8, 6, 3, 7, infinite }};
		int durations[] =  { 0, 2, 3, 1, 5, 6, 4, 8, 4, 3, 2};
		int biginWindow[] = { 0, 0, 0, 0, 0, 50, 50, 50, 50, 0, 0};
		int endWindow[] = { infinite, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
		tsp.chercheSolution(10000, durations.length, costs, durations,biginWindow,endWindow);
		
		
		assertEquals(79, tsp.getCoutMeilleureSolution());

	}
}
