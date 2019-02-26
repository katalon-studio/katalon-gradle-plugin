package com.katalon.gradle.plugin;

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import com.katalon.gradle.plugin.list.ListTestCasesTask;
import com.katalon.gradle.plugin.list.ListTestSuitesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.GroovyPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;

import java.util.ArrayList;

public class KatalonGradlePlugin implements Plugin<Project> {
    private static final String RESOURCE_DIR = "Keywords";
    private static final String METADATA_FILE = "katalon-plugin.json";
    static final String GROOVY_DEPENDENCY = "org.codehaus.groovy:groovy-all";
    static final String GROOVY_VERSION = "2.4.7";

    private void applyPlugins(Project project) {
        project.getPlugins().apply(JavaPlugin.class);
        project.getPlugins().apply(GroovyPlugin.class);
        project.getPlugins().apply("com.github.johnrengelman.shadow");
    }

    private void addSourceSets(Project project) {
        JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
        SourceSet mainSourceSet = javaPluginConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        SourceDirectorySet resourceDirectorySet = mainSourceSet.getResources();

//        Set directory where resources reside
        ArrayList<String> resourceDirs = new ArrayList<>();
        resourceDirs.add(RESOURCE_DIR);
        resourceDirectorySet.setSrcDirs(resourceDirs);

//        Include only a specific file
        ArrayList<String> resourceFiles = new ArrayList<>();
        resourceFiles.add(METADATA_FILE);
        resourceDirectorySet.setIncludes(resourceFiles);
    }

    public void apply(Project project) {
        applyPlugins(project);
        addSourceSets(project);

        KatalonGradlePluginExtension extension = project.getExtensions()
                .create("katalon", KatalonGradlePluginExtension.class);

        project.getTasks().create("katalonListTestCases", ListTestCasesTask.class);

        project.getTasks().create("katalonListTestSuites", ListTestSuitesTask.class);

        project.getTasks().create("katalonCopyDependencies", CopyDependencyTask.class);

        project.getTasks().create("katalonPluginConfigure", AddCompileOnlyDependencyTask.class);

        ShadowJar shadowTask = (ShadowJar) project.getTasks().getByName("shadowJar");

        Task packageTask = project.getTasks().create("katalonPluginPackage");

        Task relocationTask = project.getTasks().create(
                "katalonPluginShade",
                RelocatePackageTask.class,
                task -> task.setExtension(extension));

        packageTask.dependsOn(shadowTask);
        shadowTask.dependsOn(relocationTask);

        // Exclude generated source by Katalon Studio from being bundled
        shadowTask.exclude("internal");
        shadowTask.exclude("CustomKeywords.class");
    }
}
