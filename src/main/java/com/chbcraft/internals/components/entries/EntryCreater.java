package com.chbcraft.internals.components.entries;

import com.chbcraft.internals.entris.config.Configuration;
import com.chbcraft.plugin.CustomPlugin;

import java.util.ArrayList;

public class EntryCreater {
    public PluginEntry createPluginEntry(Configuration pluginConfig, CustomPlugin plugin, ArrayList<String> clazz){
        return new PluginEntry(pluginConfig,plugin,clazz);
    }
}
