/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbAduResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;

/**
 * @author Stefano Brega (27/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class AuroraResponsePacket extends MbAduResponse {
    public AuroraResponsePacket(MbPdu pdu) {
        super(pdu);
    }
}
