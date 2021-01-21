package com.txl.plugin.task

import com.txl.plugin.xmlutils.AndroidDimenXMLParser
import com.txl.plugin.xmlutils.FileUtil
import com.txl.plugin.xmlutils.FileX
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * 每个模块进行适配的task
 * 每一个模块应该有下面的属性可以配置
 * 1.设计图 宽度（默认360）
 * 2.需要适配的尺寸，由总体一起配置，
 * 3.每一个需要适配尺寸的转换策略,默认是  缩放因子 * value中dimen的实际大小 （缩放因子=当前适配尺寸/设计图尺寸）
 * 4.每个模块可以自由开启或关闭适配，适配插件有总体开关
 * 5.设置res目录的路径
 * */
public class ModuleAdaptionTaskD extends DefaultTask{
    /**
     * 默认设计及图宽度
     * */
    @Input
    float defaultDesignWidth = 360f
    /**
     * 需要适配的最小宽度  比如 {400f,411f,480f}单位是dp，这个值不在每个模块单独设置，由总体配置来
     * */
    @Input
    Set<Integer> needToAdaptedWidth = new HashSet<Integer>()
    /**
     * 转换因子,默认不进行设置
     * */
    @Input
    Map<Integer,Float> conversionMap = new HashMap<>()

    /**
     * 是否开启适配
     * */
    @Input
    boolean enableAdapter = true
    /**
     * 资源路径，默认src/main/res
     * */
    @Input
    String resPath = ""

    @TaskAction
    def adaption(){
        if(!enableAdapter){
            println("ModuleAdaptionTask ${project.name}  cancel adaption")
            return
        }
        //强行指定路径来判断对应逻辑
        String originFilePath = project.getProjectDir().path+resPath+"values"+File.separator+"dimens.xml"
        println("ModuleAdaptionTask name ${project.name}  origin path ${originFilePath}  has sacle ${conversionMap}")
        FileX filex = new FileX(originFilePath)
        if(!filex.exists()){
            return
        }
        Map<String,String> map = AndroidDimenXMLParser.readDimensXML(originFilePath)
        for (item in needToAdaptedWidth){
            try{
                println "ModuleAdaptionTask ${project.name}  start adaption width $item"

                String newFilePath = project.getProjectDir().path+resPath+"values-sw${item.toInteger()}dp"+File.separator+"dimens.xml"
                filex = new FileX(newFilePath)
                if(!filex.exists()){
                    FileUtil.createFile(newFilePath)
                }
                float scale = item/defaultDesignWidth
                if(conversionMap != null && conversionMap.containsKey(item)){
                    scale = conversionMap.get(item)
                    println("ModuleAdaptionTask module ${project.name} contain specail  ${item}  sacle ${scale}")
                }
                if(project.name == "testAutoBuildDimen"){
                    for (key in conversionMap.keySet()){
                        println("key is $key value $item  key  equals ${"$item".equals( key)}  == ${key == "$item"}")
                    }
                    println("ModuleAdaptionTask module ${project.name}  $conversionMap my specail ${item} value ${conversionMap.get("${item}")}")
                }
                AndroidDimenXMLParser.saveMap2XML(map,newFilePath,defaultDesignWidth,item,scale)
                println("ModuleAdaptionTask module ${project.name}  adaption success width ${item} newpath sacle ${scale}")
            }catch(IOException e1) {
                e1.printStackTrace()
            }
        }
    }
}
