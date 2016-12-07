/**
 * Copyright (C) 2013 - 2015 Oracle and/or its affiliates. All rights reserved.
 */
package oracle.pgx.demos;

import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.api.internal.AnalysisResult;

public class CompilationExample {

  public static void main(String[] args) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    CompiledProgram maxDegree = session.compileProgram("examples/gm/max_degree.gm");
    PgxGraph graph = session.readGraphWithProperties("examples/graphs/sample.adj.json");
    AnalysisResult<Integer> result = maxDegree.run(graph);
    System.out.println("max_degree = " + result.getReturnValue() + " (took " + result.getExecutionTimeMs() + "ms)");
  }

}
