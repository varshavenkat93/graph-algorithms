/**
 * This Java program is used to call vector_of_bools GreenMarl code.
 */


import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;

public class VectorOfBools{

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram vector_testing = session.compileProgram("examples/gm/vector_of_bools.gm");
    AnalysisResult<Integer> result = vector_testing.run();
    System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

}
