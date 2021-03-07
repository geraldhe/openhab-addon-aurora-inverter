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
 * @author Stefano Brega (09/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespSysConfig extends AuroraResponse {
    public void setConfigCode(int code) {
        setParam1((char) code);
    }

    public int getConfigCode() {
        return getParam1();
    }

    @Override
    public String getValue() {
        return String.valueOf(getConfigCode());
    }
}
