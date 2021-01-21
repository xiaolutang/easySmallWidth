package com.txl.plugin

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.SetProperty

/**
 * 扩展构建属性
 * 每一个模块应该有下面的属性可以配置
 * 1.设计图 宽度（默认360）
 * 2.需要适配的尺寸，由总体一起配置，
 * 3.每一个需要适配尺寸的转换策略,默认是  缩放因子 * value中dimen的实际大小 （缩放因子=当前适配尺寸/设计图尺寸）
 * 4.每个模块可以自由开启或关闭适配，适配插件有总体开关
 * 5.设置res目录的路径
 * */
@CompileStatic
public class BuildAdaptionPluginExtension {
    BuildAdaptionPluginExtension(Project project){
        this.needToAdaptedWidth = project.objects.setProperty(Integer)
        this.conversionMap = project.objects.mapProperty(Integer, Float)
        this.subAdaptionPluginExtensionMapProperty = project.objects.mapProperty(String, BuildAdaptionPluginExtension)
    }
    /**
     * 默认设计及图宽度
     * */
    float defaultDesignWidth = 0
    /**
     * 需要适配的最小宽度  比如 {400,411,480}单位是dp，这个值不在每个模块单独设置，由总体配置来
     * */
    SetProperty<Integer> needToAdaptedWidth
    /**
     * 转换因子,默认不进行设置,针对每一个适配最小宽度进行独立设置，
     * 假设要适配的最小宽度是 400dp  转换因子是 1.5  那么就用400作为key，1.5作为value值
     * */
    MapProperty<Integer, Float> conversionMap

    /**
     * 是否开启适配
     * */
    boolean enableAdapter = true
    /**
     * 资源路径，默认src/main/res
     * */
    String resPath = ""

    /**
     * 子模块适配配置
     * */
    MapProperty<String, BuildAdaptionPluginExtension> subAdaptionPluginExtensionMapProperty

    @CompileStatic
    public BuildAdaptionPluginExtension createBuildAdaptionPluginExtension(Project project,BuildAdaptionPluginExtension parent,String name){
        def ex =  new BuildAdaptionPluginExtension(project)
        parent.subAdaptionPluginExtensionMapProperty.put(name,ex)
        return ex
    }
}
