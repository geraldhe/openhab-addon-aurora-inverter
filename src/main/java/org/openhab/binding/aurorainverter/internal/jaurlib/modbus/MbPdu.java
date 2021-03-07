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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class MbPdu extends MbStruct {
    public MbCode code;
    public MbData data;

    public MbPdu() {
        code = new MbCode();
        data = new MbData();
    }

    protected void setCode(MbCode c) {
        code = c;
    }

    @Override
    public int read(@Nullable InputStream inputStream) throws IOException {
        int nbytes = code.read(inputStream);
        nbytes += data.read(inputStream);
        return nbytes;
    }

    @Override
    public void write(@Nullable OutputStream output) throws IOException {
        code.write(output);
        data.write(output);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "code=" + code + ", data=" + data + '}';
    }
}
