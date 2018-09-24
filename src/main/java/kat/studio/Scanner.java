package kat.studio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scanner {

  public List<TestCase> scanTestCases(String root) throws IOException {
    Path rootPath = Paths.get(root);
    Path tcDir = Paths.get(root, "Test Cases");
    Stream<Path> matches = Files.find(tcDir, Integer.MAX_VALUE, (path, basicFileAttributes) -> path.toString().endsWith(".tc"));
    List<TestCase> tcs = matches.map(path -> {
      Path fileName = path.getFileName();
      TestCase tc = new TestCase();
      tc.setName(fileName.toString());
      tc.setPath(rootPath.relativize(path).toString());
      return tc;
    }).collect(Collectors.toList());

    return tcs;
  }

  public List<TestSuite> scanTestSuites(String root) throws IOException {
    Path rootPath = Paths.get(root);
    Path tcDir = Paths.get(root, "Test Suites");
    Stream<Path> matches = Files.find(tcDir, Integer.MAX_VALUE, (path, basicFileAttributes) -> path.toString().endsWith(".ts"));
    List<TestSuite> testSuites = matches.map(path -> {
      Path fileName = path.getFileName();
      TestSuite tc = new TestSuite();
      tc.setName(fileName.toString());
      tc.setPath(rootPath.relativize(path).toString());
      return tc;
    }).collect(Collectors.toList());

    return testSuites;
  }
}
