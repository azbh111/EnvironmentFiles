package com.github.azbh111.ideaplugin.environmentvariable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyLinkedHashMap extends LinkedHashMap<String, String> {

    public MyLinkedHashMap(Map<String, String> myEnvs) {
        super(myEnvs);
    }
    public MyLinkedHashMap() {
    }
}
