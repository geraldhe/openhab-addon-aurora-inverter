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
import org.eclipse.jdt.annotation.Nullable;
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

import jssc.SerialPort;
import jssc.SerialPortException;

/***
 * The {@link AuroraInverterInverterHandler} is responsible for handling the serial port connection
 *
 * @author Gerald Heilmann - Initial contribution
 */
public class AuroraInverterBridgeHandler extends BaseBridgeHandler {
    private Logger logger = LoggerFactory.getLogger(AuroraInverterBridgeHandler.class);

    // private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // private Calendar nextPossibleConnectionRetry;

    private AuroraDriver auroraDrv;

    @Nullable
    private SerialPort serialPort;

    private AuroraInverterBridgeConfiguration config;

    public AuroraInverterBridgeHandler(@NonNull Bridge bridge) {
        super(bridge);

        this.auroraDrv = new AuroraDriver(null, new AuroraRequestFactory(), new AuroraResponseFactory());

        logger.debug("Creating bridge...");

        // this.nextPossibleConnectionRetry = Calendar.getInstance();
        // this.nextPossibleConnectionRetry.set(Calendar.YEAR, 1900);

        logger.debug("AuroraInverterHandler.constr ");
        // this.dateFormat.format(this.nextPossibleConnectionRetry.getTime()));
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand for channel {}: {}", channelUID.getId(), command.toString());
    }

    @SuppressWarnings("null")
    @Override
    public void initialize() {
        // if (this.nextPossibleConnectionRetry.after(Calendar.getInstance())) {
        // logger.debug("wait some time before trying to connect... "
        // + this.dateFormat.format(this.nextPossibleConnectionRetry.getTime()));
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_CONFIGURATION_PENDING,
        // "wait some time before trying to connect... "
        // + this.dateFormat.format(this.nextPossibleConnectionRetry.getTime()));
        // return;
        // }

        this.config = getConfigAs(AuroraInverterBridgeConfiguration.class);

        if (this.config.inverterSerialPort == null || this.config.inverterSerialPort.length() == 0) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "no serial port specified");
            return;
        }

        if (this.config.inverterSerialPortBaudRate <= 0 || this.config.inverterSerialPortBaudRate > 921600) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "invalid baud rate specified");
            return;
        }

        // configuration is ok, next reconnect-try is allowed in 2 minutes.
        // this.nextPossibleConnectionRetry = Calendar.getInstance();
        // this.nextPossibleConnectionRetry.add(Calendar.MINUTE, 2);

        try {
            this.serialPort = new SerialPort(config.inverterSerialPort);
            this.serialPort.openPort();
            this.serialPort.setParams(config.inverterSerialPortBaudRate, 8, 1, 0);
            this.auroraDrv.setSerialPort(this.serialPort);
            // dont stop it - hold the connection open...
            // _auroraDriver.stop();
            updateStatus(ThingStatus.ONLINE);
        } catch (SerialPortException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getExceptionType());
            logger.error("Communication error", e);
        } catch (Exception e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_INITIALIZING_ERROR, e.getMessage());
            logger.error("Initialization error", e);
        }
    }

    @Override
    public void dispose() {
        if (this.serialPort != null) {
            try {
                this.serialPort.closePort();
            } catch (SerialPortException e) {
                logger.error("cant close port", e);
            }
        }
    }
}
