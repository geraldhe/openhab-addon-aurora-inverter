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
public class MbAduRequest extends MbAdu {

    MbAddress address = null;

    public MbAduRequest(MbPduFactory pduFactory) {
        this.pduFactory = pduFactory;
    }

    public MbAduRequest(MbPduFactory pduFactory, MbAddress mb_address, MbPdu mb_pdu) {
        this.pduFactory = pduFactory;
        this.address = mb_address;
        this.pdu = mb_pdu;
        this.crc = new MbCrc(address, pdu);

    }

    @Override
    public int read(InputStream in) throws IOException {
        address = new MbAddress(0);
        crc = new MbCrc();
        int nbytes = address.read(in);
        pdu = pduFactory.read(in);
        if (pdu == null) {
            throw new IOException("No Class created for input bytes: " + in);
        }

        nbytes += pdu.size();
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
        address.write(out);
        pdu.write(out);
        crc.write(out);
    }

    @Override
    protected MbCrc computeCrc() {
        MbCrc crc = new MbCrc(address, pdu);
        return crc;
    }
}
