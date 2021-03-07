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
package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseBuilder;

/**
 * @author Stefano Brega (11/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class AReqCumulatedEnergy extends AuroraRequest {

    public AReqCumulatedEnergy(MbCode code) {
        super(code);
    }

    @Override
    public AuroraResponse create(AuroraResponseBuilder factory) {
        return factory.createResponse(this);
    }

    public AuroraCumEnergyEnum getType() {
        return AuroraCumEnergyEnum.fromCode(getParam1());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getType();
    }
}
