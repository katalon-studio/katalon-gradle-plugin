package com.katalon.gradle.plugin;

import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import com.katalon.gradle.plugin.list.ListTestCasesTask;
import com.katalon.gradle.plugin.list.ListTestSuitesTask;
import groovy.util.Node;
import groovy.util.NodeList;
import groovy.util.XmlParser;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.compile.GroovyCompile;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.util.Map;

public class KatalonGradlePlugin implements Plugin<Project> {
    private static final String PACKAGE_PREFIX = "myapp";

    private void parseClasspath(Project project) {
        /**
         * Parse Eclipse .classpath and add dependency to build.gradle
         */
        try {
            XmlParser parser = new XmlParser();
            File classpathFile = new File(".classpath");
            Node root = parser.parse(classpathFile);

            // Get list of nodes that have tag <classpathentry>
            NodeList classpathEntries = (NodeList) root.get("classpathentry");

            // Remove all classpathEntry that is not lib
            classpathEntries.removeIf(o -> {
                Node node = (Node) o;
                Map attributes = node.attributes();
                Object fileKind = attributes.get("kind");
                return !fileKind.equals("lib");
            });

            // Add gradle dependency with method `compileOnly`
            classpathEntries.forEach(o -> {
                Node node = (Node) o;
                Map attributes = node.attributes();
                Object filePath = attributes.get("path");
                project.getDependencies().add("compileOnly", project.files(filePath));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void apply(Project project) {

        project.getTasks().create("katalonListTestCases", ListTestCasesTask.class);

        project.getTasks().create("katalonListTestSuites", ListTestSuitesTask.class);

        project.getTasks().create("katalonCopyDependencies", CopyDependencyTask.class);


        Task bundleTask = project.getTasks().create("katalonBundle");

        project.getTasks().whenTaskAdded(new Action<Task>() {
            @Override
            public void execute(Task shadowJar) {
                if (shadowJar.getName().equals("shadowJar")) {
                    bundleTask.dependsOn(shadowJar);

                    Task katalonConfigurations = project.getTasks()
                            .create("katalonConfigurations", task -> parseClasspath(project));
                    project.getTasks().withType(JavaCompile.class)
                            .forEach(javaCompile -> javaCompile.dependsOn(katalonConfigurations));
                    project.getTasks().withType(GroovyCompile.class)
                            .forEach(groovyCompile -> groovyCompile.dependsOn(katalonConfigurations));


                    ConfigureShadowRelocation relocationTask = project.getTasks().create(
                            "katalonRelocate",
                            ConfigureShadowRelocation.class,
                            configureShadowRelocation -> {
                                configureShadowRelocation.setTarget((ShadowJar) shadowJar);
                                configureShadowRelocation.setPrefix(PACKAGE_PREFIX);
                            });

                    shadowJar.dependsOn(relocationTask);
                }
            }
        });
    }
}
