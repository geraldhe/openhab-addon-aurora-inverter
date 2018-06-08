/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPduFactory;

/**
 * @author Stefano Brega (22/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class AuroraRequestFactory extends MbPduFactory {

    protected static Map<Integer, Class<?>> mapCode2ClassMsg = new HashMap<Integer, Class<?>>();
    protected static Map<Class<?>, Integer> mapClassMsg2Code = new HashMap<Class<?>, Integer>();

    public AuroraRequestFactory() {
        initMap();
        revertMap();
    }

    protected void revertMap() {
        for (Integer code : mapCode2ClassMsg.keySet()) {
            mapClassMsg2Code.put(mapCode2ClassMsg.get(code), code);
        }
    }

    public MbPdu create(Class<?> aClass) {
        MbPdu result;

        try {
            Integer code = mapClassMsg2Code.get(aClass);
            result = (MbPdu) aClass.getConstructor(MbCode.class).newInstance(new MbCode(code));
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    @Override
    protected MbPdu create(MbCode code) {
        MbPdu result;

        try {
            Class<?> theClass = mapCode2ClassMsg.get(code.getValue());
            result = (MbPdu) theClass.getConstructor(MbCode.class).newInstance(code);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public void initMap() {
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETVERSIONID.value, AReqVersionId.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETDSP.value, AReqDspData.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETSTATE.value, AReqState.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETFWVERSION.value, AReqFwVersion.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETMFGDATE.value, AReqMFGdate.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETSERIALNUMBER.value, AReqSerialNumber.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETSYSTEMCONFIG.value, AReqSystemConfig.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETPRODUCTNUMBER.value, AReqProductNumber.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETTIMECOUNTER.value, AReqTimeCounter.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETACCUMULATEDENERGY.value, AReqCumulatedEnergy.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETACTUALTIME.value, AReqActualTime.class);
        mapCode2ClassMsg.put(AuroraRequestCodeEnum.GETLASTALARMS.value, AReqLastAlarms.class);
    }

    public AuroraRequest createAReqVersionId() {
        AuroraRequest req = (AuroraRequest) create(AReqVersionId.class);
        return req;
    }

    public AuroraRequest createAReqVersionIdShort() {
        AuroraRequest req = (AuroraRequest) create(AReqVersionId.class);
        if (req != null) {
            req.setParam1('\0');
        }
        return req;
    }

    public AuroraRequest createAReqDspData(AuroraDspRequestEnum auroraDspRequestEnum) {
        AReqDspData req = (AReqDspData) create(AReqDspData.class);
        req.setParam1((char) auroraDspRequestEnum.get());
        return req;
    }

    public AuroraRequest createAReqCumulatedEnergy(AuroraCumEnergyEnum requestedValue) {
        AReqCumulatedEnergy req = (AReqCumulatedEnergy) create(AReqCumulatedEnergy.class);
        req.setParam1((char) requestedValue.get());
        return req;
    }

    public AuroraRequest createAReqState() {
        AReqState req = (AReqState) create(AReqState.class);
        return req;
    }

    public AuroraRequest createAReqFwVersion() {
        AReqFwVersion req = (AReqFwVersion) create(AReqFwVersion.class);
        return req;
    }

    public AuroraRequest createAReqMfgDate() {
        AReqMFGdate req = (AReqMFGdate) create(AReqMFGdate.class);
        return req;
    }

    public AuroraRequest createAReqSerialNumber() {
        AReqSerialNumber req = (AReqSerialNumber) create(AReqSerialNumber.class);
        return req;
    }

    public AuroraRequest createAReqSystemConfig() {
        AReqSystemConfig req = (AReqSystemConfig) create(AReqSystemConfig.class);
        return req;
    }

    public AuroraRequest createAReqProductNumber() {
        AReqProductNumber req = (AReqProductNumber) create(AReqProductNumber.class);
        return req;
    }

    public AuroraRequest createAReqTimeCounter() {
        AReqTimeCounter req = (AReqTimeCounter) create(AReqTimeCounter.class);
        return req;
    }

    public AuroraRequest createAReqActualTime() {
        AReqActualTime req = (AReqActualTime) create(AReqActualTime.class);
        return req;
    }

    public AuroraRequest createAReqAlarmsList() {
        AReqLastAlarms req = (AReqLastAlarms) create(AReqLastAlarms.class);
        return req;
    }
}
