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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Stefano Brega (08/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespMFGdate extends AuroraResponse {
    public ARespMFGdate() {
        super();
    }

    public void setDate(Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR) - 2000;

        String strWeek = String.format("%02d", week);
        String strYear = String.format("%02d", year);
        setWeek(strWeek);
        setYear(strYear);
    }

    protected void setWeek(String week) {
        setParam1(week.charAt(0));
        setParam2(week.charAt(1));
    }

    protected void setYear(String year) {
        setParam3(year.charAt(0));
        setParam4(year.charAt(1));
    }

    protected String getYear() {
        return "" + getParam3() + getParam4();
    }

    protected String getWeek() {
        return "" + getParam1() + getParam2();
    }

    public Date get() {
        int year = Integer.parseInt(getYear()) + 2000;
        int week = Integer.parseInt(getWeek());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return description.isEmpty() ? super.toString() : description + ": " + getValue();
    }

    @Override
    public String getValue() {
        return new SimpleDateFormat("MMMMM yyyy").format(get());
    }
}
