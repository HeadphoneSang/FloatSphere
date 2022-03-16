package com.chbcraft.internals.components;

import com.chbcraft.internals.classloader.PluginLoader;
import com.chbcraft.internals.components.entries.PluginEntry;
import com.chbcraft.plugin.CustomPlugin;

import java.io.File;

public abstract class PluginsProxy {
    protected final PluginLoader loader;
    protected String PLUGIN_PATH;
    protected static final PluginManager manager;
    protected final MessageBox logger;
    static{
        manager = new PluginManager();
    }
    public PluginsProxy(String path){
        PLUGIN_PATH = new File(PluginsProxy.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()).getParentFile().getParentFile().getAbsolutePath().toString()+"\\"+path;
        loader = new PluginLoader(PLUGIN_PATH);
        logger = MessageBox.getLogger();
    }
    protected abstract PluginEntry loadPlugin(String pluginName);
    protected abstract PluginEntry loadPlugin(File targetFile);

    /**
     * 将所有插件加载到内存中
     * @return 返回是否加载成功
     */
    protected int loadPlugins() {
        File file = new File(PLUGIN_PATH);
        File[] files;
        boolean flag = true;
        if(!file.exists())
            flag = file.mkdirs();
        files = file.listFiles();
        if(files!=null&&flag){
            File temp;
            for (File value : files) {
                temp = value;
                logger.broadcastPlugin(temp.getName());//xiugai
                long start = System.currentTimeMillis();
                if (temp.getName().endsWith(".jar"))
                    manager.addPluginEntry(loadPlugin(temp),start);
            }
        }
        return manager.getPluginNumber();
    }
    public abstract void enablePlugins();
    public abstract boolean reloadPlugins();
    protected abstract boolean unloadPlugin();
    public abstract CustomPlugin getPlugin(String pluginName);
    public abstract boolean reloadPlugin(String pluginName);
}
