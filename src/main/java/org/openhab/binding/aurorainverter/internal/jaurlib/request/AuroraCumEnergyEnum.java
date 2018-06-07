package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbrega on 11/12/2014.
 */
public enum AuroraCumEnergyEnum {

    DAILY(0),
    WEEKLY(1),
    LAST7DAYS(2),
    MONTHLY(3),
    YEARLY(4),
    TOTAL(5),
    PARTIAL(6);

    public final int value;
    private static Map<Integer, AuroraCumEnergyEnum> mapVal2Enum = createMap();

    private static Map<Integer, AuroraCumEnergyEnum> createMap() {

        Map<Integer, AuroraCumEnergyEnum> result = new HashMap<Integer, AuroraCumEnergyEnum>();
        for (AuroraCumEnergyEnum e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    AuroraCumEnergyEnum(int val) {
        value = val;
    }

    public static AuroraCumEnergyEnum fromCode(int code) {

        return mapVal2Enum.get(code);
    }

    public int get() {
        return value;
    }

}
