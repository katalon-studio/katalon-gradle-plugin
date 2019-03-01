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
    static final String RESOURCE_DIR = "Keywords";
    static final String METADATA_FILE = "katalon-plugin.json";
    static final String GROOVY_DEPENDENCY = "org.codehaus.groovy:groovy-all";
    static final String GROOVY_VERSION = "2.4.7";

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

        project.getTasks().create("katalonPluginAddDependency", AddCompileOnlyDependencyTask.class);

        project.getTasks().create("katalonPluginAddResource", AddSourceSets.class,
                task -> task.setExtension(extension));

        ShadowJar shadowTask = (ShadowJar) project.getTasks().getByName("shadowJar");

        Task packageTask = project.getTasks().create("katalonPluginPackage");

        Task relocationTask = project.getTasks().create("katalonPluginShade", RelocatePackageTask.class,
                task -> task.setExtension(extension));

        packageTask.dependsOn(shadowTask);
        shadowTask.dependsOn(relocationTask);

        // Exclude generated source by Katalon Studio from being bundled
        shadowTask.exclude("internal");
        shadowTask.exclude("CustomKeywords.class");
    }
}
