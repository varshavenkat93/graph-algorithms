/**
 * This Java program is used to call vector_of_bools GreenMarl code.
 */


import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;

public class CallingProcedureFromProcedure{

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram calling_procedure = session.compileProgram("examples/gm/calling_procedure_inside_procedure.gm");
    AnalysisResult<Integer> result = calling_procedure.run();
    System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

}
