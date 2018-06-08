/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class MbCrc extends MbStruct {

    public Unsigned16 val = new Unsigned16();

    public MbCrc(MbStruct msg) {
        this(msg.toByteArray());
    }

    public MbCrc(byte[] bytes) {
        val.set(compute(bytes));
    }

    public MbCrc() {
    }

    public MbCrc(MbStruct... structs) {
        byte[] result = structs[0].toByteArray();
        for (int i = 1; i < structs.length; i++) {
            byte[] curr = structs[i].toByteArray();
            byte[] temp = new byte[result.length + curr.length];
            System.arraycopy(result, 0, temp, 0, result.length);
            System.arraycopy(curr, 0, temp, result.length, curr.length);
            result = temp;
        }

        val.set(compute(result));
    }

    int compute(byte[] data) {
        int count = data.length;
        int offset = 0;

        int polynomial = 0x8408;
        int crc = 0xFFFF;
        if (count == 0) {
            return 0;
        }
        for (int i = 0; i < count; i++) {
            byte current = data[offset + i];
            for (int j = 0; j < 8; j++, current >>= 1) {
                if (((crc ^ current) & 0x1) > 0) {
                    crc = ((crc >> 1) ^ polynomial);
                } else {
                    crc >>= 1;
                }
                crc &= 0xFFFF;
            }
        }

        crc = ~crc;

        return (crc & 0xFFFF);
    }

    public int getValue() {
        return val.get();
    }
}
