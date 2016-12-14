/**
 * Copyright (C) 2013 - 2015 Oracle and/or its affiliates. All rights reserved.
 */
package oracle.pgx.demos;

import oracle.pgx.api.CompiledProgram;
import oracle.pgx.api.Pgx;
import oracle.pgx.api.PgxGraph;
import oracle.pgx.api.PgxSession;
import oracle.pgx.config.Format;
import oracle.pgx.config.GraphConfig;

public class HdfsExample {

  public static void main(String[] mainArgs) throws Exception {
    PgxSession session = Pgx.createSession("my-session");
    PgxGraph g1 = session.readGraphWithProperties("hdfs:/user/pgx/sample.adj.json");

    GraphConfig pgbConfig = g1.store(Format.PGB, "hdfs:/user/pgx/sample.pgb");
    PgxGraph g2 = session.readGraphWithProperties(pgbConfig);
    System.out.println("g1 N = " + g1.getNumVertices() + " E = " + g1.getNumEdges());
    System.out.println("g2 N = " + g2.getNumVertices() + " E = " + g2.getNumEdges());

    CompiledProgram p = session.compileProgram("hdfs:/user/pgx/max_degree.gm");
    System.out.println("compiled " + p.getName());
  }
}
