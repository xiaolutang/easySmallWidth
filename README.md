# EasySmallWidth

## 设计目的：

让smallWidth屏幕适配更加灵活，容易，减轻开发者的维护难度。

## EasySmallWidth原理

AndroidStudio通过Gradle进行打包，会经过 **Initialization**、**Configuration**、**Execution** 三个阶段。在configuration会读取所有项目的配置，并最终生成Gradle TASK执行的有相图。EasySmallWidth就是在Gradle 读取项目配置之后，生成有相图之前，插入自定义Task,让我们需要的dimens文件，先于构建Apk。通过观察Gradle打包生成apk的Task，不论是Android library 还是android application 在执行task之前都会执行 preBuild Task 。因此EasySmallWidth选取PreBuild作为插入点。进行适配工作。

## EasySmallWidth配置项

| 属性               | 作用                                                         | 默认值                           |
| ------------------ | ------------------------------------------------------------ | -------------------------------- |
| enableAdapter      | 是否开启适配  true  开启，false 关闭                         | true                             |
| defaultDesignWidth | 默认设计图宽度                                               | 360f                             |
| resPath            | res相对 当前模块的路径。系统会根据res查找默认的dimen文件，然后创建在此路径下创建values-swxxxdp文件。考虑到项目可能从eclipse迁移过来。增加这个配置项 | /src/main/res/                   |
| needToAdaptedWidth | 需要适配的最小宽度，需要自己添加                             | size为0的set                     |
| conversionMap      | 需要自定义转换的dimens文件，默认不需要填写。                 | 以Integer为Key,Float为value空map |

EasySmallWidth包含二级配置，总体配置，模块配置。具体的配置参考EasySmallWidth的使用step3。当没有模块配置的时候，会使用总体配置 。



## EasySmallWidth的使用

**step1:**  

在根目录的build.gradle添加下面的代码

![image-20210125210118474](img\根buildgradle引入.png)

```groovy
classpath 'com.txl.EasySmallWidth:easySmallWidth:1.0.0'
```

**step2:** 引入插件

项目的入口引入插件,EasySmallWidth会自动遍历当前项目的 所有模块并为它添加适配任务，所以不需要在 每个模块重复引用插件

```
apply plugin: 'BuildAdaptionPlugin'
```

插件引入成功后在模块的中可以看到EasySmallWidth为每个 模块添加的创建和删除适配文件的task

![image-20210125210731910](img\每个模块的创建和删除适配文件task.png)

如果Android Studio 没有显示task  重新设置下下面的属性即可

![image-20210125212842090](img\android_studio_显示gradleTask.png)

**step3:添加配置项**

```groovy
adaptionAppExtension{
    defaultDesignWidth = 360f  //默认设计图的宽度为360dp
    enableAdapter = true //开启屏幕适配
    needToAdaptedWidth.add(400) // 需要适配最小宽度400dp  即会创建values-sw400dp
    needToAdaptedWidth.add(411) // 需要适配最小宽度411dp  即会创建values-sw411dp
    needToAdaptedWidth.add(441) // 需要适配最小宽度441dp  即会创建values-sw441dp
//   res路径，默认不用配置
//    resPath = "${File.separator}src${File.separator}main${File.separator}res${File.separator}"
    
    //针对testAutoBuildDimen 模块创建独自的配置
    def ex = createBuildAdaptionPluginExtension(project,adaptionAppExtension,"testAutoBuildDimen")
    //对模块testAutoBuildDimen sw400与默认值的转换关系为 实际值*1.0
    ex.conversionMap.put(400,1.0f)
    //对模块testAutoBuildDimen sw400与默认值的转换关系为 实际值*2.0
    ex.conversionMap.put(411,2.0f)
    //对模块testAutoBuildDimen sw400与默认值的转换关系为 实际值*3.0
    ex.conversionMap.put(441,3.0f)
    def aeasy = createBuildAdaptionPluginExtension(project,adaptionAppExtension,"aeasybuild")
    //aeasy  模块的默认宽度是375dp
    aeasy.defaultDesignWidth = 375f
}
```

## 验证EasySmallWidth

直接通过android studio 进行编译，然后在对应模块下就会产生对应的dimens文件

**app模块：**

![image-20210125214106890](img\app模块的适配文件.png)



可以看到 sw400dp中的 dimen值 = 400/360 * 实际值  符合SmallWidth要求

**aeasybuild模块：**

![image-20210125214409760](img\aeasybuild模块适配文件.png)

可以看到   sw400dp中的 dimen值 = 400/375 * 实际值  符合SmallWidth要求  (我们在配置的时候将aeasybuild的默认宽度设置成立375dp)

**testAutoBuildDimen模块：**

![image-20210125214737257](img\testAutoBuild模块适配文件.png)

为什么这个模块的值不是按照smallWidth来的呢？因为我们在前面配置的时候对它做了自定义转换，sw400与设计图1.0倍处理

# EasySmallWidth demo

地址： https://github.com/xiaolutang/androidTool

# EasySmallWidth特性

缺点：

1. 由于SmallWidth适配的原因，它无法解决为了适配需要生成更多的文件的问题。

优点：

1. 代码侵入性低
2. 基于系统原理适配稳定性高
3. 方便适配方案切换，可与AndroidAutoSize适配方案进行无缝切换
4. 兼容性好，与第三方库适配没有冲突（与AndroidAutoSize不兼容）
5. 适配灵活，可以针对每一个模块进行适配。

可以这样说EasySmallWidth做为SmallWidth的升级版，主要的职责就是解决SmallWidth的维护性 和代码入侵的问题。

# 注意事项 

1. EasySmallWidth为了方便使用者切换适配方案（主要是AndroidAutoSize），在执行clean  task 的时候会删除每个模块下的适配文件，如果想买有特殊 处理，需要提前保存下
2. EasySmallWidth兼容三方库是指兼容第三方运用SmallWidth原理进行适配的三方库，它与AutoSize并不兼容。

# 关于三方库的适配问题建议

因为现在流行的AutoSize与SmallWidth之间不兼容，建议三方库不进行屏幕适配，对外公布自己的设计图尺寸，同时将默认的dimen文件发不出来。具体的适配工作交给开发者自己选择。

# 说明

由于个人水平有限，个人也是一边看gradle文档，一边开发。项目免不了有一些奇奇怪怪的问题，欢迎大家和我一起开发这个框架。让屏幕适配变得更加简单