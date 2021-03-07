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

/**
 * @author Stefano Brega (07/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespState extends AuroraResponse {

    public ARespState() {
        super();
    }

    public void setGlobalState(char val) {
        setSubCode(val);
    }

    public void setInverterState(char val) {
        setParam1(val);
    }

    public void setCh1DcDcState(char val) {
        setParam2(val);
    }

    public void setCh2DcDcState(char val) {
        setParam3(val);
    }

    public void setAlarmState(char val) {
        setParam4(val);
    }

    public char getGlobalState() {
        return getSubCode();
    }

    public char getInverterState() {
        return getParam1();
    }

    public char getCh1DcDcState() {
        return getParam2();
    }

    public char getCh2DcDcState() {
        return getParam3();
    }

    public char getAlarmState() {
        return getParam4();
    }

    @Override
    public String getValue() {
        return "" + getSubCode() + getParam1() + getParam2() + getParam3() + getParam4();
    }
}
