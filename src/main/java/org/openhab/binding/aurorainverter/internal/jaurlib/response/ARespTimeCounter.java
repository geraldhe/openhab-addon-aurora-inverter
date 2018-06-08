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
