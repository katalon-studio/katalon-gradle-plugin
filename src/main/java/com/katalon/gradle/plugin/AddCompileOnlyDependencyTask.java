package com.katalon.gradle.plugin;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.util.XmlParser;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.compile.GroovyCompile;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import static com.katalon.gradle.plugin.KatalonGradlePlugin.GROOVY_DEPENDENCY;
import static com.katalon.gradle.plugin.KatalonGradlePlugin.GROOVY_VERSION;

public class AddCompileOnlyDependencyTask extends DefaultTask {
    private Project project;

    public AddCompileOnlyDependencyTask() {
        this.project = this.getProject();
        project.getTasks().withType(JavaCompile.class)
                .forEach(javaCompile -> javaCompile.dependsOn(this));
        project.getTasks().withType(GroovyCompile.class)
                .forEach(groovyCompile -> groovyCompile.dependsOn(this));

        String groovyDependency = GROOVY_DEPENDENCY + (GROOVY_VERSION.isEmpty() ? "" : (":" + GROOVY_VERSION));
        this.project.getDependencies().add("shadow", groovyDependency);
    }

    @TaskAction
    public void addDependency() {
        /*
          Parse Eclipse .classpath and add dependency to build.gradle
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
                this.project.getDependencies().add("compileOnly", this.project.files(filePath));
            });
        } catch (FileNotFoundException e) {
            System.out.println(".classpath Not Found. No dependency added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
