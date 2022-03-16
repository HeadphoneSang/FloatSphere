package com.chbcraft.internals.classloader;

import com.chbcraft.exception.ClassSameNameException;
import com.chbcraft.internals.components.FloatSphere;
import com.chbcraft.internals.components.MessageBox;
import com.chbcraft.internals.entris.config.Configuration;
import com.chbcraft.main.Main;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class PluginLoader extends ClassLoader{
    /**
     *  插件文件夹
     */
    private final String pluginPath;
    /**
     * 类名包括类字节码
     */
    private static final ConcurrentHashMap<String,byte[]> classMap;
    static{
        classMap = new ConcurrentHashMap<>();
    }

    /**
     * 构造器,初始化目录位置
     * @param pluginPath 插件存放目录
     */
    public PluginLoader(String pluginPath){
        this.pluginPath = pluginPath;
        if(!pluginPath.endsWith("\\"))
            pluginPath+="\\";
    }
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(classMap.containsKey(name)){
            byte[] classByte = classMap.get(name);
            return defineClass(name,classByte, 0, classByte.length);
        }
        try{
            super.findClass(name);
        }catch (ClassNotFoundException c){
            MessageBox.getLogger().warn("There may be something Wrong with your configuration file,Please check your [plugin.yml]'s main info in YAML!\nThis is your info -》 main: "+name);
            c.printStackTrace();
        }
        return null;
    }
    /**
     * @param className class文件
     */
    @Deprecated
    private void loadClazz(String className){
        if(!className.endsWith("class"))
            return;
        File classFile = new File(pluginPath+"\\"+className);
        if(classFile.exists()){
            try {
                addClass(classFile.getName().replace("/",".").replace(".class",""),Files.readAllBytes(Paths.get(classFile.getAbsolutePath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 导入插件包Jar
     * @param jarName Jar包的名字
     * @return 返回Object[]数组,第一个元素为类全限定名集合,第二个元素为配置文件
     * @throws IOException IO错误
     */
    public Object[] loadJar(String jarName) throws IOException {
        File targetFile = new File(pluginPath+"\\"+jarName);
        Object[] returnPlug = null;
        if(!targetFile.exists())
            MessageBox.getLogger().error(jarName.substring(jarName.lastIndexOf(".")));
        if(jarName.endsWith(".jar"))
            returnPlug = operateJar(targetFile,jarName);
        return returnPlug;
    }
    public Object[] loadJar(File targetFile) throws IOException {
        Object[] returnPlug = null;
        String jarName = targetFile.getName();
        if(!targetFile.exists())
            MessageBox.getLogger().error(jarName.substring(jarName.lastIndexOf(".")));
        if(jarName.endsWith(".jar"))
            returnPlug = operateJar(targetFile,jarName);
        return returnPlug;
    }

    /**
     * 扫描目标Jar包,并将class文件字节码载入内存,配置文件载入内存
     * @param targetJar 目标Jar文件
     * @param jarName jar包的名子
     * @return {@link Object[]} 返回一个对象数组,第一个为本插件所有的包的名称,第二个为插件的基础配置信息配置文件
     * @throws IOException IO错误
     */
    private Object[] operateJar(File targetJar, String jarName) throws IOException {
        Object[] resultEntry = null;
        resultEntry = new Object[2];
        ArrayList<String> allClazz = new ArrayList<>();
        JarInputStream jarInput = null;
        BufferedInputStream classInput = null;
        try {
            JarFile jarFile = new JarFile(targetJar);
            jarInput = new JarInputStream(new FileInputStream(targetJar));
            JarEntry nowEntry;
            while((nowEntry = jarInput.getNextJarEntry())!=null){
                String entryName = nowEntry.getName().replace("/",".");
                if(entryName.endsWith(".class")) {
                    classInput = new BufferedInputStream(jarFile.getInputStream(nowEntry));
                    operateClass(classInput,entryName.replace(".class",""));
                    allClazz.add(entryName);
                }
                else if(entryName.equalsIgnoreCase("plugin.yml")){
                    classInput = new BufferedInputStream(jarFile.getInputStream(nowEntry));
                    resultEntry[1] = operateYml(classInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(jarInput!=null)
                jarInput.close();
            if(classInput!=null)
                classInput.close();
            resultEntry[0] = allClazz;
        }
        return resultEntry;
    }

    /**
     * 读取配置文件并转化为内存对象
     * @param input 文件输入流
     * @return {@link Configuration} 返回结果配置文件对象
     */
    private Configuration operateYml(BufferedInputStream input){
        Yaml yaml = new Yaml();
        Object map = yaml.load(input);
        return FloatSphere.createConfig(map);
    }
    /**
     * 操作从Jar包中找到的所有的class文件
     * @param classInput 文件输入流
     * @param entryName class文件的全限定名
     * @throws IOException IO错误
     */
    private void operateClass(BufferedInputStream classInput,String entryName) throws IOException {
        byte[] buff = new byte[1024];
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int readNum = 0;
        while((readNum = classInput.read(buff))!=-1)
            byteBuff.write(buff,0,readNum);
        addClass(entryName,byteBuff.toByteArray());
        byteBuff.close();
    }
    /**
     * 添加不同的class到表中
     * @param className 类名包名
     * @param classByte 类的字节信息
     */
    private void addClass(String className,byte[] classByte){
        if(classMap.containsKey(className)){
            throw new ClassSameNameException(className);
        }
        classMap.put(className,classByte);
    }
}
