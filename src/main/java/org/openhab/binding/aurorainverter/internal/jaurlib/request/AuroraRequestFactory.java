/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.request;

import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
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
        try {
            Integer code = mapClassMsg2Code.get(aClass);
            if (code == null) {
                return null;
            }
            return (MbPdu) aClass.getConstructor(MbCode.class).newInstance(new MbCode(code));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected MbPdu create(MbCode code) {
        try {
            Class<?> theClass = mapCode2ClassMsg.get(code.getValue());
            if (theClass == null) {
                return null;
            }
            return (MbPdu) theClass.getConstructor(MbCode.class).newInstance(code);
        } catch (Exception e) {
            return null;
        }
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
