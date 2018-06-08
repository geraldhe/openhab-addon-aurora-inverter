/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.io.IOException;

import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;

/**
 * @author Stefano Brega (11/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespCumulatedEnergy extends AuroraResponse {
    public void setType(AuroraCumEnergyEnum partial) {
        setParam1((char) partial.get());
    }

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
    public String getValue() {
        return Long.toString(get());
    }
}
