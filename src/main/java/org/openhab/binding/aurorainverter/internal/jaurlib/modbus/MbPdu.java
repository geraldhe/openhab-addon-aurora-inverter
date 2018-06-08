/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class MbPdu extends MbStruct {
    public MbCode code;
    public MbData data;

    public MbPdu() {
    }

    protected void setCode(MbCode c) {
        code = c;
    }

    @Override
    public int read(InputStream inputStream) throws IOException {
        int nbytes = code.read(inputStream);
        nbytes += data.read(inputStream);
        return nbytes;
    }

    @Override
    public void write(OutputStream output) throws IOException {
        code.write(output);
        data.write(output);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "code=" + code + ", data=" + data + '}';
    }
}
