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
package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespNone;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;

/**
 * @author Stefano Brega (27/12/15) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class InvCmdCumEnergy extends InverterCommand {
    public AuroraCumEnergyEnum period;

    public InvCmdCumEnergy(int addressParameter, AuroraCumEnergyEnum aPeriod) {
        super(addressParameter);
        period = aPeriod;
    }

    @Override
    public AuroraResponse execute(AuroraDriver auroraDriver) {
        AuroraResponse auroraResponse = new ARespNone();
        try {
            auroraResponse = auroraDriver.acquireCumulatedEnergy(address, period);
        } catch (Exception e) {
            logger.error("bad response: {}, {}", auroraResponse, e.getMessage());
        }

        return auroraResponse;
    }
}
