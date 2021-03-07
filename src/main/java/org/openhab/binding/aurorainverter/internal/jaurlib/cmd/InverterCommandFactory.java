/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
package org.openhab.binding.aurorainverter.internal.jaurlib.cmd;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraDspRequestEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stefano Brega (28/12/15) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class InverterCommandFactory {
    protected Logger logger = LoggerFactory.getLogger(InverterCommandFactory.class);

    Map<String, AuroraCumEnergyEnum> mapEnergyCmd = new HashMap<>();
    Map<String, AuroraDspRequestEnum> mapDspCmd = new HashMap<>();

    public InverterCommandFactory() {
        mapEnergyCmd.put("daily", AuroraCumEnergyEnum.DAILY);
        mapEnergyCmd.put("weekly", AuroraCumEnergyEnum.WEEKLY);
        mapEnergyCmd.put("monthly", AuroraCumEnergyEnum.MONTHLY);
        mapEnergyCmd.put("yearly", AuroraCumEnergyEnum.YEARLY);
        mapEnergyCmd.put("last7days", AuroraCumEnergyEnum.LAST7DAYS);
        mapEnergyCmd.put("partial", AuroraCumEnergyEnum.PARTIAL);
        mapEnergyCmd.put("total", AuroraCumEnergyEnum.TOTAL);

        mapDspCmd.put("freqAll", AuroraDspRequestEnum.FREQUENCY_ALL);
        mapDspCmd.put("gridVoltageAll", AuroraDspRequestEnum.GRID_VOLTAGE_ALL);
        mapDspCmd.put("gridCurrentAll", AuroraDspRequestEnum.GRID_CURRENT_ALL);
        mapDspCmd.put("gridPowerAll", AuroraDspRequestEnum.GRID_POWER_ALL);
        mapDspCmd.put("input1Voltage", AuroraDspRequestEnum.INPUT_1_VOLTAGE);
        mapDspCmd.put("input1Current", AuroraDspRequestEnum.INPUT_1_CURRENT);
        mapDspCmd.put("input2Voltage", AuroraDspRequestEnum.INPUT_2_VOLTAGE);
        mapDspCmd.put("input2Current", AuroraDspRequestEnum.INPUT_2_CURRENT);
        mapDspCmd.put("inverterTemp", AuroraDspRequestEnum.INVERTER_TEMPERATURE_GRID_TIED);
        mapDspCmd.put("boosterTemp", AuroraDspRequestEnum.BOOSTER_TEMPERATURE_GRID_TIED);
    }

    public InverterCommand create(String opCodeParameter, String subCodeParameter, int addressParameter) {
        switch (opCodeParameter) {
            case "cumEnergy":
                AuroraCumEnergyEnum period = mapEnergyCmd.get(subCodeParameter);
                if (period != null) {
                    return new InvCmdCumEnergy(addressParameter, period);
                }
                throw new IllegalStateException("Received cumEnergy command - but result was null!");
            case "dspData":
                AuroraDspRequestEnum magnitude = mapDspCmd.get(subCodeParameter);
                if (magnitude != null) {
                    return new InvCmdDspData(addressParameter, magnitude);
                }
                throw new IllegalStateException("Received dspData command - but result was null!");
            case "productNumber":
                return new InvCmdProductNumber(addressParameter);
            case "serialNumber":
                return new InvCmdSerialNumber(addressParameter);
            case "versionNumber":
                return new InvCmdVersionNumber(addressParameter);
            case "firmwareNumber":
                return new InvCmdFirmwareVersion(addressParameter);
            case "manufacturingDate":
                return new InvCmdMfgDate(addressParameter);
            case "sysConfig":
                return new InvCmdSysConfig(addressParameter);
            case "timeCounter":
                return new InvCmdTimeCounter(addressParameter);
            case "actualTime":
                return new InvCmdActualTime(addressParameter);
            case "lastAlarms":
                return new InvCmdLastAlarms(addressParameter);
            default:
                logger.error("Received unknown inverter command, with opcode: {}", opCodeParameter);
                throw new IllegalStateException("Received unknown inverter command!");
        }
    }
}
