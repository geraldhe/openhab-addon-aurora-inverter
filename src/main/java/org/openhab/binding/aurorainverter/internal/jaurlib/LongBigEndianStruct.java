package org.openhab.binding.aurorainverter.internal.jaurlib;

import java.nio.ByteOrder;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_Struct;

/**
 * Created by sbrega on 10/12/2014.
 */

public class LongBigEndianStruct extends MB_Struct {

    public Unsigned32 val;

    public LongBigEndianStruct(long aLong) {
        this();
        this.val.set(aLong);
    }

    public LongBigEndianStruct() {
        val = new Unsigned32();
    }

    // sembra che i valori misurati dall'inverter siano in BIG_ENDIAN a differenza del CRC
    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.BIG_ENDIAN;
    }
}