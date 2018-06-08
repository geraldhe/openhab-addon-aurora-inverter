/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
