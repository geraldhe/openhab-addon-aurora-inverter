/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
