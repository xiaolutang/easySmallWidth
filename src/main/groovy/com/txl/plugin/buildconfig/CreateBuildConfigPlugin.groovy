package com.txl.plugin.buildconfig

import org.apache.tools.ant.Project
import org.gradle.api.Plugin

class CreateBuildConfigPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        def createTask = target.tasks.register("createBuildC",CreateBuildConfigTask)
        target.afterEvaluate{
            try {
                def preBuild = target.getTasks().getByName("preBuild")
                preBuild.configure {
                    dependsOn createTask
                }
            }catch(Exception e){
                e.printStackTrace()
            }
        }
    }
}
