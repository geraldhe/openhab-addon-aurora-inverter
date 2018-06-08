/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbPdu;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbAddress;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 * @author Stefano Brega (17/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class AuroraDriver {
    private Logger logger = LoggerFactory.getLogger(AuroraDriver.class);

    protected int serialPortTimeout = 2000;
    protected final AuroraRequestFactory auroraRequestFactory;
    protected final AuroraResponseFactory auroraResponseFactory;
    protected SerialPort serialPort;
    protected AuroraResponse msgReceived = null;
    private long receivingPause = 50;
    private long communicationPause = 50;

    public AuroraDriver(SerialPort serialPort, AuroraRequestFactory reqFactory, AuroraResponseFactory respFactory) {
        this.serialPort = serialPort;

        this.auroraResponseFactory = respFactory;
        this.auroraRequestFactory = reqFactory;
    }

    protected void sendRequest(int address, MbPdu auroraRequest) throws Exception {
        AuroraRequestPacket auroraRequestPacket = new AuroraRequestPacket(new MbAddress(address), auroraRequest);
        Thread.sleep(communicationPause);
        serialPort.purgePort(SerialPort.PURGE_RXCLEAR | SerialPort.PURGE_TXCLEAR);
        serialPort.writeBytes(auroraRequestPacket.toByteArray());
    }

    private AuroraResponse readResponse(AuroraRequest auroraRequest) throws Exception {
        AuroraResponse result = auroraRequest.create(auroraResponseFactory);

        if (result == null) {
            throw new Exception("No Response available for Request: " + auroraRequest);
        }

        try {
            serialPort.purgePort(SerialPort.PURGE_RXCLEAR);
            Thread.sleep(receivingPause);
            byte[] buffer = serialPort.readBytes(8, serialPortTimeout);
            logger.trace("Read buffer (Hex): " + FormatStringUtils.byteArrayToHex(buffer));
            AuroraResponsePacket pkt = new AuroraResponsePacket(result);
            pkt.read(new ByteArrayInputStream(buffer));
            store((AuroraResponse) pkt.getPdu());
            result = (AuroraResponse) pkt.getPdu();
        } catch (IOException ex) {
            result.setErrorCode(ResponseErrorEnum.CRC);
        } catch (SerialPortTimeoutException e) {
            result.setErrorCode(ResponseErrorEnum.TIMEOUT);
        } catch (Exception ue) {
            result.setErrorCode(ResponseErrorEnum.UNKNOWN);
        }

        return result;
    }

    private void store(AuroraResponse message) {
        msgReceived = message;
    }

    public void stop() {
        try {
            if (serialPort != null) {
                serialPort.closePort();// Close serial port
            }
        } catch (SerialPortException ex) {
            logger.error("error closing port {}: {}", ex.getPortName(), ex.getMessage());
        }
    }

    public synchronized AuroraResponse acquireVersionId(int address) throws Exception {
        logger.debug("Sending acquireVersionId to: {}", address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqVersionId();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireDspValue(int invAddress, AuroraDspRequestEnum requestedValue)
            throws Exception {
        logger.debug("Sending acquireDspValue (" + requestedValue + ") to: " + invAddress);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqDspData(requestedValue);
        sendRequest(invAddress, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response: {}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireCumulatedEnergy(int address, AuroraCumEnergyEnum requestedValue)
            throws Exception {
        logger.debug("Sending Cumulated Energy Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqCumulatedEnergy(requestedValue);
        sendRequest(address, auroraRequest);
        AuroraResponse response = readResponse(auroraRequest);
        logger.debug("Received response: " + response);
        return response;
    }

    public synchronized AuroraResponse acquireState(int address) throws Exception {
        logger.debug("Sending State Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqState();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireFirmwareVersion(int address) throws Exception {
        logger.debug("Sending Firmware Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqFwVersion();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireMFGdate(int address) throws Exception {
        logger.debug("Sending Manufacturing Date Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqMfgDate();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireSystemConfig(int address) throws Exception {
        logger.debug("Sending System Configuration Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqSystemConfig();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireSerialNumber(int address) throws Exception {
        logger.debug("Sending Serial Number Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqSerialNumber();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireProductNumber(int address) throws Exception {
        logger.debug("Sending Product Number Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqProductNumber();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireTimeCounter(int address) throws Exception {
        logger.debug("Sending Product Number Request to: " + address);
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqTimeCounter();
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireActualTime(int address) throws Exception {
        return acquireData(address);
    }

    public synchronized AuroraResponse acquireData(int address) throws Exception {
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqActualTime();
        logger.debug("Sending Request " + auroraRequest + "to address: " + address);
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public synchronized AuroraResponse acquireLastAlarms(int address) throws Exception {
        AuroraRequest auroraRequest = auroraRequestFactory.createAReqAlarmsList();
        logger.debug("Sending Request " + auroraRequest + " to address: " + address);
        sendRequest(address, auroraRequest);
        AuroraResponse responseMsg = readResponse(auroraRequest);
        logger.debug("Received response{}", responseMsg);
        return responseMsg;
    }

    public void initSerialPort() throws SerialPortException {
        serialPort.openPort(); // Open serial port
        serialPort.setParams(19200, 8, 1, 0); // Set params.
    }

    public void setSerialPort(SerialPort aSerialPort) {
        this.serialPort = aSerialPort;
    }

    public void setSerialPort(String serialPortName, int serialPortBaudRate) throws SerialPortException {
        serialPort = new SerialPort(serialPortName);
        serialPort.openPort(); // Open serial port
        serialPort.setParams(serialPortBaudRate, 8, 1, 0); // Set params.
    }
}
