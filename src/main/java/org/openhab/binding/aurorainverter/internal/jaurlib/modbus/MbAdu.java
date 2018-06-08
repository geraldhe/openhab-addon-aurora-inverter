/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public abstract class MbAdu extends MbStruct {
    MbPdu pdu = null;
    MbCrc crc = null;

    MbPduFactory pduFactory = null;

    protected void checkCrc(MbCrc otherCrc) throws InvalidCrcException {
        if (computeCrc().getValue() != otherCrc.val.get()) {
            throw new InvalidCrcException("CRC Error");
        }
    }

    protected abstract MbCrc computeCrc();

    public MbPdu getPdu() {
        return pdu;
    }
}
