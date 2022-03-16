package com.chbcraft.main;
import com.chbcraft.internals.components.FloatSphere;
import com.chbcraft.internals.components.PluginsProxy;
import com.chbcraft.plugin.CustomPlugin;

public class Main {
    public static void main(String[] args)  {
        PluginsProxy proxy = FloatSphere.createPluginProxy();
        long start = System.currentTimeMillis();
        proxy.enablePlugins();
        CustomPlugin plugin = proxy.getPlugin("Test");
        plugin.onEnable();
        System.out.println(System.currentTimeMillis()-start+"ms");
    }
}
