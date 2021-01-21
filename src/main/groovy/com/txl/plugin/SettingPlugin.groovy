package com.txl.plugin

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class SettingPlugin implements Plugin<Settings> {
    void apply(Settings settings) {
       for (item in settings.getRootProject().children){
           println( "SettingPlugin item name :${item.name}  path :${item.path}")
       }
    }
}
