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
package org.openhab.binding.aurorainverter.internal.jaurlib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author Stefano Brega (07/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class MyFormatter extends SimpleFormatter {
    @Override
    public synchronized String format(LogRecord record) {
        Date date = new Date(record.getMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y.M.d HH:mm:ss.SSS");

        String str = simpleDateFormat.format(date);
        StringBuilder builder = new StringBuilder();
        builder.append(str).append(" ").append(record.getThreadID()).append(" ").append(record.getLevel()).append(" ")
                .append(record.getSourceClassName()).append(" ").append(record.getSourceMethodName()).append(" \"")
                .append(record.getMessage()).append("\"\n");
        return builder.toString();
    }
}
