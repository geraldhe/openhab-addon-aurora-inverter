/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stefano Brega (23/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public abstract class InverterCommand {
    protected Logger logger = LoggerFactory.getLogger(InverterCommand.class);

    protected final int address;

    public InverterCommand(int addressParameter) {
        address = addressParameter;
    }

    public abstract AuroraResponse execute(AuroraDriver auroraDriver);
}
