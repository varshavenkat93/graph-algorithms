/**
 * This Java program is used to call vector_of_vectors_testing GreenMarl code.
 */

import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.common.util.vector.Vect;
import oracle.pgx.api.PgxVect;


public class VertexVectorProperty {

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram vertex_vector_property = session.compileProgram("examples/gm/vertex_vector_property.gm");
    PgxGraph graph = session.readGraphWithProperties("examples/graphs/sample_lp.adj.json");
    VertexProperty<Integer,Integer> id = graph.getVertexProperty("id");
    System.out.println("Number of nodes:"+(int)id.size());	
    VertexProperty<Integer,PgxVect<Integer>> bitstring = graph.createVertexVectorProperty(PropertyType.INTEGER,(int)id.size(), "bitstring");
    AnalysisResult<Integer> result = vertex_vector_property.run(graph, (int)id.size(), bitstring );
    System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

} 
