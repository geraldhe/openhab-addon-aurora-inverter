package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_code;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseBuilder;

/**
 * Created by sbrega on 02/12/2014.
 */
public class AReq_DspData extends AuroraRequest {

    @Override
    public AuroraResponse create(AuroraResponseBuilder factory) {
        return factory.createResponse(this);
    }

    public AReq_DspData(MB_code code) {
        super(code);

    }

    public AuroraDspRequestEnum getType() {
        return AuroraDspRequestEnum.fromCode(getParam1());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getType();
    }
}
