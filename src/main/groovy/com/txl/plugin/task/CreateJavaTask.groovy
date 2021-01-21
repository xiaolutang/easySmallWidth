package com.txl.plugin.task

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CreateBuildConfigTask extends DefaultTask {
    /**
     * 文件名
     * */
    String fileName = "BuildConfig"
    String filePath = "src/main/java"
    String packageName = "com.txl.plugin"
    @TaskAction
    void doTask() {
        //生成java类
        TypeSpec.Builder builder = TypeSpec.classBuilder(fileName)
                .addModifiers(Modifier.PUBLIC)
        JavaFile javaFile = JavaFile.builder(packageName, builder.build()).build()
        //将java写入到文件夹下
//        File file = new File(project.buildDir, "generated/source/container")
        File file = new File(project.projectDir, filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        javaFile.writeTo(file)
        println "[write to]: ${file.absolutePath}"
    }

}
