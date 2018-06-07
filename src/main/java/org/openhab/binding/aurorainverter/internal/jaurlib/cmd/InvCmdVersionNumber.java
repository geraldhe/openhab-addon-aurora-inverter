package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;

/**
 * Created by stefano on 27/12/15.
 */
public class InvCmdVersionNumber extends InverterCommand {

    public InvCmdVersionNumber(int aInverterAddress) {
        super(aInverterAddress);
    }

    @Override
    public AuroraResponse execute(AuroraDriver auroraDriver) {

        AuroraResponse auroraResponse = null;
        try {
            auroraResponse = auroraDriver.acquireVersionId(address);

        } catch (Exception e) {
            String errorString = e.getMessage();
            log.severe("Bad Response: " + auroraResponse + ", " + errorString);
        }

        return auroraResponse;

    }

}
