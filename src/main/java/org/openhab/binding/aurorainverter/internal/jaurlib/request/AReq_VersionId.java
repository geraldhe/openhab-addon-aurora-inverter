package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_code;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseBuilder;

/**
 * Created by stefano on 22/11/14.
 */
public class AReq_VersionId extends AuroraRequest {

    @Override
    public AuroraResponse create(AuroraResponseBuilder inverter) {
        return inverter.createResponse(this);
    }

    public AReq_VersionId(MB_code code) {
        super(code);
        setParam1('.');

    }
}
