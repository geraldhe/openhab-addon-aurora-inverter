package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefano on 29/11/14.
 */
public enum AI_TransformerType {

    Transformer_Version('T'),
    Transformerless_Version('N'),
    Transformer_HF_version('t'),
    Dummy_transformer_type('X');

    final char value;

    public char get() {
        return value;
    }

    AI_TransformerType(char val) {
        value = val;
    }

    private static Map<Character, AI_TransformerType> mapVal2Enum = createMap();

    private static Map<Character, AI_TransformerType> createMap() {

        Map<Character, AI_TransformerType> result = new HashMap<Character, AI_TransformerType>();
        for (AI_TransformerType e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    public static AI_TransformerType fromCode(char code) {

        return mapVal2Enum.get(code);
    }

}
