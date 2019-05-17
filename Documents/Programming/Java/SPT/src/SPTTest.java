import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

class SPTTest {

	@BeforeAll
	static void setUp() {
		
	}
	/** Creates and populates an edge array with random weights between 0 and 40.
	 * The element in the ith row and column will be -1, staying true to the formatting.
	 * 
	 * @param size is the given length and height of the array
	 * @return the newly populated array
	 */
	private int[][] populateEdges(int size) {
		java.util.Random rand = new java.util.Random();
		
		int[][] edges = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				edges[i][j] = rand.nextInt(41);
				
				//weight of 0 means no edge
				if(edges[i][j] == 0) {
					edges[i][j] = -1;
				}
				
				//no loops
				if(i == j) {
					edges[i][j] = -1;
				}
			}
		}
		return edges;
	}
	
	/** Calculates the the total weight of the Shortest Path Tree: or the minimum weight to visit all the nodes. */
	private int calculateMinLength(int[][] edges, int source) {
		int length = 0;
		int[] answer;
		try {
			answer = SPT.findSPT(edges, source);
			for(int i = 0; i < answer.length; i++) {
				int connectedNode = answer[i];
				if(connectedNode != -1) {
					length += edges[connectedNode][i];
				}
			}
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have");
		}
		return length;
	}
	
	/** Tests whether the algorithm gives the shortest path. */
	@Test
	void testUndirectedGraphs() {
		int source = 0;
		
		int[][] nineEdges = {{-1, 4, -1, -1, -1, -1, -1, 8, -1},
							{4, -1, 8, -1, -1, -1, -1, 11, -1},
							{-1, 8, -1, 7, -1, 4, -1, -1, 2}, 
							{-1, -1, 7, -1, 9, 14, -1, -1, -1}, 
							{-1, -1, -1, 9, -1, 10, -1, -1, -1}, 
							{-1, -1, 4, 14, 10, -1, 2, -1, -1},
							{-1, -1, -1, -1, -1, 2, -1, 1, 6},
							{8, 11, -1, -1, -1, -1, 1, -1, 7},
							{-1, -1, 2, -1, -1, -1, 6, 7, -1}};		
		try {
			int[] actualAnswer = SPT.findSPT(nineEdges, source);
			int[] expectedAnswer = {-1, 0, 1, 2, 5, 6, 7, 0, 2};
			assertArrayEquals("The method doesn't give the right answer", expectedAnswer, actualAnswer);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have");
		}
		
		source = 2;

		int[][] sixEdges = 	{{-1, 7, 9, -1, -1, 14}, 
							{7, -1, 10, 15, -1, -1},
							{9, 10, -1, 11, -1, 2},
							{-1, 15, 11, -1, 6, -1},
							{-1, -1, -1, 6, -1, 9},
							{14, -1, 2, -1, 9, -1}};
		
		//Call the respective test exception methods?
		try {
			int[] actualAnswer = SPT.findSPT(sixEdges, source);
			int[] expectedAnswer = {2, 2, -1, 2, 5, 2};
			assertArrayEquals("The method doesn't give the right answer", expectedAnswer, actualAnswer);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have");
		}
	} 
	
	@Test 
	void testDirectedGraphs() {
		//directed edges 
		int source = 0;
		int[][] directedEdges_1 = {{-1, 8, 4, 11, -1, -1, -1},
									{-1, -1, -1, 1, 3, -1, -1},
									{-1, -1, -1, 9, -1, 6, -1},
									{-1, -1, -1, -1, 4, 4, 17},
									{-1, -1, -1, -1, -1, -1, 13},
									{-1, -1, -1, -1, -1, -1, 15},
									{-1, -1, -1, -1, -1, -1, -1}};
		try {
			int[] actualAnswer = SPT.findSPT(directedEdges_1, source);
			int[] expectedAnswer = {-1, 0, 0, 1, 1, 2, 4};
			assertArrayEquals("The method doesn't give the right answer", expectedAnswer, actualAnswer);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have : " + e.getMessage());
		}
		
		source = 2;
		int[][] directedEdges_2 = {{-1, -1, 2, -1, -1, -1},
								   {5, -1, -1, 4, -1, -1},
								   {-1, 8, -1, -1, 7, -1},
								   {-1, -1, -1, -1, 6, 3},
								   {-1, -1, -1, -1, -1, 1},
								   {-1, -1, -1, -1, -1, -1}};
		try {
			int[] actualAnswer = SPT.findSPT(directedEdges_2, source);
			int[] expectedAnswer = {1, 2, -1, 1, 2, 4};
			assertArrayEquals("The method doesn't give the right answer", expectedAnswer, actualAnswer);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have : " + e.getMessage());
		}
		
	}
	
	/** Tests whether an NPE is thrown when the source is null (can be assumed that an integer parameter is given and auto-unboxed) */
	@Test
	void nullSource() {
		int[][] edges = {{-1, 5, 4}, {5, -1, 3}, {4, 3, -1}};
		Integer source = null;
		
		assertThrows(NullPointerException.class, () -> SPT.findSPT(edges, source));
	} 
	
	/** Tests whether an NPE is thrown when the edges are null */
	@Test
	void nullEdgesTest() {
		int[][] edges = null; 
		int source = 0;
		
		assertThrows(NullPointerException.class, () -> SPT.findSPT(edges, source));
	} 
	
	/** Test whether the answer given by the method has the right dimensions of 1xn */
	@Test
	void wrongTreeArrayDimensionsTest() {
		java.util.Random rand = new java.util.Random();
		
		int size = rand.nextInt(100) + 5;
		
		int[][] edges = populateEdges(size);
		int source = 0;
		
		try {
			assertEquals("The Shortest Path Tree returns the wrong number of elements", SPT.findSPT(edges, source).length , size);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have");
		}
	}
	
	/** The source in the returned array is in the wrong index. */
	@Test
	void wrongSourceAnswerTest() {
		int[][] edges = {{-1, 7, 9, -1, -1, 14},
		 		{7, -1, 10, 15, -1, -1},
		 		{9, 10, -1, 11, -1, 2},
		 		{-1, 15, 11, -1, 6, -1},
		 		{-1, -1, -1, 6, -1, 9},
		 		{14, -1, 2, -1, 9, -1}};
		
		try {
			for(int i = 0; i < edges.length; i++) {
				int source = i;
				assertEquals("The source isn't represented by a -1 in the answer", SPT.findSPT(edges, source)[source], -1);
			}
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception thrown when it shouldn't have");
		}
	}
	
	/** Testing whether the edge parameter throws InvalidGraphException for arguments not of shape mxm */
	@Test
	void wrongEdgeArrayDimensionsTest() {
		
		int[][] edges_1 = {{-1, 1, 2, -1}, {1, -1, -1, 3}};
		int root = 0;
		assertThrows(InvalidGraphException.class, () -> SPT.findSPT(edges_1, root));
		
		int[][] edges_2 = {{-1, 1}, {1, -1, 4}, {2, 4}, {-1, 5, 10, -1}};
		assertThrows(InvalidGraphException.class, () -> SPT.findSPT(edges_2, root));
	}
	
	/** Test whether the algorithm works with negative edges (it shouldn't because it's Dijsktra's) */
	@Test
	void negativeEdgesTest() {
		int[][] edges = {{-1, 5, -10, -1, -1},
						 {-1, 5, -1, -1, -29},
						 {-1, -10, -1, 3, 6},
						 {-1, -1, 3, -1, -4},
						 {-1, -29, 6, -4, -1}};
		int source = 0;
		
		assertThrows(LoopDetectedException.class, () -> SPT.findSPT(edges, source));
	}
	
	/** Testing whether the method throws InvalidSourceException when the source number is smaller than 0. */
	@Test
	void sourceBelowBoundsTest() {
		int[][] edges = {{-1, 7, 9, -1, -1, 14},
				 {7, -1, 10, 15, -1, -1},
				 {9, 10, -1, 11, -1, 2},
				 {-1, 15, 11, -1, 6, -1},
				 {-1, -1, -1, 6, -1, 9},
				 {14, -1, 2, -1, 9, -1}};
		
		java.util.Random rand = new java.util.Random();
		int source = rand.nextInt(9998) - 10000;
		
		assertThrows(InvalidSourceException.class, () -> SPT.findSPT(edges, source));
	}
	
	/** Tests whether InvalidSourceException is thrown when the root is equal to the edge's size. */
	@Test
	void noEdgesTest() {
		int[][] edges = new int[0][0];
		int root = 0;
		
		assertThrows(InvalidSourceException.class, () -> SPT.findSPT(edges, root)); //what about invalid graph exception
	} 
	
	/** Testing whether the InvalidSourceException is thrown when the source number is equal to the */
	@Test
	void sourceJustAboveBoundsTest() {
		
		java.util.Random rand = new java.util.Random();
		int size = rand.nextInt(999) + 1;
		
		int[][] edges = populateEdges(size);
		int source = size;
		
		assertThrows(InvalidSourceException.class, () -> SPT.findSPT(edges, source));
	}
	
	/** Testing whether the method throws InvalidSourceException when the source number is larger than the largest index for edges. */
	@Test
	void sourceAboveBoundsTest() {
		
		int[][] edges = {{-1, 7, 9, -1, -1, 14},
				 {7, -1, 10, 15, -1, -1},
				 {9, 10, -1, 11, -1, 2},
				 {-1, 15, 11, -1, 6, -1},
				 {-1, -1, -1, 6, -1, 9},
				 {14, -1, 2, -1, 9, -1}};
		
		java.util.Random rand = new java.util.Random();
		int source = rand.nextInt(999) + edges.length + 1;
		
		assertThrows(InvalidSourceException.class, () -> SPT.findSPT(edges, source));
	}
	
	/** Tests whether LoopDetected Exception is thrown when there are loops on a subset of the input graph's vertices */
	@Test
	void loopDetectionTest() {
		int[][] edges = {{-1, 9, 7, -1, -1, -1, -1},
						{-1, -1, -1, 4, 8, -1, -1}, 
						{-1, -1, -1, 6, -1, 5, -1}, 
						{-1, -1, -1, 1, -1, -1, 7}, 
						{-1, -1, -1, -1, 2, -1, 11}, 
						{-1, -1, -1, -1, -1, -1, 15},
						{-1, -1, -1, -1, -1, -1, 8}};
		int source = 0;
		
		assertThrows(LoopDetectedException.class, () -> SPT.findSPT(edges, source));
	}
	
	/**Tests whether the method throws an exception if the input graph is unconnected : there's no path between the root and a node. */
	@Test
	void unconnectedGraphTest() {
		int[][] edges_1 = {{-1, 3, -1, 8, -1, -1},
						 {3, -1, 5, -1, -1, -1},
						 {-1, 5, -1, 6, -1, -1},
						 {8, -1, 6, -1, -1, -1},
						 {-1, -1, -1, -1, -1, 21},
						 {-1, -1, -1, -1, 21, -1}};	
		int source = 0;
		
		assertThrows(NoPathException.class, () -> SPT.findSPT(edges_1, source));
		
		int[][] edges_2 = {{-1, -1, -1, -1, 2}, 
				{-1, -1, -1, -1, -1}, 
				{-1, -1, -1, -1, -1}, 
				{-1, -1, 3, -1, -1}, 
				{7, -1, -1, -1, -1}};
		
		assertThrows(NoPathException.class, () -> SPT.findSPT(edges_2, source));
	}
	
	/** Test whether the method detects there are no vertices connected to each other */
	@Test
	void noVerticesConnectedTest() {
		int[][] edges = {{-1, -1, -1, -1, -1}, 
				{-1, -1, -1, -1, -1}, 
				{-1, -1, -1, -1, -1}, 
				{-1, -1, -1, -1, -1}, 
				{-1, -1, -1, -1, -1}};
		int source = 0;
		
		assertThrows(NoPathException.class, () -> SPT.findSPT(edges, source));	
	}
	
	/** Testing how the method responds when 2 routes are the shortest path */
	@Test
	void multipleRoutesTest() {
		
		int[][] edges = 	{{-1, 1, -1, 1},
							 {-1, -1, -1, 2},
							 {-1, 2, -1, -1},
							 {-1, -1, 3, -1}};
		
		int length = calculateMinLength(edges, 0);
		assertEquals("Total weight of shortest path tree wasn't correct", 5, length);
	}
	
	/** Tests whether an InvalidGraphException is thrown for input graphs, which are cycles with no direction */
	@Test
	void undirectedCycleTest() {
		int[][] undirectedEdges = {{-1, 3, -1, 4},
				{3, -1, 7, -1},
				{-1, 7, -1, 2},
				{4, -1, 2, -1}};
		int source = 0;
		
		try {
			int[] actualAnswer = {-1, 0, 3, 0};
			assertArrayEquals("Algorithm can't deal with cycles", SPT.findSPT(undirectedEdges, source), actualAnswer);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception shouldn't have been thrown");
		}
	}
	
	/** Tests whether an InvalidGraphException is thrown for input graphs, which are cycles with direction */
	@Test
	void directedCycleTest() {
		int[][] directedEdges = {{-1, 1, -1, 1},
				{-1, -1, -1, 2},
				{-1, 2, -1, -1},
				{-1, -1, 3, -1}};
		int source = 0;
				
		try {
			int[] actualAnswer = {-1, 0, 3, 0};
			assertArrayEquals("Algorithm can't deal with cycles", SPT.findSPT(directedEdges, source), actualAnswer);
		} catch (InvalidSourceException | InvalidGraphException | LoopDetectedException | NoPathException e) {
			fail("Exception shouldn't have been thrown");
		}
	}

}