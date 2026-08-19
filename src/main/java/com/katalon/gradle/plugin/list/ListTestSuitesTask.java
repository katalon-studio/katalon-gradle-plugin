package com.katalon.gradle.plugin.list;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

import java.io.File;
import java.util.List;

@DisableCachingByDefault(because = "Only prints results to the console")
public class ListTestSuitesTask extends DefaultTask {

    private final File rootDir;

    public ListTestSuitesTask() {
        this.rootDir = getProject().getRootDir();
    }

    @TaskAction
    void scanTestSuites() {
        String rootPath = rootDir.getAbsolutePath();
        Scanner scanner = new Scanner();
        try {
            List<TestSuite> testSuites = scanner.scanTestSuites(rootPath);
            testSuites.forEach(suite -> System.out.println(suite.getPath()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
