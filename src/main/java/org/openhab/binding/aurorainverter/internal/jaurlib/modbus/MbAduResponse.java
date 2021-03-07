/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class MbAduResponse extends MbAdu {

    public MbAduResponse(MbPdu pdu) {
        this.pdu = pdu;
        this.crc = new MbCrc(pdu);
    }

    @Override
    public int read(InputStream in) throws IOException {
        crc = new MbCrc();
        int nbytes = pdu.read(in);
        nbytes += crc.read(in);

        try {
            checkCrc(crc);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        return nbytes;
    }

    @Override
    public void write(OutputStream out) throws IOException {
        pdu.write(out);
        crc.write(out);
    }

    @Override
    protected void checkCrc(MbCrc otherCrc) throws InvalidCrcException {
        MbCrc computedCrc = computeCrc();
        if (computedCrc.val.get() != otherCrc.val.get()) {
            throw new InvalidCrcException("CRC Error");
        }
    }

    @Override
    protected MbCrc computeCrc() {
        return new MbCrc(pdu);
    }
}
