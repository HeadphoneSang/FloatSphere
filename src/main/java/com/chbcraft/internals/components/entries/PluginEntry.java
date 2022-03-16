package com.chbcraft.internals.components.entries;

import com.chbcraft.internals.entris.config.Configuration;
import com.chbcraft.plugin.CustomPlugin;

import java.util.ArrayList;

public class PluginEntry {

    private Configuration pluginConfig = null;
    private CustomPlugin plugin = null;
    private ArrayList<String> clazz = null;

    PluginEntry(Configuration pluginConfig,CustomPlugin plugin,ArrayList<String> clazz){
        this.plugin = plugin;
        this.pluginConfig = pluginConfig;
        this.clazz = clazz;
    }

    public Configuration getPluginConfig() {
        return pluginConfig;
    }

    public void setPluginConfig(Configuration pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    public CustomPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(CustomPlugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<String> getClazz() {
        return clazz;
    }

    public void setClazz(ArrayList<String> clazz) {
        this.clazz = clazz;
    }

    public String getPluginName(){
        return (String) pluginConfig.getValueByKey("name");
    }
}
