/**
 * This Java program calls print_testing.gm to test the print functionality of Greenmarl.
 Current version of PGX 1.2.1 does not support print in Remote mode.
 */

import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;

public class PrintTesting {

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram print_testing = session.compileProgram("examples/gm/print_testing.gm");
    AnalysisResult<Integer> result = print_testing.run();
    System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

}
