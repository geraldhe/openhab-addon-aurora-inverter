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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javolution.io.Struct;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
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

    public byte @Nullable [] toByteArray() {
        byte[] result = null;
        try {
            OutputStream aOutputStream = new ByteArrayOutputStream();
            write(aOutputStream);
            result = ((ByteArrayOutputStream) aOutputStream).toByteArray();
        } catch (IOException e) {
            logger.error("implementation error: {} | {}", e.getMessage(), e.getStackTrace().toString());
        }
        return result;
    }
}
