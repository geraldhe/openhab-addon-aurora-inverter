package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import javolution.io.Struct;

/**
 * Created by stefano on 22/11/14.
 */
public class MB_Struct extends Struct {

    @Override
    public boolean isPacked() {
        return true;
    }

    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }

    public byte[] toByteArray() {
        byte[] result = null;
        try {
            OutputStream aOutputStream = new ByteArrayOutputStream();
            write(aOutputStream);
            result = ((ByteArrayOutputStream) aOutputStream).toByteArray();
        } catch (IOException e) {
            // result = null;
        }
        return result;
    }

}