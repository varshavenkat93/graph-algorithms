
import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;
import oracle.pgx.api.VertexProperty;
import oracle.pgx.common.types.PropertyType;
import oracle.pgx.common.util.vector.Vect;
import oracle.pgx.api.PgxVect;


public class NegativeIndexing {

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram negative_indexing = session.compileProgram("examples/gm/negative_indexing.gm");
    AnalysisResult<Integer> result = negative_indexing.run();
    System.out.println("Result = " + result.getReturnValue().intValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

} 
