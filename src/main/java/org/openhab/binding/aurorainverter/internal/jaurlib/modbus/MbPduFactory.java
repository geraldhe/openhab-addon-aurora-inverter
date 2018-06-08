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

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public abstract class MbPduFactory {

    protected abstract MbPdu create(MbCode code);

    public MbPdu read(InputStream inputStream) throws IOException {
        final MbCode code = new MbCode();
        code.read(inputStream);

        MbPdu request = create(code);
        if (request == null) {
            throw new IOException("No PDU found for code: " + code.getValue());
        }

        request.data.read(inputStream);
        return request;
    }
}
