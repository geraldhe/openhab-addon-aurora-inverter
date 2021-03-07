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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Stefano Brega (11/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class ARespActualTime extends AuroraResponse {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static long msecBase = 0;

    @Override
    @Nullable
    public String getValue() {
        @Nullable
        Long val = get();

        return val == null ? null : Long.toString(val);
    }

    public ARespActualTime() {
        super();
        try {
            msecBase = (long) ((dateFormat.parse("01/01/2000").getTime()) / 1000.0);
        } catch (ParseException e) {
            logger.error("implementation error - cant parse value {}", e.getMessage());
        }
    }

    public void set(long time) {
        setLongParam(time);
    }

    @Nullable
    public Long get() {
        try {
            return getLongParam();
        } catch (IOException e) {
            logger.error("IOException: {}", e.getMessage());
            return null;
        }
    }

    @Nullable
    public Date getDate() {
        @Nullable
        Long val = get();
        return val == null ? null : new Date(val * 1000 + msecBase * 1000);
    }

    @Override
    public String toString() {
        long msecNow = (long) (new Date().getTime() / 1000.0);

        @Nullable
        Long val = get();

        if (val == null) {
            return "no value!";
        } else {
            return description.isEmpty() ? super.toString()
                    : description + ": " + getDate() + ", difference (msec): " + (msecNow - val - msecBase);
        }
    }
}
