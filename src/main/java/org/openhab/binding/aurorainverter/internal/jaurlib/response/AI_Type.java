package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefano on 29/11/14.
 */
public enum AI_Type {

    Wind_Version('W'),
    Photovoltaic_Version('N'),
    Dummy_inverter_type('X');

    final char value;

    public char get() {
        return value;
    }

    AI_Type(char val) {
        value = val;
    }

    private static Map<Character, AI_Type> mapVal2Enum = createMap();

    private static Map<Character, AI_Type> createMap() {

        Map<Character, AI_Type> result = new HashMap<Character, AI_Type>();
        for (AI_Type e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    public static AI_Type fromCode(char code) {

        return mapVal2Enum.get(code);
    }

}
