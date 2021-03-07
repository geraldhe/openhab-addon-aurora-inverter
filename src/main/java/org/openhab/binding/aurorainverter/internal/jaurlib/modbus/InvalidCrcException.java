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
package org.openhab.binding.aurorainverter.internal.jaurlib.modbus;

/**
 * @author Gerald Heilmann (08/06/18) - Initial contribution
 */
public class InvalidCrcException extends Exception {

    private static final long serialVersionUID = 1981668604634492167L;

    public InvalidCrcException(String msg) {
        super(msg);
    }
}
