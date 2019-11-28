package test.modelTest;



import org.junit.Test;

import model.computer.tsp.IteratorArraySorted;
import model.computer.tsp.SommetCout;

/**
 * test de la classe IteratorArraySorted.
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class TestIteratorArraySorted {
	
	
	@Test
	/**
	 * Test of the constructeur of the method next. test if the iteratore give the 
	 * SommetCout in derising order. 
	 */
	public void TestIterator(){
		SommetCout [] sommetCouts = new SommetCout[4]; 
		sommetCouts[0] = new SommetCout(2,2); 
		sommetCouts[1] = new SommetCout(3,3); 
		sommetCouts[2] = new SommetCout(0,0); 
		sommetCouts[3] = new SommetCout(1,1); 
		
		System.out.println(sommetCouts[0].compareTo(sommetCouts[0]));
		System.out.println(sommetCouts[0].compareTo(sommetCouts[1]));
		System.out.println(sommetCouts[0].compareTo(sommetCouts[2]));
		System.out.println(sommetCouts[0].compareTo(sommetCouts[3]));
		
		IteratorArraySorted it  = new IteratorArraySorted(sommetCouts);
		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.next());
	}

}
