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
package org.openhab.binding.aurorainverter.internal.jaurlib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbAddress;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraDspRequestEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraRequest;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraRequestFactory;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraRequestPacket;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponseFactory;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponsePacket;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ResponseErrorEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.utils.FormatStringUtils;
import org.openhab.core.io.transport.serial.PortInUseException;
import org.openhab.core.io.transport.serial.SerialPort;
import org.openhab.core.io.transport.serial.SerialPortIdentifier;
import org.openhab.core.io.transport.serial.UnsupportedCommOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stefano Brega (17/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class AuroraDriver {
    private Logger logger = LoggerFactory.getLogger(AuroraDriver.class);

    protected int serialPortTimeout = 2000;
    protected final AuroraRequestFactory auroraRequestFactory;
    protected final AuroraResponseFactory auroraResponseFactory;

    protected SerialPort serialPort;

    private OutputStream output;

    private InputStream inputStream;

    @Nullable
    protected AuroraResponse msgReceived = null;
    private long receivingPause = 50;
    private long communicationPause = 50;

    public AuroraDriver(SerialPortIdentifier serialPortIdentifier, int serialPortBaudRate,
            AuroraRequestFactory reqFactory, AuroraResponseFactory respFactory)
            throws IOException, PortInUseException, UnsupportedCommOperationException {
        this.serialPort = serialPortIdentifier.open(AuroraDriver.class.getCanonicalName(), 2000);
        this.serialPort.setSerialPortParams(serialPortBaudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        this.serialPort.enableReceiveTimeout(serialPortTimeout);

        OutputStream outputStr = this.serialPort.getOutputStream();
        if (outputStr == null) {
            throw new IOException();
        } else {
            this.output = outputStr;
        }

        InputStream inputStream = this.serialPort.getInputStream();
        if (inputStream == null) {
            throw new IOException();
        } else {
            this.inputStream = inputStream;
        }

        this.auroraResponseFactory = respFactory;
        this.auroraRequestFactory = reqFactory;
    }

    protected void sendRequest(int address, MbPdu auroraRequest) throws Exception {
        AuroraRequestPacket auroraRequestPacket = new AuroraRequestPacket(new MbAddress(address), auroraRequest);

        Thread.sleep(communicationPause);

        while (this.inputStream.available() > 0) {
            int bytesToSkip = this.inputStream.available();

            byte[] bytesSkipped = this.inputStream.readNBytes(bytesToSkip);
            logger.warn(
                    "there were {} bytes (content: {}) already waiting for reading - before we sent the command. we skipped these.",
                    bytesToSkip, FormatStringUtils.byteArrayToHex(bytesSkipped));
        }

        this.output.write(auroraRequestPacket.toByteArray());
        this.output.flush();
        logger.trace("sent request.");
    }

    private AuroraResponse readResponse(AuroraRequest auroraRequest) throws IllegalStateException {
        AuroraResponse result = auroraRequest.create(auroraResponseFactory);

        if (result == null) {
            throw new IllegalStateException("Couldn't create a response-object for request: " + auroraRequest);
        }

        try {
            Thread.sleep(receivingPause);
            byte[] buffer = inputStream.readNBytes(8);
            logger.trace("Read buffer (Hex): {}", FormatStringUtils.byteArrayToHex(buffer));

            AuroraResponsePacket pkt = new AuroraResponsePacket(result);
            pkt.read(new ByteArrayInputStream(buffer));
            store((AuroraResponse) pkt.getPdu());
            result = (AuroraResponse) pkt.getPdu();
        } catch (IOException ex) {
            logger.warn("i/o-error while reading response for request {}: {}", auroraRequest.code.getValue(),
                    ex.getMessage());
            result.setErrorCode(ResponseErrorEnum.CRC);
        } catch (Exception ue) {
            logger.warn("error while reading response for request {}: {}", auroraRequest.code.getValue(),
                    ue.getMessage());
            result.setErrorCode(ResponseErrorEnum.UNKNOWN);
        }

        return result;
    }

    private void store(AuroraResponse message) {
        msgReceived = message;
    }

    public void stop() {
        try {
            inputStream.close();
        } catch (IOException e) {
            logger.debug("An error occured while closing the serial input stream", e);
        }

        try {
            output.close();
        } catch (IOException e) {
            logger.debug("An error occured while closing the serial output stream", e);
        }

        serialPort.close();
        // For some reason, there needs some delay after close so we don't fail to open back the serial device
        // TODO : investigate if this is still a real issue with the serial transport
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.debug("Thread interrupted while closing the communicator");
        }
    }

    public synchronized AuroraResponse acquireVersionId(int address) throws Exception {
        logger.debug("Sending acquireVersionId to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqVersionId();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireDspValue(int invAddress, AuroraDspRequestEnum requestedValue)
            throws Exception {
        logger.debug("Sending acquireDspValue ({}) to inverter with address {}", requestedValue, invAddress);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqDspData(requestedValue);
        sendRequest(invAddress, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireCumulatedEnergy(int address, AuroraCumEnergyEnum requestedValue)
            throws Exception {
        logger.debug("Sending Cumulated Energy Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqCumulatedEnergy(requestedValue);
        sendRequest(address, auroraRequest);
        AuroraResponse response = readResponse(auroraRequest);
        logger.debug("Received response: {}", response);
        return response;
    }

    public synchronized AuroraResponse acquireState(int address) throws Exception {
        logger.debug("Sending State Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqState();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireFirmwareVersion(int address) throws Exception {
        logger.debug("Sending Firmware Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqFwVersion();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireMFGdate(int address) throws Exception {
        logger.debug("Sending Manufacturing Date Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqMfgDate();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireSystemConfig(int address) throws Exception {
        logger.debug("Sending System Configuration Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqSystemConfig();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireSerialNumber(int address) throws Exception {
        logger.debug("Sending Serial Number Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqSerialNumber();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireProductNumber(int address) throws Exception {
        logger.debug("Sending Product Number Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqProductNumber();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireTimeCounter(int address) throws Exception {
        logger.debug("Sending Time Counter Request to inverter with address {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqTimeCounter();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireActualTime(int address) throws Exception {
        return acquireData(address);
    }

    public synchronized AuroraResponse acquireData(int address) throws Exception {
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqActualTime();
        logger.debug("Sending Request {} to inverter with address {}", auroraRequest, address);
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireLastAlarms(int address) throws Exception {
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqAlarmsList();
        logger.debug("Sending Request {} to inverter with address {}", auroraRequest, address);
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    // public void initSerialPort() throws SerialPortException {
    // serialPort.openPort(); // Open serial port
    // serialPort.setParams(19200, 8, 1, 0); // Set params.
    // }
    //
    // public void setSerialPort(SerialPort aSerialPort) {
    // this.serialPort = aSerialPort;
    // }
}
