package com.chbcraft.internals.components;

import com.chbcraft.internals.components.entries.EntryCreater;
import com.chbcraft.internals.components.entries.PluginEntry;
import com.chbcraft.internals.components.utils.ConfigurationUtil;
import com.chbcraft.internals.entris.config.Configuration;
import com.chbcraft.plugin.CustomPlugin;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FloatSphere {
    private volatile static PluginsProxy proxy = null;
    private static EntryCreater entryCreater = null;
    /**
     * 创建插件代理模块
     * @return 返回插件代理模块
     */
    public static PluginsProxy createPluginProxy(){
        synchronized (FloatSphere.class){
            if(proxy == null){
                if(proxy==null){
                    proxy = new CustomPluginProxy();
                }
            }
        }
        return proxy;
    }

    /**
     * 创建配置文件对象
     * @param map 从磁盘中存储的配置文件键值对
     * @return 返回配置文件对象
     */
    public static Configuration createConfig(Object map){
        Configuration config = null;
        if(map instanceof Map){
            config = new PluginConfiguration();
            Set<Map.Entry<String,Object>> objectSet = ConfigurationUtil.castToMap(map).entrySet();
            for(Map.Entry<String,Object> section : objectSet){
                config.addValueByKey(section.getKey(), section.getValue());
            }
        }
        return config;
    }

    /**
     * 使用创造器创建Entry
     * @param pluginConfig 插件配置文件
     * @param plugin 插件本体
     * @param clazz 插件的所有全限定名
     * @return 返回插件集合
     */
    public static PluginEntry createPluginEntry(Configuration pluginConfig, CustomPlugin plugin, ArrayList<String> clazz){
        if(entryCreater == null)
            entryCreater = new EntryCreater();
        return entryCreater.createPluginEntry(pluginConfig, plugin,clazz);
    }
}
