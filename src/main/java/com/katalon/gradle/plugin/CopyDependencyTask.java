package com.katalon.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileSystemOperations;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

import javax.inject.Inject;

@DisableCachingByDefault(because = "Copies files into a directory outside of Gradle's managed build outputs")
public abstract class CopyDependencyTask extends DefaultTask {

    private static final String LIBRARY_PREFIX = "katalon_generated_";

    @Classpath
    public abstract ConfigurableFileCollection getRuntimeClasspath();

    @Inject
    protected abstract FileSystemOperations getFileSystemOperations();

    @Inject
    protected abstract ObjectFactory getObjectFactory();

    @TaskAction
    public void copy() {
        getFileSystemOperations().delete(deleteSpec ->
                deleteSpec.delete(
                        getObjectFactory().fileTree()
                                .from("Drivers")
                                .include("**/" + LIBRARY_PREFIX + "*")
                )
        );

        getFileSystemOperations().copy(copySpec ->
                copySpec
                        .from(getRuntimeClasspath())
                        .into("Drivers")
                        .rename(s -> LIBRARY_PREFIX + s));
    }
}
