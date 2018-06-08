/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib;

import java.nio.ByteOrder;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbStruct;

import javolution.io.Struct;

/**
 * @author Stefano Brega (06/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class FloatBigEndianStruct extends MbStruct {
    public Struct.Float32 val;

    public FloatBigEndianStruct(float aFloat) {
        this();
        this.val.set(aFloat);
    }

    public FloatBigEndianStruct() {
        val = new Float32();
    }

    // it seems that the values ​​measured by the inverter are in BIG_ENDIAN unlike the CRC
    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.BIG_ENDIAN;
    }
}
