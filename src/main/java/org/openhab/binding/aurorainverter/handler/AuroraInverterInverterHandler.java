/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.handler;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.eclipse.smarthome.core.library.unit.MetricPrefix.*;
import static org.openhab.binding.aurorainverter.AuroraInverterBindingConstants.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.measure.quantity.Dimensionless;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.unit.SIUnits;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.aurorainverter.internal.AuroraInverterInverterConfiguration;
import org.openhab.binding.aurorainverter.internal.jaurlib.AuroraDriver;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraCumEnergyEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.request.AuroraDspRequestEnum;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespCumulatedEnergy;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespDspData;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespFwVersion;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespMFGdate;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespProductNumber;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespSerialNumber;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ARespVersionId;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.AuroraResponse;
import org.openhab.binding.aurorainverter.internal.jaurlib.response.ResponseErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * The{@link AuroraInverterInverterHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Gerald Heilmann - Initial contribution
 */
@NonNullByDefault
public class AuroraInverterInverterHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(AuroraInverterInverterHandler.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Map<String, AuroraDspRequestEnum> mapDsp = new HashMap<String, AuroraDspRequestEnum>();
    private final Map<String, AuroraCumEnergyEnum> mapCumEnergy = new HashMap<String, AuroraCumEnergyEnum>();

    private AuroraInverterInverterConfiguration config;

    @Nullable
    private AuroraInverterBridgeHandler bridgeHandler;

    @Nullable
    private ScheduledFuture<?> schedulerUpdates;

    @Nullable
    private ScheduledFuture<?> schedulerReconnect;

    private final Runnable runnableReconnect = new Runnable() {
        @Override
        public void run() {
            if (getThing().getStatus() == ThingStatus.OFFLINE) {
                logger.debug("trying to reconnect");
                // try reconnecting.
                initialize();
                if (getThing().getStatus() == ThingStatus.ONLINE && schedulerReconnect != null) {
                    // stop scheduler
                    logger.debug("Stopping reconnect-scheduler");
                    if (schedulerReconnect != null) {
                        schedulerReconnect.cancel(false);
                        schedulerReconnect = null;
                    }
                }
            }
        }
    };

    public AuroraInverterInverterHandler(Thing thing) {
        super(thing);

        logger.debug("CONSTR AuroraInverter-INVERTER-Handler");

        this.config = new AuroraInverterInverterConfiguration();

        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_DAILY, AuroraCumEnergyEnum.DAILY);
        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_LAST7DAYS, AuroraCumEnergyEnum.LAST7DAYS);
        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_MONTHLY, AuroraCumEnergyEnum.MONTHLY);
        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_PARTIAL, AuroraCumEnergyEnum.PARTIAL);
        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_TOTAL, AuroraCumEnergyEnum.TOTAL);
        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_WEEKLY, AuroraCumEnergyEnum.WEEKLY);
        this.mapCumEnergy.put(CHANNEL_CUMULATED_ENERGY_YEARLY, AuroraCumEnergyEnum.YEARLY);

        this.mapDsp.put(CHANNEL_ALIM_TEMPERATURE_CENTRAL, AuroraDspRequestEnum.ALIM_TEMPERATURE_CENTRAL);
        this.mapDsp.put(CHANNEL_AVG_GRID_VOLTAGE, AuroraDspRequestEnum.AVERAGE_GRID_VOLTAGE);
        this.mapDsp.put(CHANNEL_BOOSTER_TEMPERATURE_GRID_TIED, AuroraDspRequestEnum.BOOSTER_TEMPERATURE_GRID_TIED);
        this.mapDsp.put(CHANNEL_FAN_1_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_1_SPEED_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_1_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_1_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_2_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_2_SPEED_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_2_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_2_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_3_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_3_SPEED_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_3_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_3_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_4_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_4_SPEED_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_4_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_4_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_5_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_5_SPEED_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_5_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_5_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_6_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_6_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FAN_7_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_7_SPEED_RPM_CENTRAL);
        this.mapDsp.put(CHANNEL_FREQUENCY_ALL, AuroraDspRequestEnum.FREQUENCY_ALL);
        this.mapDsp.put(CHANNEL_FREQUENCY_PHASE_R_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.FREQUENCY_PHASE_R_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_FREQUENCY_PHASE_S_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.FREQUENCY_PHASE_S_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_FREQUENCY_PHASE_T_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.FREQUENCY_PHASE_T_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_GRID_CURRENT_ALL, AuroraDspRequestEnum.GRID_CURRENT_ALL);
        this.mapDsp.put(CHANNEL_GRID_CURRENT_PHASE_R_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.GRID_CURRENT_PHASE_R_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_GRID_CURRENT_PHASE_S_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.GRID_CURRENT_PHASE_S_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_GRID_CURRENT_PHASE_T_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.GRID_CURRENT_PHASE_T_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_GRID_FREQUENCY, AuroraDspRequestEnum.GRID_FREQUENCY);
        this.mapDsp.put(CHANNEL_GRID_POWER_ALL, AuroraDspRequestEnum.GRID_POWER_ALL);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE, AuroraDspRequestEnum.GRID_VOLTAGE);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE_ALL, AuroraDspRequestEnum.GRID_VOLTAGE_ALL);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE_NEUTRAL_GRID_TIED, AuroraDspRequestEnum.GRID_VOLTAGE_NEUTRAL_GRID_TIED);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE_NEUTRAL_PHASE_CENTRAL,
                AuroraDspRequestEnum.GRID_VOLTAGE_NEUTRAL_PHASE_CENTRAL);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE_PHASE_R_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.GRID_VOLTAGE_PHASE_R_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE_PHASE_S_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.GRID_VOLTAGE_PHASE_S_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_GRID_VOLTAGE_PHASE_T_CENTRAL_AND_3_PHASE,
                AuroraDspRequestEnum.GRID_VOLTAGE_PHASE_T_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_HEAK_SINK_TEMPERATURE_CENTRAL, AuroraDspRequestEnum.HEAK_SINK_TEMPERATURE_CENTRAL);
        this.mapDsp.put(CHANNEL_ILEAK_DCDC, AuroraDspRequestEnum.ILEAK_DCDC);
        this.mapDsp.put(CHANNEL_ILEAK_INVERTER, AuroraDspRequestEnum.ILEAK_INVERTER);
        this.mapDsp.put(CHANNEL_INPUT_1_CURRENT, AuroraDspRequestEnum.INPUT_1_CURRENT);
        this.mapDsp.put(CHANNEL_INPUT_1_VOLTAGE, AuroraDspRequestEnum.INPUT_1_VOLTAGE);
        this.mapDsp.put(CHANNEL_INPUT_2_CURRENT, AuroraDspRequestEnum.INPUT_2_CURRENT);
        this.mapDsp.put(CHANNEL_INPUT_2_VOLTAGE, AuroraDspRequestEnum.INPUT_2_VOLTAGE);
        this.mapDsp.put(CHANNEL_INVERTER_TEMPERATURE_GRID_TIED, AuroraDspRequestEnum.INVERTER_TEMPERATURE_GRID_TIED);
        this.mapDsp.put(CHANNEL_ISOLATION_RESISTANCE_ALL, AuroraDspRequestEnum.ISOLATION_RESISTANCE_ALL);
        this.mapDsp.put(CHANNEL_PIN_1_INPUT_POWER, AuroraDspRequestEnum.PIN_1);
        this.mapDsp.put(CHANNEL_PIN_2_INPUT_POWER, AuroraDspRequestEnum.PIN_2);
        this.mapDsp.put(CHANNEL_POWER_PEAK_ALL, AuroraDspRequestEnum.POWER_PEAK_ALL);
        this.mapDsp.put(CHANNEL_POWER_PEAK_TODAY_AL, AuroraDspRequestEnum.POWER_PEAK_TODAY_AL);
        this.mapDsp.put(CHANNEL_POWER_SATURATION_LIMIT_DER_CENTRAL,
                AuroraDspRequestEnum.POWER_SATURATION_LIMIT_DER_CENTRAL);
        this.mapDsp.put(CHANNEL_REFERENCE_RING_BULK_CENTRAL, AuroraDspRequestEnum.REFERENCE_RING_BULK_CENTRAL);
        this.mapDsp.put(CHANNEL_SUPERVISOR_TEMPERATURE_CENTRAL, AuroraDspRequestEnum.SUPERVISOR_TEMPERATURE_CENTRAL);
        this.mapDsp.put(CHANNEL_TEMPERATURE_1_CENTRAL, AuroraDspRequestEnum.TEMPERATURE_1_CENTRAL);
        this.mapDsp.put(CHANNEL_TEMPERATURE_2_CENTRAL, AuroraDspRequestEnum.TEMPERATURE_2_CENTRAL);
        this.mapDsp.put(CHANNEL_TEMPERATURE_3_CENTRAL, AuroraDspRequestEnum.TEMPERATURE_3_CENTRAL);
        this.mapDsp.put(CHANNEL_VBULK_GRID, AuroraDspRequestEnum.VBULK_GRID);
        this.mapDsp.put(CHANNEL_VBULK_ILEAK_DCDC, AuroraDspRequestEnum.VBULK_ILEAK_DCDC);
        this.mapDsp.put(CHANNEL_VBULK_MID_GRID_TIED, AuroraDspRequestEnum.VBULK_MID_GRID_TIED);
        this.mapDsp.put(CHANNEL_VBULK_MINUS_CENTRAL, AuroraDspRequestEnum.VBULK_MINUS_CENTRAL);
        this.mapDsp.put(CHANNEL_VBULK_PLUS_CENTRAL_AND_3_PHASE, AuroraDspRequestEnum.VBULK_PLUS_CENTRAL_AND_3_PHASE);
        this.mapDsp.put(CHANNEL_VPANEL_MICRO_CENTRAL, AuroraDspRequestEnum.VPANEL_MICRO_CENTRAL);
        this.mapDsp.put(CHANNEL_WIND_GENERATOR_FREQUENCY, AuroraDspRequestEnum.WIND_GENERATOR_FREQUENCY);

        for (AuroraDspRequestEnum key : AuroraDspRequestEnum.values()) {
            if (!this.mapDsp.containsValue(key)) {
                logger.error("implementation-error: AuroraDspRequestEnum key missing: {}", key);
            }
        }

        for (AuroraCumEnergyEnum key : AuroraCumEnergyEnum.values()) {
            if (!this.mapCumEnergy.containsValue(key)) {
                logger.error("implementation-error: AuroraCumEnergyEnum key missing: {}", key);
            }
        }
    }

    @Override
    public void initialize() {
        logger.debug("INIT AuroraInverter-INVERTER-Handler");

        this.config = getConfigAs(AuroraInverterInverterConfiguration.class);

        if (config.inverterAddress < 0) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "no valid inverter address specified");
            return;
        }

        Bridge brdge = this.getBridge();
        if (brdge == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
            return;
        }

        this.bridgeHandler = (AuroraInverterBridgeHandler) brdge.getHandler();

        try {
            if (this.bridgeHandler != null) {
                AuroraDriver drv = this.bridgeHandler.auroraDrv;
                AuroraResponse respVersion = drv.acquireVersionId(config.inverterAddress);

                logger.debug("Result from inverter: " + respVersion.toString());

                ResponseErrorEnum errorCode = respVersion.getErrorCode();
                if (errorCode != ResponseErrorEnum.NONE) {
                    logger.error("Inverter not online: " + errorCode.toString());
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, errorCode.toString());
                    return;
                }

                Map<String, String> properties = editProperties();
                if (respVersion instanceof ARespVersionId) {
                    ARespVersionId respVersionCasted = (ARespVersionId) respVersion;
                    properties.put(DEVICE_PROPERTY_MODELNAME, respVersionCasted.getModelName());
                    properties.put(DEVICE_PROPERTY_NATIONALITY, respVersionCasted.getNationality());
                    properties.put(DEVICE_PROPERTY_TRANSFORMER, respVersionCasted.getTransformerInfo());
                    properties.put(DEVICE_PROPERTY_TYPE, respVersionCasted.getType());
                }

                ARespMFGdate respMfgDate = (ARespMFGdate) drv.acquireMFGdate(config.inverterAddress);
                properties.put(DEVICE_PROPERTY_MFGDATE, dateFormat.format(respMfgDate.get()));

                ARespProductNumber respProdNumber = (ARespProductNumber) drv
                        .acquireProductNumber(config.inverterAddress);
                properties.put(DEVICE_PROPERTY_PRODUCTNUMBER, respProdNumber.get());

                ARespSerialNumber respSerialNumber = (ARespSerialNumber) drv
                        .acquireSerialNumber(config.inverterAddress);
                properties.put(DEVICE_PROPERTY_SERIALNUMBER, respSerialNumber.get());

                ARespFwVersion respFirmwareVersion = (ARespFwVersion) drv
                        .acquireFirmwareVersion(config.inverterAddress);
                properties.put(DEVICE_PROPERTY_FIRMWAREVERSION, respFirmwareVersion.get());

                updateProperties(properties);

                startAutomaticRefresh();

                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE, "no bridge handler?!");
                return;
            }
        } catch (Exception e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            logger.error("communication error", e);
        }
    }

    public void update(ChannelUID channelUID) {
        Bridge brdge = this.getBridge();
        Thing thing = this.getThing();
        if (brdge == null || brdge.getStatus() != ThingStatus.ONLINE || thing.getStatus() != ThingStatus.ONLINE) {
            if (schedulerReconnect != null && !schedulerReconnect.isCancelled()) {
                logger.debug("Starting reconnect-scheduler");
                schedulerReconnect = scheduler.scheduleWithFixedDelay(runnableReconnect, 2, 2, MINUTES);
            }
            return;
        }

        if (this.bridgeHandler != null) {
            AuroraDriver drv = this.bridgeHandler.auroraDrv;
            int invAddr = config.inverterAddress;

            String channelId = channelUID.getId();

            try {
                if (mapCumEnergy.containsKey(channelId)) {
                    AuroraCumEnergyEnum rng = mapCumEnergy.get(channelId);
                    ARespCumulatedEnergy response = (ARespCumulatedEnergy) drv.acquireCumulatedEnergy(invAddr, rng);
                    DecimalType kwh = new DecimalType(response.get() / 1000.0f);
                    updateState(channelUID, new QuantityType<>(kwh, KILO(SmartHomeUnits.WATT_HOUR)));
                } else if (mapDsp.containsKey(channelId)) {
                    ARespDspData response = (ARespDspData) drv.acquireDspValue(invAddr, this.mapDsp.get(channelId));
                    DecimalType rspVal = new DecimalType(response.getFloatParam());
                    if (channelId.contains("temperature")) {
                        updateState(channelUID, new QuantityType<>(rspVal, SIUnits.CELSIUS));
                    } else if (channelId.contains("current")) {
                        updateState(channelUID, new QuantityType<>(rspVal, SmartHomeUnits.AMPERE));
                    } else if (channelId.contains("voltage")) {
                        updateState(channelUID, new QuantityType<>(rspVal, SmartHomeUnits.VOLT));
                    } else if (channelId.contains("frequency")) {
                        updateState(channelUID, new QuantityType<>(rspVal, SmartHomeUnits.HERTZ));
                    } else if (channelId.contains("power")) {
                        updateState(channelUID, new QuantityType<>(rspVal, SmartHomeUnits.WATT));
                    } else if (channelId.contains("resistance")) {
                        updateState(channelUID, new QuantityType<>(rspVal, MEGA(SmartHomeUnits.OHM)));
                    } else {
                        updateState(channelUID, new QuantityType<Dimensionless>(rspVal, SmartHomeUnits.ONE));
                    }
                } else {
                    logger.error("unknown channel to update: {}", channelUID);
                }
            } catch (Exception e) {
                logger.error("error updating value {}\r\n{}", channelUID, e.toString());
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            }
        }
    }

    // source: OneWireGPIOHandler.java
    private void startAutomaticRefresh() {
        Runnable refresher = () -> {
            List<Channel> channels = getThing().getChannels();
            for (Channel channel : channels) {
                if (isLinked(channel.getUID().getId())) {
                    update(channel.getUID());
                }
            }
        };

        schedulerUpdates = scheduler.scheduleWithFixedDelay(refresher, 0, config.refreshRate, TimeUnit.SECONDS);
        logger.debug("Start automatic refresh every {} seconds", config.refreshRate);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand for channel {}: {}", channelUID.getId(), command.toString());
        if (command instanceof RefreshType) {
            update(channelUID);
        }
    }

    @Override
    public void dispose() {
        logger.debug("DESTRUCT AuroraInverter-INVERTER-Handler");

        if (schedulerReconnect != null) {
            schedulerReconnect.cancel(false);
            schedulerReconnect = null;
        }

        if (schedulerUpdates != null) {
            schedulerUpdates.cancel(true);
            schedulerUpdates = null;
        }

        super.dispose();
    }
}
