package com.katalon.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.compile.GroovyCompile;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.work.DisableCachingByDefault;

import java.util.ArrayList;

import static com.katalon.gradle.plugin.KatalonGradlePlugin.METADATA_FILE;

@DisableCachingByDefault(because = "Configures source sets as a side effect; has no cacheable outputs")
public class AddSourceSets extends DefaultTask {
    @Input
    private KatalonGradlePluginExtension extension;
    private Project project;

    public AddSourceSets() {
        project = this.getProject();
        project.getTasks().withType(JavaCompile.class)
                .forEach(javaCompile -> javaCompile.dependsOn(this));
        project.getTasks().withType(GroovyCompile.class)
                .forEach(groovyCompile -> groovyCompile.dependsOn(this));
    }

    @TaskAction
    public void addDependency() {
        SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
        SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        SourceDirectorySet resourceDirectorySet = mainSourceSet.getResources();

//        Set directory where resources reside
        ArrayList<String> resourceDirs = new ArrayList<>();
        resourceDirs.add(getExtension().getMetadataDir());
        resourceDirectorySet.setSrcDirs(resourceDirs);

//        Include only a specific file
        ArrayList<String> resourceFiles = new ArrayList<>();
        resourceFiles.add(METADATA_FILE);
        resourceDirectorySet.setIncludes(resourceFiles);
    }

    public KatalonGradlePluginExtension getExtension() {
        return extension;
    }

    public void setExtension(KatalonGradlePluginExtension extension) {
        this.extension = extension;
    }
}
