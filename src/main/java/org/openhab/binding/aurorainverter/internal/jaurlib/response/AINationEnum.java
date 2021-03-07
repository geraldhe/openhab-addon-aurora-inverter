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
public enum AINationEnum {
    USA_UL1741('A'),
    Germany_VDE0126('E'),
    Spain_DR_1663_2000('S'),
    Italy_ENEL_DK_5950('I'),
    UK_UK_G83('U'),
    Australia_AS_4777('K'),
    France_VDE_French_Model('F');

    final char value;

    AINationEnum(char val) {
        value = val;
    }

    private static Map<Character, AINationEnum> mapVal2Enum = createMap();

    private static Map<Character, AINationEnum> createMap() {
        Map<Character, AINationEnum> result = new HashMap<Character, AINationEnum>();
        for (AINationEnum e : values()) {
            result.put(e.get(), e);
        }

        return Collections.unmodifiableMap(result);
    }

    public char get() {
        return value;
    }

    public static AINationEnum fromCode(char code) {
        return mapVal2Enum.get(code);
    }
}
