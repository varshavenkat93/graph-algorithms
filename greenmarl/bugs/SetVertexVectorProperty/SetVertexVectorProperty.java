/**
 * This Java Program is used to illustrate the time consumed by VertexProperty.set method to set a vertex property of Vector Type
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

public class SetVertexVectorProperty {
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
     PgxGraph graph = session.readGraphWithProperties("examples/graphs/amazon_graphlab.adj.json");
     VertexProperty<Integer,PgxVect<Integer>> bit_mask = graph.createVertexVectorProperty(PropertyType.INTEGER, BITMASK_LENGTH, "bit_mask");
     Iterable<Map.Entry<PgxVertex<Integer>,PgxVect<Integer>>> bm_iterator = bit_mask.getValues();
     int i=0;
     for(Map.Entry me : bm_iterator) {
     	 Integer[] bitmask = create_bitmask() ;
         PgxVect<Integer> bm = new PgxVect(bitmask, PropertyType.INTEGER ) ;
         System.out.println(i++);
         bit_mask.set((PgxVertex<Integer>)me.getKey(), bm);
     
     }
     System.out.println("Done");  
 }

} 
