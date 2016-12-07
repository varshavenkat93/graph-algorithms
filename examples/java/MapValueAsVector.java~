/**
 * Copyright (C) 2013 - 2015 Oracle and/or its affiliates. All rights reserved.
 */


import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;

public class MapValueAsVector {

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram hello_world = session.compileProgram("examples/gm/map_value_as_vector.gm");
   // PgxGraph graph = session.readGraphWithProperties("examples/graphs/sample.adj.json");
    AnalysisResult<Integer> result = hello_world.run();
    System.out.println("Result = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

}
