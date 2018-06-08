/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.handler;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.aurorainverter.internal.AuroraInverterBridgeConfiguration;
import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraRequestFactory;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jssc.SerialPortException;

/***
 * The {@link AuroraInverterInverterHandler} is responsible for handling the serial port connection
 *
 * @author Gerald Heilmann - Initial contribution
 */
public class AuroraInverterBridgeHandler extends BaseBridgeHandler {
    private Logger logger = LoggerFactory.getLogger(AuroraInverterBridgeHandler.class);

    protected AuroraDriver auroraDrv;

    private AuroraInverterBridgeConfiguration config;

    public AuroraInverterBridgeHandler(@NonNull Bridge bridge) {
        super(bridge);
        logger.debug("CONSTR AuroraInverter-BRIDGE-Handler");

        this.auroraDrv = new AuroraDriver(null, new AuroraRequestFactory(), new AuroraResponseFactory());
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand for channel {}: {}", channelUID.getId(), command.toString());
    }

    @Override
    public void initialize() {
        logger.debug("INIT AuroraInverter-BRIDGE-Handler");

        this.config = getConfigAs(AuroraInverterBridgeConfiguration.class);

        if (this.config.inverterSerialPort == null || this.config.inverterSerialPort.length() == 0) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "no serial port specified");
            return;
        }

        if (this.config.inverterSerialPortBaudRate <= 0 || this.config.inverterSerialPortBaudRate > 921600) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "invalid baud rate specified");
            return;
        }

        try {
            this.auroraDrv.setSerialPort(config.inverterSerialPort, config.inverterSerialPortBaudRate);
            // dont stop it - hold the connection open...
            // _auroraDriver.stop();
            updateStatus(ThingStatus.ONLINE);
        } catch (SerialPortException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_INITIALIZING_ERROR,
                    e.getExceptionType() + " | " + e.getMessage());
            logger.error("SerialPortException", e);
        }
    }

    @Override
    public void dispose() {
        logger.debug("DESTRUCT AuroraInverter-BRIDGE-Handler");
        if (this.auroraDrv != null) {
            this.auroraDrv.stop();
            this.auroraDrv = null;
        }
    }
}
