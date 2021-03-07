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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.aurorainverter.internal.jaurlib.FloatBigEndianStruct;
import org.openhab.binding.aurorainverter.internal.jaurlib.LongBigEndianStruct;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbData;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbStruct;

import javolution.io.Struct;

/**
 * @author Stefano Brega - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public abstract class AuroraResponse extends MbPdu {

    protected String description = "";
    protected ResponseErrorEnum errorCode = ResponseErrorEnum.NONE;

    @Nullable
    public abstract String getValue();

    class DataInternal extends MbData {
        public Unsigned8 subCode;
        BytesInternal byteArray;

        DataInternal() {
            subCode = new Unsigned8();
            byteArray = new BytesInternal();
        }

        @Override
        public int read(@Nullable InputStream in) throws IOException {
            return super.read(in) + byteArray.read(in);
        }

        @Override
        public void write(@Nullable OutputStream out) throws IOException {
            super.write(out);
            byteArray.write(out);
        }

        @Override
        public String toString() {
            return super.toString() + ((errorCode != ResponseErrorEnum.TIMEOUT) ? byteArray.toString()
                    : "Invalid Response, error: " + errorCode);
        }
    }

    class BytesInternal extends MbData {
        final Unsigned8[] bytes;

        BytesInternal() {
            bytes = array(new Struct.Unsigned8[4]);
        }
    }

    public AuroraResponse(MbCode code) {
        this.code = code;
        this.data = new DataInternal();
    }

    public AuroraResponse() {
        this.code = new MbCode(0);
        this.data = new DataInternal();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubCode(char val) {
        ((DataInternal) data).subCode.set((short) val);
    }

    public void setParam1(char val) {
        ((DataInternal) data).byteArray.bytes[0].set((short) val);
    }

    public void setParam2(char val) {
        ((DataInternal) data).byteArray.bytes[1].set((short) val);
    }

    public void setParam3(char val) {
        ((DataInternal) data).byteArray.bytes[2].set((short) val);
    }

    public void setParam4(char val) {
        ((DataInternal) data).byteArray.bytes[3].set((short) val);
    }

    public void setFloatParam(float val) {
        MbStruct struct = new FloatBigEndianStruct(val);
        try {
            ByteArrayInputStream iStream = new ByteArrayInputStream(struct.toByteArray());
            ((DataInternal) this.data).byteArray.read(iStream);
        } catch (IOException e) {
            logger.error("IOException - {} | {}", e.getMessage(), e.getStackTrace().toString());
        }
    }

    public float getFloatParam() {
        float result = (float) -9999.9;
        FloatBigEndianStruct struct = new FloatBigEndianStruct(0);
        try {
            ByteArrayInputStream iStream = new ByteArrayInputStream(((DataInternal) this.data).byteArray.toByteArray());
            struct.read(iStream);
            result = struct.val.get();
        } catch (IOException e) {
            logger.error("IOException - {} | {}", e.getMessage(), e.getStackTrace().toString());
        }
        return result;
    }

    public void setLongParam(long val) {
        MbStruct struct = new LongBigEndianStruct(val);
        try {
            ByteArrayInputStream iStream = new ByteArrayInputStream(struct.toByteArray());
            ((DataInternal) this.data).byteArray.read(iStream);
        } catch (IOException e) {
            logger.error("IOException - {} | {}", e.getMessage(), e.getStackTrace().toString());
        }
    }

    public long getLongParam() throws IOException {
        LongBigEndianStruct struct = new LongBigEndianStruct(0);
        ByteArrayInputStream iStream = new ByteArrayInputStream(((DataInternal) this.data).byteArray.toByteArray());
        struct.read(iStream);
        return struct.val.get();
    }

    public char getParam1() {
        return (char) ((DataInternal) data).byteArray.bytes[0].get();
    }

    public char getParam2() {
        return (char) ((DataInternal) data).byteArray.bytes[1].get();
    }

    public char getParam3() {
        return (char) ((DataInternal) data).byteArray.bytes[2].get();
    }

    public char getParam4() {
        return (char) ((DataInternal) data).byteArray.bytes[3].get();
    }

    public char getSubCode() {
        return (char) ((DataInternal) data).subCode.get();
    }

    public ResponseErrorEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ResponseErrorEnum code) {
        errorCode = code;
    }
}
