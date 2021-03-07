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
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;

/**
 * @author Stefano Brega (11/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class ARespCumulatedEnergy extends AuroraResponse {
    public void setType(AuroraCumEnergyEnum partial) {
        setParam1((char) partial.get());
    }

    @Nullable
    public Long get() {
        try {
            return getLongParam();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return description.isEmpty() ? super.toString() : description + ": " + get();
    }

    @Override
    @Nullable
    public String getValue() {
        @Nullable
        Long val = get();

        return (val == null) ? "no value!" : Long.toString(val);
    }
}
