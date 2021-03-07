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
package org.openhab.binding.aurorainverter.handler;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.aurorainverter.internal.AuroraInverterBridgeConfiguration;
import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraRequestFactory;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseFactory;
import org.openhab.core.io.transport.serial.PortInUseException;
import org.openhab.core.io.transport.serial.SerialPortIdentifier;
import org.openhab.core.io.transport.serial.SerialPortManager;
import org.openhab.core.io.transport.serial.UnsupportedCommOperationException;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * The {@link AuroraInverterInverterHandler} is responsible for handling the
 * serial port connection
 *
 * @author Gerald Heilmann - Initial contribution
 */
@NonNullByDefault
public class AuroraInverterBridgeHandler extends BaseBridgeHandler {
    private Logger logger = LoggerFactory.getLogger(AuroraInverterBridgeHandler.class);

    @Nullable
    protected AuroraDriver auroraDrv;

    private AuroraInverterBridgeConfiguration config = new AuroraInverterBridgeConfiguration();

    /**
     * Serial port manager used to get serial port identifiers.
     */
    private final SerialPortManager serialPortManager;

    public AuroraInverterBridgeHandler(Bridge bridge, SerialPortManager serialPortManager) {
        super(bridge);
        logger.debug("CONSTR AuroraInverter-BRIDGE-Handler");

        this.serialPortManager = serialPortManager;
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand for channel {}: {}", channelUID.getId(), command.toString());
    }

    @Override
    public void initialize() {
        logger.debug("INIT AuroraInverter-BRIDGE-Handler");

        this.config = getConfigAs(AuroraInverterBridgeConfiguration.class);
        String identifier = this.config.inverterSerialPort;

        if (identifier == null || identifier.length() == 0) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "no serial port specified");
            return;
        }

        if (identifier.length() > 0) {
            if (this.config.inverterSerialPortBaudRate <= 0 || this.config.inverterSerialPortBaudRate > 921600) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "invalid baud rate specified");
                return;
            }

            try {
                SerialPortIdentifier serialPortIdentifier = serialPortManager.getIdentifier(identifier);
                if (serialPortIdentifier == null) {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                            "Serial port " + identifier + " does not exist!");

                    String identifiers = serialPortManager.getIdentifiers()
                            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
                    logger.error("Serial port {} does not exist! choose one of these: {}", identifier, identifiers);

                    return;
                }

                this.auroraDrv = new AuroraDriver(serialPortIdentifier, config.inverterSerialPortBaudRate,
                        new AuroraRequestFactory(), new AuroraResponseFactory());
                // dont stop it - hold the connection open...
                // _auroraDriver.stop();
                updateStatus(ThingStatus.ONLINE);
                logger.debug("successfully initialized brdige.");
            } catch (PortInUseException e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_INITIALIZING_ERROR, e.getMessage());
                logger.error("PortInUse", e);
            } catch (IOException e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_INITIALIZING_ERROR, e.getMessage());
                logger.error("IOException", e);
            } catch (UnsupportedCommOperationException e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_INITIALIZING_ERROR, e.getMessage());
                logger.error("UnsupportedCommOperationException", e);
            }
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "no serial port specified");
            return;
        }
    }

    @Override
    public void dispose() {
        logger.debug("DESTRUCT AuroraInverter-BRIDGE-Handler");

        if (this.auroraDrv != null) {
            this.auroraDrv.stop();
            this.auroraDrv = null;
        }

        super.dispose();
    }
}
