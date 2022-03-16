package com.chbcraft.internals.components;

import com.chbcraft.internals.components.entries.PluginEntry;

import java.util.concurrent.ConcurrentHashMap;

public class PluginManager {
    private final ConcurrentHashMap<String, PluginEntry> pluginMap;
    private int pluginNumber = 0;
    PluginManager(){
        pluginMap = new ConcurrentHashMap<>();
    }
    /**
     * 添加一个插件进入管理类
     * @param entry 插件总类
     */
    public void addPluginEntry(PluginEntry entry,long startTime){
        String pluginName = entry.getPluginName();
        if(pluginMap.containsKey(pluginName))
            MessageBox.getLogger().broadcastPlugin(pluginName);
        else{
            pluginMap.put(pluginName,entry);
            this.pluginNumber++;
            MessageBox.getLogger().broadcastPlugin(pluginName,startTime);
        }
    }
    int getPluginNumber(){
        return this.pluginNumber;
    }

    /**
     * 根据Key获得插件类
     * @param pluginName key
     * @return 返回插件类
     */
    public PluginEntry getPluginEntry(String pluginName){
        return pluginMap.get(pluginName);
    }
}
