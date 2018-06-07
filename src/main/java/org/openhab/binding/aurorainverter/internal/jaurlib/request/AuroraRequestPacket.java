package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_ADU_Request;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_PDU;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_address;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.PDUFactory;

/**
 * Created by sbrega on 26/11/2014.
 */
public class AuroraRequestPacket extends MB_ADU_Request {

    static final PDUFactory factory = new AuroraRequestFactory();

    public AuroraRequestPacket(MB_address mb_address, MB_PDU pdu) {
        super(factory, mb_address, pdu);

    }

    public AuroraRequestPacket() {
        super(factory);

    }
}
