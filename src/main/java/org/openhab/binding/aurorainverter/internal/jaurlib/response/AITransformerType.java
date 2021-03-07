/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
public enum AITransformerType {

    Transformer_Version('T'),
    Transformerless_Version('N'),
    Transformer_HF_version('t'),
    Dummy_transformer_type('X');

    final char value;

    public char get() {
        return value;
    }

    AITransformerType(char val) {
        value = val;
    }

    private static Map<Character, AITransformerType> mapVal2Enum = createMap();

    private static Map<Character, AITransformerType> createMap() {
        Map<Character, AITransformerType> result = new HashMap<Character, AITransformerType>();
        for (AITransformerType e : values()) {
            result.put(e.get(), e);
        }
        return Collections.unmodifiableMap(result);
    }

    public static AITransformerType fromCode(char code) {
        return mapVal2Enum.get(code);
    }
}
