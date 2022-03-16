package com.chbcraft.internals.components;

import com.chbcraft.internals.entris.config.ConfigSection;
import com.chbcraft.internals.entris.config.Configuration;
import com.chbcraft.internals.entris.config.Section;
import java.util.List;

public class PluginConfiguration extends Configuration {
    PluginConfiguration(){}
    @Override
    public Object getValueByKey(String key) {
        Object value;
        value = getSectionByKey(key).getValue();
        return value;
    }

    /**
     * 通过键值获得Section
     * @param key 键值
     * @return 对应的Section
     */
    @Override
    public Section getSectionByKey(String key) {
        Section section0 = null;
        for(Section section : sections){
            if(section.getKey().equals(key))
                section0 = section;
        }
        return section0;
    }

    /**
     * 通过嵌套键值查找属性
     * @param keys 嵌套键值
     * @return 返回对应的属性
     */
    @Override
    public Object getValueByKeys(String keys) {
        if(!keys.contains("/"))
            return null;
        String[] allKey = keys.split("/",2);
        Section section = getSectionByKey(allKey[0]);
        Object value = section.getValue();
        if(value instanceof List)
            return section.getValueByKey(allKey[1]);
        return value;
    }

    @Override
    public void setValueByKey(String key, String value) {

    }

    @Override
    public void setValueByKeys(String key, String value) {

    }

    @Override
    public void addValueByKey(String key, Object value) {
        Section section = new ConfigSection(key,value);
        sections.add(section);
    }
}
