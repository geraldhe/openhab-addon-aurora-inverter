/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javolution.io.Struct;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class MbStruct extends Struct {

    protected Logger logger = LoggerFactory.getLogger(MbStruct.class);

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
            logger.error("implementation error: {} | {}", e.getMessage(), e.getStackTrace().toString());
            // result = null;
        }
        return result;
    }

}
