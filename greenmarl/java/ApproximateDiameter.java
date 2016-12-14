/**
 * Java code to call Approximate Diameter Greenmarl implementation
 */


import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxVertex;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.common.util.vector.Vect;
import oracle.pgx.api.PgxVect;
import java.lang.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Character;

public class ApproximateDiameter {
  public static final int RAND_MAX = 32767;
  public static final int BITMASK_LENGTH = 64;
  public static Random rand = new Random();
  
  public static float my_rand(){
  	float res = (float) ( rand.nextInt(RAND_MAX) / (RAND_MAX + 1.0) );
  	return res;
  }
  
  public static int hash_value(){
  	int ret = 0;
  	while(my_rand() < 0.5){
  		ret ++;
  	} 
  	return ret;
  }

public static Integer[] create_bitmask(){
  	Integer[] bit_mask = new Integer[BITMASK_LENGTH];
  	for(int i = 0; i < BITMASK_LENGTH; i++) {
    		bit_mask[i] = Integer.valueOf(0); 
	}	
  	int hash = hash_value();
  	bit_mask[hash] = Integer.valueOf(1);
  	return bit_mask;
  	
  }

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram approximate_diameter = session.compileProgram("examples/gm/approximate_diameter3.gm");
    //PgxGraph graph = session.readGraphWithProperties("examples/graphs/amazon_graphlab.adj.json");
    PgxGraph graph = session.readGraphWithProperties("examples/graphs/test.adj.json");
    VertexProperty<Integer,Integer> id = graph.getVertexProperty("id");
    System.out.println("Number of nodes:"+(int)id.size());
    double n = (double)id.size();
    int maxIter = 256;
    int K =1;
    int log_n = (int) Math.log(n);
    VertexProperty<Integer,Integer> radius = graph.createVertexProperty(PropertyType.INTEGER, "radius");
    VertexProperty<Integer,PgxVect<Integer>> bit_string = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bit_string");
    VertexProperty<Integer,PgxVect<Integer>> bit_mask = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bit_mask");
    VertexProperty<Integer,PgxVect<Double>> neighbourhood = graph.createVertexVectorProperty(PropertyType.DOUBLE, maxIter, "neighbourhood");
     int i = 0;
     //To read bitmask from Powergraph 
     String fin = "/var/services/homes/vavenkatesh/graphlab/release/toolkits/graph_analytics/bitmasks_from_Powergraph.txt";
     BufferedReader br = new BufferedReader(new FileReader(fin));
     String line = null;
     PrintWriter writer = new PrintWriter(new FileOutputStream("bitmasks.txt", false));
     Iterable<Map.Entry<PgxVertex<Integer>,PgxVect<Integer>>> bm_iterator = bit_mask.getValues();
     for(Map.Entry me : bm_iterator) {
     	 if((line = br.readLine()) != null){
     	 Integer[] bitmask = new Integer[BITMASK_LENGTH];
     	 int j = 0;
         for (char ch: line.toCharArray()) {
         	bitmask[j++] = Integer.valueOf(Character.getNumericValue(ch));
         }
     	 
     	 //Comment reading bitmask from file and uncomment the following to generate bitmasks in Java
     	// Integer[] bitmask = create_bitmask() ;
         PgxVect<Integer> bm = new PgxVect(bitmask, PropertyType.INTEGER ) ;
         bit_mask.set((PgxVertex<Integer>)me.getKey(), bm); // takes a long time
         System.out.println(i++);
         }
       writer.println(bit_mask.get((PgxVertex<Integer>)me.getKey()));
    }
    br.close();
    writer.close();
    
    //Call the GreenMarl implementation
    AnalysisResult<Integer> result = approximate_diameter.run(graph, BITMASK_LENGTH,  maxIter, K, bit_mask, bit_string , neighbourhood, radius);
    
    //Writing Radius of each node to file
    writer = new PrintWriter(new FileOutputStream("radius_results.txt", false));
    Iterable<Map.Entry<PgxVertex<Integer>,Integer>> radius_iterator = radius.getValues();
    for(Map.Entry me : radius_iterator) {
         writer.print(me.getKey() + ": ");
   	 writer.println(me.getValue());
      }
    writer.close();
    
    //writing final bitmasks to file
    writer = new PrintWriter(new FileOutputStream("bitmask_results.txt", false));
    bm_iterator = bit_string.getValues();
    for(Map.Entry me : bm_iterator) {
         writer.print(me.getKey() + ": ");
   	 writer.println(me.getValue());
      }
    writer.close();
    
    //to write bitmask to be read and used by Powergraph
    writer = new PrintWriter(new FileOutputStream("/var/services/homes/vavenkatesh/graphlab/release/toolkits/graph_analytics/bitmask_initial.txt", false));
    bm_iterator = bit_mask.getValues();
    for(Map.Entry me : bm_iterator) {
        StringBuilder strNum = new StringBuilder();
        PgxVect<Integer> bitmask_vector = (PgxVect<Integer>) me.getValue();
	Integer[] initial_bitmask = bitmask_vector.toArray();
	for (int num : initial_bitmask) 
	{
	     strNum.append(num);
	}
	writer.println(strNum.toString());
      }
     writer.close();
     
    //Writing Neighbourhood values of each node for each hop to file
     writer = new PrintWriter(new FileOutputStream("neighbourhood.txt", false));
     Iterable<Map.Entry<PgxVertex<Integer>,PgxVect<Double>>> ngbr_iterator = neighbourhood.getValues();
     for(Map.Entry me : ngbr_iterator) {
         writer.print(me.getKey() + ": ");
         writer.println(me.getValue());
     }
     writer.close();
     
     //Printing out Results
     System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
    }
} 
