/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stefano Brega (29/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public enum AIType {

    Wind_Version('W'),
    Photovoltaic_Version('N'),
    Dummy_inverter_type('X');

    final char value;

    public char get() {
        return value;
    }

    AIType(char val) {
        value = val;
    }

    private static Map<Character, AIType> mapVal2Enum = createMap();

    private static Map<Character, AIType> createMap() {
        Map<Character, AIType> result = new HashMap<Character, AIType>();
        for (AIType e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    public static AIType fromCode(char code) {
        return mapVal2Enum.get(code);
    }
}
