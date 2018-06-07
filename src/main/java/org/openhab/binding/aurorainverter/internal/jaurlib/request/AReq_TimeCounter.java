package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_code;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseBuilder;

/**
 * Created by sbrega on 10/12/2014.
 */
public class AReq_TimeCounter extends AuroraRequest {
    public AReq_TimeCounter(MB_code code) {
        super(code);
    }

    @Override
    public AuroraResponse create(AuroraResponseBuilder factory) {
        return factory.createResponse(this);
    }
}
