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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Stefano Brega (11/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespActualTime extends AuroraResponse {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static long msecBase = 0;

    @Override
    public String getValue() {
        return Long.toString(get());
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

    public Long get() {
        try {
            return getLongParam();
        } catch (IOException e) {
            logger.error("IOException: {}", e.getMessage());
            return null;
        }
    }

    public Date getDate() {
        Date result = new Date(get() * 1000 + msecBase * 1000);
        return result;
    }

    @Override
    public String toString() {
        long msecNow = (long) (new Date().getTime() / 1000.0);
        return description.isEmpty() ? super.toString()
                : description + ": " + getDate() + ", difference (msec): " + (msecNow - get() - msecBase);
    }
}
