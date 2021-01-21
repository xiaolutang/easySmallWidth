package com.txl.plugin.buildconfig

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import javax.lang.model.element.Modifier

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
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                .addField(String.class,"VERSION_NAME",Modifier.PUBLIC,Modifier.STATIC,Modifier.FINAL)
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
