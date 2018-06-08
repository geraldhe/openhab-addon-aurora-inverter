/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stefano Brega (27/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
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
