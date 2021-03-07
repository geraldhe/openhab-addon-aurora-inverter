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
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Stefano Brega (10/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespTimeCounter extends AuroraResponse {

    public void set(long timeCounter) {
        setLongParam(timeCounter);
    }

    public Long get() {
        try {
            return getLongParam();
        } catch (IOException e) {
            logger.error("IOException: {} {}", e.getMessage(), e.getStackTrace().toString());
            return null;
        }
    }

    @Override
    public String toString() {
        return description.isEmpty() ? super.toString()
                : description + ": " + String.valueOf(TimeUnit.MILLISECONDS.toDays(get() * 1000));
    }

    @Override
    public String getValue() {
        return String.valueOf(get());
    }
}
