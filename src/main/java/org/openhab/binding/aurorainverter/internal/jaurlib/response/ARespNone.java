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
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

/**
 * @author Gerald Heilmann - Initial contribution
 */
public class ARespNone extends AuroraResponse {

    @Override
    public String getValue() {
        return "no value!";
    }

    public ARespNone() {
        super();
    }

    @Override
    public String toString() {
        return "empty response (insted of null)";
    }
}
