package com.chbcraft.internals.components.utils;

import com.chbcraft.internals.entris.config.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ConfigurationUtil {
    private ConfigurationUtil(){};

    /**
     * 转换Object转换为Map返回
     * @param obj 目标对象
     * @return 返回转换结果
     */
    @SuppressWarnings("unchecked")
    public static Map<String,Object> castToMap(Object obj){
        Map<String,Object> returnMap = null;
        if(obj instanceof Map)
            returnMap = (Map<String, Object>) obj;
        return returnMap;
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<String> castToList(Object obj){
        ArrayList<String> list = null;
        if(obj instanceof ArrayList)
            list = (ArrayList<String>) obj;
        return list;
    }
}
