package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_PDU;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MB_code;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.PDUFactory;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_ActualTime;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_CumulatedEnergy;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_DspData;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_FwVersion;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_LastAlarms;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_MFGdate;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_ProductNumber;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_SerialNumber;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_State;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_SystemConfig;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_TimeCounter;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReq_VersionId;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraDspRequestEnum;

/**
 * Created by sbrega on 27/11/2014.
 */

public class AuroraResponseFactory extends PDUFactory implements AuroraResponseBuilder {

    public AuroraResponseFactory() {
        super();
    }

    @Override
    protected MB_PDU create(MB_code code) {
        return null;
    }

    @Override
    public AuroraResponse createResponse(AReq_ActualTime request) {

        AResp_ActualTime result = new AResp_ActualTime();
        result.setDescription("Inverter Time");
        return result;

    }

    @Override
    public AuroraResponse createResponse(AReq_VersionId request) {
        AResp_VersionId result = new AResp_VersionId();
        result.setDescription("Version Number");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_MFGdate request) {
        AResp_MFGdate result = new AResp_MFGdate();
        result.setDescription("Manufacturing Date");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_TimeCounter request) {

        AResp_TimeCounter result = new AResp_TimeCounter();
        result.setDescription("Time Counter (days)");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_State request) {
        AResp_State result = new AResp_State();
        result.setDescription("Configuration");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_FwVersion request) {
        AResp_FwVersion result = new AResp_FwVersion();
        result.setDescription("Firmware Version");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_SerialNumber request) {
        AResp_SerialNumber result = new AResp_SerialNumber();
        result.setDescription("Serial Number");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_SystemConfig request) {
        return new AResp_SysConfig();
    }

    @Override
    public AuroraResponse createResponse(AReq_ProductNumber request) {
        AResp_ProductNumber result = new AResp_ProductNumber();
        result.setDescription("Product Number");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_CumulatedEnergy request) {
        AResp_CumulatedEnergy result = new AResp_CumulatedEnergy();
        String description = AuroraCumEnergyEnum.fromCode(request.getParam1()).toString() + " Cumulated Energy";
        result.setDescription(description);
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReq_DspData request) {
        AResp_DspData result = new AResp_DspData();
        String description = AuroraDspRequestEnum.fromCode(request.getParam1()).toString();
        result.setDescription(description);
        return result;

    }

    @Override
    public AuroraResponse createResponse(AReq_LastAlarms request) {
        AResp_LastAlarms result = new AResp_LastAlarms();
        result.setDescription("Alarms List");
        return result;
    }

}