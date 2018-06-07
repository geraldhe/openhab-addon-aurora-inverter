package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;

/**
 * Created by stefano on 27/12/15.
 */
public class InvCmdCumEnergy extends InverterCommand {
    public AuroraCumEnergyEnum period;

    public InvCmdCumEnergy(int addressParameter, AuroraCumEnergyEnum aPeriod) {
        super(addressParameter);
        period = aPeriod;
    }

    @Override
    public AuroraResponse execute(AuroraDriver auroraDriver) {

        AuroraResponse auroraResponse = null;
        try {
            auroraResponse = auroraDriver.acquireCumulatedEnergy(address, period);

        } catch (Exception e) {
            log.severe("Bad Response: " + auroraResponse);
        }

        return auroraResponse;
    }

}
