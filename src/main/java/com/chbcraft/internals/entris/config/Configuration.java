package com.chbcraft.internals.entris.config;

import java.util.ArrayList;

public abstract class Configuration {
    protected ArrayList<Section> sections;
    protected Configuration(){
        sections = new ArrayList<>();
    }
    public abstract Object getValueByKey(String key);
    public abstract Section getSectionByKey(String key);
    public abstract Object getValueByKeys(String keys);
    public abstract void setValueByKey(String key,String value);
    public abstract void setValueByKeys(String key,String value);
    public abstract void addValueByKey(String key,Object value);
}
