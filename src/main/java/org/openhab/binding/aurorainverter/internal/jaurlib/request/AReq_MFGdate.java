package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_code;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseBuilder;

/**
 * Created by stefano on 08/12/14.
 */
public class AReq_MFGdate extends AuroraRequest {
    public AReq_MFGdate(MB_code code) {
        super(code);
    }

    @Override
    public AuroraResponse create(AuroraResponseBuilder inverter) {
        return inverter.createResponse(this);
    }

}
