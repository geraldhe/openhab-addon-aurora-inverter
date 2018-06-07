package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_ADU_Response;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_PDU;

/**
 * Created by sbrega on 27/11/2014.
 */
public class AuroraResponsePacket extends MB_ADU_Response {
    public AuroraResponsePacket(MB_PDU pdu) {
        super(pdu);
    }
}
