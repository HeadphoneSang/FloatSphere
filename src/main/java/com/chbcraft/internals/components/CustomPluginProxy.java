package com.chbcraft.internals.components;

import com.chbcraft.internals.components.entries.PluginEntry;
import com.chbcraft.internals.components.utils.ConfigurationUtil;
import com.chbcraft.internals.entris.config.Configuration;
import com.chbcraft.plugin.CustomPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomPluginProxy extends PluginsProxy {
    CustomPluginProxy(){
        super("plugins");
    }
    @Override
    protected PluginEntry loadPlugin(File targetFile) {
        Class<?> clazz = null;
        PluginEntry pluginEntry;
        Object[] infos = null;
        Configuration config = null;
        try {
            infos = loader.loadJar(targetFile);
            config = (Configuration)infos[1];
            String mainClass = String.valueOf(config.getValueByKey("main"));
            clazz = loader.loadClass(mainClass);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(clazz!=null) {
            try {
                CustomPlugin plugin = (CustomPlugin)clazz.newInstance();
                ArrayList<String> allClass = ConfigurationUtil.castToList(infos[0]);
                pluginEntry = FloatSphere.createPluginEntry(config,plugin,allClass);
                return pluginEntry;
            } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
                MessageBox.getLogger().warn("The index of the main class is Wrong or the main class is not a derived class of the Plugin-Template!");
                e.printStackTrace();
            }
        }
        return null;
    }
    @Deprecated
    @Override
    protected PluginEntry loadPlugin(String pluginName) {
        File file = new File(PLUGIN_PATH+"\\"+pluginName);
        return loadPlugin(file);
    }

    public void enablePlugins(){
        int number = loadPlugins();
        logger.log("Complete loads all plugins\nSuccess Loaded "+number+" Plugin!");
    }
    @Override
    public boolean reloadPlugins() {
        return false;
    }

    @Override
    protected boolean unloadPlugin() {
        return false;
    }

    @Override
    public CustomPlugin getPlugin(String pluginName) {
        return manager.getPluginEntry(pluginName).getPlugin();
    }

    @Override
    public boolean reloadPlugin(String pluginName) {
        return false;
    }
}
