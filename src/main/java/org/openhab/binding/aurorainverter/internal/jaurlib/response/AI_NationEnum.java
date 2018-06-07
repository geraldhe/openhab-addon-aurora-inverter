package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefano on 29/11/14.
 */
public enum AI_NationEnum {

    USA_UL1741('A'),
    Germany_VDE0126('E'),
    Spain_DR_1663_2000('S'),
    Italy_ENEL_DK_5950('I'),
    UK_UK_G83('U'),
    Australia_AS_4777('K'),
    France_VDE_French_Model('F');

    final char value;

    AI_NationEnum(char val) {
        value = val;
    }

    private static Map<Character, AI_NationEnum> mapVal2Enum = createMap();

    private static Map<Character, AI_NationEnum> createMap() {

        Map<Character, AI_NationEnum> result = new HashMap<Character, AI_NationEnum>();
        for (AI_NationEnum e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    public char get() {
        return value;
    }

    public static AI_NationEnum fromCode(char code) {

        return mapVal2Enum.get(code);
    }

}
