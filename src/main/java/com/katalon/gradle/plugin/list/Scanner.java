package com.katalon.gradle.plugin.list;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scanner {

  public List<TestCase> scanTestCases(String root) throws IOException {
    Path rootPath = Paths.get(root);
    List<TestCase> testCases = scan(root, "Test Cases", ".tc", path -> {
      Path fileName = path.getFileName();
      TestCase tc = new TestCase();
      tc.setName(fileName.toString());
      tc.setPath(rootPath.relativize(path).toString());
      return tc;
    });

    return testCases;
  }

  public List<TestSuite> scanTestSuites(String root) throws IOException {
    Path rootPath = Paths.get(root);
    List<TestSuite> testSuites = scan(root, "Test Suites", ".ts", path -> {
      Path fileName = path.getFileName();
      TestSuite tc = new TestSuite();
      tc.setName(fileName.toString());
      tc.setPath(rootPath.relativize(path).toString());
      return tc;
    });

    return testSuites;
  }

  private <T> List<T> scan(String root, String subPath, String suffix, Function<? super Path, T> mapping) throws IOException {
    Path tcDir = Paths.get(root, subPath);
    Stream<Path> matches = Files.find(tcDir, Integer.MAX_VALUE, (path, basicFileAttributes) -> path.toString().endsWith(suffix));
    List<T> results = matches
      .map(mapping)
      .collect(Collectors.toList());

    return results;
  }
}
