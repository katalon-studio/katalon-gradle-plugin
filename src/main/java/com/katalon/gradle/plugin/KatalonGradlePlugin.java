package com.katalon.gradle.plugin;

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import com.katalon.gradle.plugin.list.ListTestCasesTask;
import com.katalon.gradle.plugin.list.ListTestSuitesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.GroovyPlugin;
import org.gradle.api.plugins.JavaPlugin;

public class KatalonGradlePlugin implements Plugin<Project> {

    private void applyPlugins(Project project) {
        project.getPlugins().apply(JavaPlugin.class);
        project.getPlugins().apply(GroovyPlugin.class);
        project.getPlugins().apply("com.github.johnrengelman.shadow");
    }

    public void apply(Project project) {
        applyPlugins(project);

        KatalonGradlePluginExtension extension = project.getExtensions()
                .create("katalon", KatalonGradlePluginExtension.class);

        project.getTasks().create("katalonListTestCases", ListTestCasesTask.class);

        project.getTasks().create("katalonListTestSuites", ListTestSuitesTask.class);

        project.getTasks().create("katalonCopyDependencies", CopyDependencyTask.class);

        project.getTasks().create("katalonConfigurations", AddCompileOnlyDependencyTask.class);

        ShadowJar shadowTask = (ShadowJar) project.getTasks().getByName("shadowJar");

        Task bundleTask = project.getTasks().create("katalonBundle");

        Task relocationTask = project.getTasks().create(
                "katalonRelocate",
                RelocatePackageTask.class,
                task -> task.setExtension(extension));

        bundleTask.dependsOn(shadowTask);
        shadowTask.dependsOn(relocationTask);

        // Exclude generated source by Katalon Studio from being bundled
        shadowTask.exclude("internal");
        shadowTask.exclude("CustomKeywords.class");
    }
}
