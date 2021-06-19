package com.lyanba.crm.utils;

import java.util.HashMap;
import java.util.Map;

public class HandleFlag {

    /*public static Map<String,Object> handleFlag(boolean flag){
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }*/

    public static Map<String, Object> success() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", 10000);
        return map;
    }

    public static Map<String, Object> fail() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", 10001);
        return map;
    }

    public static Map<String, Object> successObj(String key, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", 10000);
        map.put(key, obj);
        return map;
    }

    public static Map<String, Object> failObj(String key, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", 10001);
        map.put(key, obj);
        return map;
    }
}
