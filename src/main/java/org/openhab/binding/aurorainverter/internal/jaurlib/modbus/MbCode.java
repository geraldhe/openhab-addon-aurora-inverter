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
package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

import javolution.io.Struct;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class MbCode extends MbStruct {
    public Struct.Unsigned8 val = new Struct.Unsigned8();

    public MbCode(int value) {
        val.set((short) value);
    }

    public MbCode() {
        this(0);
    }

    public Integer getValue() {
        return (int) val.get();
    }
}
