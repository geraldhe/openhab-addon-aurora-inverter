/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;

/**
 * @author Stefano Brega (27/12/15) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class InvCmdMfgDate extends InverterCommand {
    public InvCmdMfgDate(int aInverterAddress) {
        super(aInverterAddress);
    }

    @Override
    public AuroraResponse execute(AuroraDriver auroraDriver) {
        AuroraResponse auroraResponse = null;
        try {
            auroraResponse = auroraDriver.acquireMFGdate(address);
        } catch (Exception e) {
            logger.error("bad response: {}, {}", auroraResponse, e.getMessage());
        }

        return auroraResponse;
    }
}
