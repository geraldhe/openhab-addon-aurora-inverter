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
package org.openhab.binding.aurorainverter.internal.jaurlib.request;

/**
 * @author Stefano Brega (19/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public enum AuroraRequestCodeEnum {

    GETSTATE(50),
    GETPRODUCTNUMBER(52),
    GETSERIALNUMBER(63),
    GETVERSIONID(58),
    GETDSP(59),
    GETACTUALTIME(70),
    GETFWVERSION(72),
    GETSYSTEMCONFIG(77),
    GETACCUMULATEDENERGY(78),
    GETMFGDATE(65),
    GETTIMECOUNTER(80),
    GETLASTALARMS(86);

    public final int value;

    AuroraRequestCodeEnum(int val) {
        value = val;
    }
}
