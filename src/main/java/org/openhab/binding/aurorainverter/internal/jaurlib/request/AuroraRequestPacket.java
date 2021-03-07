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
package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbAddress;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbAduRequest;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPduFactory;

/**
 * @author Stefano Brega (26/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class AuroraRequestPacket extends MbAduRequest {

    static final MbPduFactory FACTORY = new AuroraRequestFactory();

    public AuroraRequestPacket(MbAddress mb_address, MbPdu pdu) {
        super(FACTORY, mb_address, pdu);
    }

    public AuroraRequestPacket() {
        super(FACTORY);
    }
}
