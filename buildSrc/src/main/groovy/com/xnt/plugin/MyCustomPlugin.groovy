package com.xnt.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyCustomPlugin implements Plugin<Project> {
    void apply(Project project) {

        project.extensions.create("pluginExtension",CustomPluginExtension.class);

        project.task('myTask') << {
            println "Hi this is micky's plugin"
        }

        project.task("name")<<{
            println("my name is "+project.pluginExtension.customName)
        }
    }
}