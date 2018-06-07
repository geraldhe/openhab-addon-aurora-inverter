package org.openhab.binding.aurorainverter.internal.jaurlib.response;

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

/**
 * Created by stefano on 06/12/14.
 */
public interface AuroraResponseBuilder {

    public AuroraResponse createResponse(AReq_ActualTime request);

    public AuroraResponse createResponse(AReq_VersionId request);

    public AuroraResponse createResponse(AReq_MFGdate request);

    public AuroraResponse createResponse(AReq_TimeCounter request);

    public AuroraResponse createResponse(AReq_State request);

    public AuroraResponse createResponse(AReq_FwVersion request);

    public AuroraResponse createResponse(AReq_SerialNumber request);

    public AuroraResponse createResponse(AReq_SystemConfig request);

    public AuroraResponse createResponse(AReq_ProductNumber request);

    public AuroraResponse createResponse(AReq_CumulatedEnergy request);

    public AuroraResponse createResponse(AReq_DspData request);

    public AuroraResponse createResponse(AReq_LastAlarms request);
}
