package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import java.util.logging.Logger;

import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;

/**
 * Created by stefano on 23/12/14.
 */
public abstract class InverterCommand {

    protected Logger log = Logger.getLogger(getClass().getSimpleName());

    protected final int address;

    public InverterCommand(int addressParameter) {
        address = addressParameter;
    }

    public abstract AuroraResponse execute(AuroraDriver auroraDriver);
}
