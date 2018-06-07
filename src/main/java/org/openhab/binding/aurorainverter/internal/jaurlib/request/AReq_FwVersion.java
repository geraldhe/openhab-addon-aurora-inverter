package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_code;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseBuilder;

/**
 * Created by stefano on 07/12/14.
 */
public class AReq_FwVersion extends AuroraRequest {

    @Override
    public AuroraResponse create(AuroraResponseBuilder inverter) {
        return inverter.createResponse(this);
    }

    public AReq_FwVersion(MB_code code) {
        super(code);

    }
}
