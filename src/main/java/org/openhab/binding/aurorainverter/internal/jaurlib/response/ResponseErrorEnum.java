package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbrega on 27/11/2014.
 */

public enum ResponseErrorEnum {
    NONE(0),
    CRC(1),
    TIMEOUT(2),
    UNKNOWN(3);

    final int value;

    ResponseErrorEnum(int val) {
        value = val;
    }

    private static Map<Integer, ResponseErrorEnum> mapVal2Enum = createMap();

    private static Map<Integer, ResponseErrorEnum> createMap() {

        Map<Integer, ResponseErrorEnum> result = new HashMap<Integer, ResponseErrorEnum>();
        for (ResponseErrorEnum e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    public final int get() {
        return value;
    }

    public static ResponseErrorEnum fromCode(Integer code) {
        return mapVal2Enum.get(code);
    }
}
