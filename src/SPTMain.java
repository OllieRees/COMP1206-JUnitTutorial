
public class SPTMain {
	
	public static void main(String[] args) {
		
		int[][] edges = {{-1, 1, -1, -1, -1},
						{-1, -1, 3, 5, -1},
						{-1, -1, -1, 3, -1},
						{-1, -1, -1, -1, 5},
						{-1, -1, -1, -1, -1}};
		int source = 0;
		
		try {
			int[] tree = SPT.findSPT(edges, source);
			for(int e : tree) 
				System.out.println(e);
		} catch(InvalidGraphException e) {
			System.err.println("InvalidGraphException: ");
		}  catch(NoPathException e) {
			System.err.println("NoPathException: ");
		} catch(InvalidSourceException e) {
			System.err.println("InvalidSourceException: ");
		} catch(LoopDetectedException e) {
			System.err.println("LoopDetectedException: ");
		} 
	}
	
	private static int[][] populateEdges(int size) {
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
	
}
