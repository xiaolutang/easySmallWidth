package com.txl.plugin.task;


import com.txl.plugin.xmlutils.FileUtil;
import com.txl.plugin.xmlutils.FileX;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 删除 {@link ModuleAdaptionTask} 创建的文件
 * */
public class ModuleDeleteAdaptionTask extends DefaultTask{
    private final static String TAG = "ModuleDeleteAdaptionTask";
    /**
     * 默认设计及图宽度
     * */
    @Input
    public float defaultDesignWidth = 360f;
    /**
     * 需要适配的最小宽度  比如 {400f,411f,480f}单位是dp，这个值不在每个模块单独设置，由总体配置来
     * */
    @Input
    public Set<Integer> needToAdaptedWidth = new HashSet<Integer>();
    /**
     * 转换因子,默认不进行设置
     * */
    @Input
    public Map<Integer,Float> conversionMap = new HashMap<>();

    /**
     * 是否开启适配
     * */
    @Input
    public boolean enableAdapter = true;
    /**
     * 资源路径，默认src/main/res
     * */
    @Input
    public String resPath = "";

    @TaskAction
    void adaption(){
        Project project = getProject();
        FileX filex = null;
        for (int item : needToAdaptedWidth){
            try{
                String newFilePath = getProject().getProjectDir().getPath()+resPath+"values-sw"+item+"dp"+File.separator+"dimens.xml";
                System.out.println(TAG +project.getName()+ " : delete file "+newFilePath);
                filex = new FileX(newFilePath);
                if(filex.exists()){
                    FileUtil.delFile(newFilePath);
                }
                filex = new FileX(getProject().getProjectDir().getPath()+resPath+"values-sw"+item+"dp");
                if(filex.exists() && filex.isDirectory() && (filex.listFiles() == null || filex.listFiles().length == 0)){
                    filex.delete();
                }
            }catch(Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
