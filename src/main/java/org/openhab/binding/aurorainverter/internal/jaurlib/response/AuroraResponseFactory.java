/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPduFactory;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqActualTime;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqCumulatedEnergy;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqDspData;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqFwVersion;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqLastAlarms;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqMFGdate;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqProductNumber;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqSerialNumber;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqState;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqSystemConfig;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqTimeCounter;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AReqVersionId;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraDspRequestEnum;

/**
 * @author Stefano Brega (27/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class AuroraResponseFactory extends MbPduFactory implements AuroraResponseBuilder {
    public AuroraResponseFactory() {
        super();
    }

    @Override
    protected MbPdu create(MbCode code) {
        return null;
    }

    @Override
    public AuroraResponse createResponse(AReqActualTime request) {
        ARespActualTime result = new ARespActualTime();
        result.setDescription("Inverter Time");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqVersionId request) {
        ARespVersionId result = new ARespVersionId();
        result.setDescription("Version Number");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqMFGdate request) {
        ARespMFGdate result = new ARespMFGdate();
        result.setDescription("Manufacturing Date");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqTimeCounter request) {
        ARespTimeCounter result = new ARespTimeCounter();
        result.setDescription("Time Counter (days)");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqState request) {
        ARespState result = new ARespState();
        result.setDescription("Configuration");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqFwVersion request) {
        ARespFwVersion result = new ARespFwVersion();
        result.setDescription("Firmware Version");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqSerialNumber request) {
        ARespSerialNumber result = new ARespSerialNumber();
        result.setDescription("Serial Number");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqSystemConfig request) {
        return new ARespSysConfig();
    }

    @Override
    public AuroraResponse createResponse(AReqProductNumber request) {
        ARespProductNumber result = new ARespProductNumber();
        result.setDescription("Product Number");
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqCumulatedEnergy request) {
        ARespCumulatedEnergy result = new ARespCumulatedEnergy();
        String description = AuroraCumEnergyEnum.fromCode(request.getParam1()).toString() + " Cumulated Energy";
        result.setDescription(description);
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqDspData request) {
        ARespDspData result = new ARespDspData();
        String description = AuroraDspRequestEnum.fromCode(request.getParam1()).toString();
        result.setDescription(description);
        return result;
    }

    @Override
    public AuroraResponse createResponse(AReqLastAlarms request) {
        ARespLastAlarms result = new ARespLastAlarms();
        result.setDescription("Alarms List");
        return result;
    }
}
