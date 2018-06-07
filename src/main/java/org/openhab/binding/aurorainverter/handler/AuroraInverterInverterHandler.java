/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.handler;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.aurorainverter.internal.AuroraInverterInverterConfiguration;
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

    // private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected final List<ChannelUID> measuredChannels = new ArrayList<ChannelUID>();

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            updateAll();
        }
    };

    // private final Map<String, AuroraDspRequestEnum> map = new HashMap<String, AuroraDspRequestEnum>();

    private AuroraInverterInverterConfiguration config;

    @Nullable
    private AuroraInverterBridgeHandler bridgeHandler;

    @Nullable
    private ScheduledFuture<?> scheduledFuture;

    public AuroraInverterInverterHandler(Thing thing) {
        super(thing);

        this.config = new AuroraInverterInverterConfiguration();

        // this.map.put(CHANNEL_ALIM_TEMPERATURE_CENTRAL, AuroraDspRequestEnum.ALIM_TEMPERATURE_CENTRAL);
        // this.map.put(CHANNEL_AVG_GRID_VOLTAGE, AuroraDspRequestEnum.AVERAGE_GRID_VOLTAGE);
        // this.map.put(CHANNEL_BOOSTER_TEMPERATURE_GRID_TIED, AuroraDspRequestEnum.BOOSTER_TEMPERATURE_GRID_TIED);
        // this.map.put(CHANNEL_FAN_1_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_1_SPEED_CENTRAL);
        // this.map.put(CHANNEL_FAN_1_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_1_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FAN_2_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_2_SPEED_CENTRAL);
        // this.map.put(CHANNEL_FAN_2_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_2_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FAN_3_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_3_SPEED_CENTRAL);
        // this.map.put(CHANNEL_FAN_3_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_3_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FAN_4_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_4_SPEED_CENTRAL);
        // this.map.put(CHANNEL_FAN_4_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_4_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FAN_5_SPEED_CENTRAL, AuroraDspRequestEnum.FAN_5_SPEED_CENTRAL);
        // this.map.put(CHANNEL_FAN_5_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_5_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FAN_6_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_6_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FAN_7_SPEED_RPM_CENTRAL, AuroraDspRequestEnum.FAN_7_SPEED_RPM_CENTRAL);
        // this.map.put(CHANNEL_FREQUENCY_ALL, AuroraDspRequestEnum.FREQUENCY_ALL);
        // this.map.put(CHANNEL_FREQUENCY_PHASE_R_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.FREQUENCY_PHASE_R_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_FREQUENCY_PHASE_S_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.FREQUENCY_PHASE_S_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_FREQUENCY_PHASE_T_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.FREQUENCY_PHASE_T_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_GRID_CURRENT_ALL, AuroraDspRequestEnum.GRID_CURRENT_ALL);
        // this.map.put(CHANNEL_GRID_CURRENT_PHASE_R_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.GRID_CURRENT_PHASE_R_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_GRID_CURRENT_PHASE_S_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.GRID_CURRENT_PHASE_S_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_GRID_CURRENT_PHASE_T_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.GRID_CURRENT_PHASE_T_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_GRID_FREQUENCY, AuroraDspRequestEnum.GRID_FREQUENCY);
        // this.map.put(CHANNEL_GRID_POWER_ALL, AuroraDspRequestEnum.GRID_POWER_ALL);
        // this.map.put(CHANNEL_GRID_VOLTAGE, AuroraDspRequestEnum.GRID_VOLTAGE);
        // this.map.put(CHANNEL_GRID_VOLTAGE_ALL, AuroraDspRequestEnum.GRID_VOLTAGE_ALL);
        // this.map.put(CHANNEL_GRID_VOLTAGE_NEUTRAL_GRID_TIED, AuroraDspRequestEnum.GRID_VOLTAGE_NEUTRAL_GRID_TIED);
        // this.map.put(CHANNEL_GRID_VOLTAGE_NEUTRAL_PHASE_CENTRAL,
        // AuroraDspRequestEnum.GRID_VOLTAGE_NEUTRAL_PHASE_CENTRAL);
        // this.map.put(CHANNEL_GRID_VOLTAGE_PHASE_R_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.GRID_VOLTAGE_PHASE_R_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_GRID_VOLTAGE_PHASE_S_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.GRID_VOLTAGE_PHASE_S_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_GRID_VOLTAGE_PHASE_T_CENTRAL_AND_3_PHASE,
        // AuroraDspRequestEnum.GRID_VOLTAGE_PHASE_T_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_HEAK_SINK_TEMPERATURE_CENTRAL, AuroraDspRequestEnum.HEAK_SINK_TEMPERATURE_CENTRAL);
        // this.map.put(CHANNEL_ILEAK_DCDC, AuroraDspRequestEnum.ILEAK_DCDC);
        // this.map.put(CHANNEL_ILEAK_INVERTER, AuroraDspRequestEnum.ILEAK_INVERTER);
        // this.map.put(CHANNEL_INPUT_1_CURRENT, AuroraDspRequestEnum.INPUT_1_CURRENT);
        // this.map.put(CHANNEL_INPUT_1_VOLTAGE, AuroraDspRequestEnum.INPUT_1_VOLTAGE);
        // this.map.put(CHANNEL_INPUT_2_CURRENT, AuroraDspRequestEnum.INPUT_2_CURRENT);
        // this.map.put(CHANNEL_INPUT_2_VOLTAGE, AuroraDspRequestEnum.INPUT_2_VOLTAGE);
        // this.map.put(CHANNEL_INVERTER_TEMPERATURE_GRID_TIED, AuroraDspRequestEnum.INVERTER_TEMPERATURE_GRID_TIED);
        // this.map.put(CHANNEL_ISOLATION_RESISTANCE_ALL, AuroraDspRequestEnum.ISOLATION_RESISTANCE_ALL);
        // this.map.put(CHANNEL_PIN_1_INPUT_POWER, AuroraDspRequestEnum.PIN_1);
        // this.map.put(CHANNEL_PIN_2_INPUT_POWER, AuroraDspRequestEnum.PIN_2);
        // this.map.put(CHANNEL_POWER_PEAK_ALL, AuroraDspRequestEnum.POWER_PEAK_ALL);
        // this.map.put(CHANNEL_POWER_PEAK_TODAY_AL, AuroraDspRequestEnum.POWER_PEAK_TODAY_AL);
        // this.map.put(CHANNEL_POWER_SATURATION_LIMIT_DER_CENTRAL,
        // AuroraDspRequestEnum.POWER_SATURATION_LIMIT_DER_CENTRAL);
        // this.map.put(CHANNEL_REFERENCE_RING_BULK_CENTRAL, AuroraDspRequestEnum.REFERENCE_RING_BULK_CENTRAL);
        // this.map.put(CHANNEL_SUPERVISOR_TEMPERATURE_CENTRAL, AuroraDspRequestEnum.SUPERVISOR_TEMPERATURE_CENTRAL);
        //
        // this.map.put(CHANNEL_TEMPERATURE_1_CENTRAL, AuroraDspRequestEnum.TEMPERATURE_1_CENTRAL);
        // this.map.put(CHANNEL_TEMPERATURE_2_CENTRAL, AuroraDspRequestEnum.TEMPERATURE_2_CENTRAL);
        // this.map.put(CHANNEL_TEMPERATURE_3_CENTRAL, AuroraDspRequestEnum.TEMPERATURE_3_CENTRAL);
        // this.map.put(CHANNEL_VBULK_GRID, AuroraDspRequestEnum.VBULK_GRID);
        // this.map.put(CHANNEL_VBULK_ILEAK_DCDC, AuroraDspRequestEnum.VBULK_ILEAK_DCDC);
        // this.map.put(CHANNEL_VBULK_MID_GRID_TIED, AuroraDspRequestEnum.VBULK_MID_GRID_TIED);
        // this.map.put(CHANNEL_VBULK_MINUS_CENTRAL, AuroraDspRequestEnum.VBULK_MINUS_CENTRAL);
        // this.map.put(CHANNEL_VBULK_PLUS_CENTRAL_AND_3_PHASE, AuroraDspRequestEnum.VBULK_PLUS_CENTRAL_AND_3_PHASE);
        // this.map.put(CHANNEL_VPANEL_MICRO_CENTRAL, AuroraDspRequestEnum.VPANEL_MICRO_CENTRAL);
        // this.map.put(CHANNEL_WIND_GENERATOR_FREQUENCY, AuroraDspRequestEnum.WIND_GENERATOR_FREQUENCY);

        // for (AuroraDspRequestEnum key : AuroraDspRequestEnum.values()) {
        // if (!this.map.containsValue(key)) {
        // logger.error("aurora-library key missing: {}", key);
        // }
        // }
    }

    @Override
    public void initialize() {
        logger.debug("AuroraInverterInverterHandler.initialize");
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
        if (this.bridgeHandler == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE, "no bridge handler?!");
            return;
        }

        try {
            // AuroraDriver drv = this.bridgeHandler.getDriver();
            // AuroraResponse respVersion = drv.acquireVersionId(config.inverterAddress);
            //
            // logger.debug("Result from inverter: " + respVersion.toString());
            //
            // ResponseErrorEnum errorCode = respVersion.getErrorCode();
            // if (errorCode != ResponseErrorEnum.NONE) {
            // logger.error("Inverter not online: " + errorCode.toString());
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, errorCode.toString());
            // return;
            // }
            //
            // Map<String, String> properties = editProperties();
            // if (respVersion instanceof AResp_VersionId) {
            // AResp_VersionId respVersionCasted = (AResp_VersionId) respVersion;
            // properties.put(DEVICE_PROPERTY_MODELNAME, respVersionCasted.getModelName());
            // properties.put(DEVICE_PROPERTY_NATIONALITY, respVersionCasted.getNationality());
            // properties.put(DEVICE_PROPERTY_TRANSFORMER, respVersionCasted.getTransformerInfo());
            // properties.put(DEVICE_PROPERTY_TYPE, respVersionCasted.getType());
            // }
            //
            // AResp_MFGdate respMfgDate = (AResp_MFGdate) drv.acquireMFGdate(config.inverterAddress);
            // properties.put(DEVICE_PROPERTY_MFGDATE, dateFormat.format(respMfgDate.get()));
            //
            // AResp_ProductNumber respProdNumber = (AResp_ProductNumber)
            // drv.acquireProductNumber(config.inverterAddress);
            // properties.put(DEVICE_PROPERTY_PRODUCTNUMBER, respProdNumber.get());
            //
            // AResp_SerialNumber respSerialNumber = (AResp_SerialNumber)
            // drv.acquireSerialNumber(config.inverterAddress);
            // properties.put(DEVICE_PROPERTY_SERIALNUMBER, respSerialNumber.get());
            //
            // AResp_FwVersion respFirmwareVersion = (AResp_FwVersion)
            // drv.acquireFirmwareVersion(config.inverterAddress);
            // properties.put(DEVICE_PROPERTY_FIRMWAREVERSION, respFirmwareVersion.get());
            //
            // updateProperties(properties);

            int refreshRate = config.refreshRate;
            this.scheduledFuture = scheduler.scheduleWithFixedDelay(refreshRunnable, refreshRate, refreshRate, SECONDS);

            updateStatus(ThingStatus.ONLINE);
        } catch (Exception e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            logger.error("communication error", e);
        }
    }

    public void update(String key) {
        Bridge brdge = this.getBridge();
        Thing thing = this.getThing();
        if (brdge == null || brdge.getStatus() != ThingStatus.ONLINE || thing.getStatus() != ThingStatus.ONLINE) {
            return;
        }

        if (this.bridgeHandler == null) {
            return;
        }
        //
        // AuroraDriver drv = this.bridgeHandler.getDriver();
        // int invAddr = config.inverterAddress;
        //
        // String keyLowerCase = key.toLowerCase();
        //
        // try {
        // if (keyLowerCase.startsWith("cumulated")) {
        // AuroraCumEnergyEnum rng = AuroraCumEnergyEnum.TOTAL;
        // switch (key) {
        // case CHANNEL_CUMULATED_ENERGY_DAILY:
        // rng = AuroraCumEnergyEnum.DAILY;
        // break;
        // case CHANNEL_CUMULATED_ENERGY_LAST7DAYS:
        // rng = AuroraCumEnergyEnum.LAST7DAYS;
        // break;
        // case CHANNEL_CUMULATED_ENERGY_MONTHLY:
        // rng = AuroraCumEnergyEnum.MONTHLY;
        // break;
        // case CHANNEL_CUMULATED_ENERGY_PARTIAL:
        // rng = AuroraCumEnergyEnum.PARTIAL;
        // break;
        // case CHANNEL_CUMULATED_ENERGY_TOTAL:
        // rng = AuroraCumEnergyEnum.TOTAL;
        // break;
        // case CHANNEL_CUMULATED_ENERGY_WEEKLY:
        // rng = AuroraCumEnergyEnum.WEEKLY;
        // break;
        // case CHANNEL_CUMULATED_ENERGY_YEARLY:
        // rng = AuroraCumEnergyEnum.YEARLY;
        // break;
        // default:
        // logger.error("unkown range: " + key);
        // break;
        // }
        // AResp_CumulatedEnergy response = (AResp_CumulatedEnergy) drv.acquireCumulatedEnergy(invAddr, rng);
        // updateState(key, new QuantityType<>(new DecimalType(response.get()), SmartHomeUnits.WATT_HOUR));
        // } else {
        // AResp_DspData response = (AResp_DspData) drv.acquireDspValue(invAddr, this.map.get(key));
        // float rspVal = response.getFloatParam();
        // if (keyLowerCase.contains("temperature")) {
        // updateState(key, new QuantityType<>(new DecimalType(rspVal), SIUnits.CELSIUS));
        // } else if (keyLowerCase.contains("current")) {
        // updateState(key, new QuantityType<>(new DecimalType(rspVal), SmartHomeUnits.AMPERE));
        // } else if (keyLowerCase.contains("voltage")) {
        // updateState(key, new QuantityType<>(new DecimalType(rspVal), SmartHomeUnits.VOLT));
        // } else if (keyLowerCase.contains("frequency")) {
        // updateState(key, new QuantityType<>(new DecimalType(rspVal), SmartHomeUnits.HERTZ));
        // } else if (keyLowerCase.contains("power")) {
        // updateState(key, new QuantityType<>(new DecimalType(rspVal), SmartHomeUnits.WATT));
        // } else if (keyLowerCase.contains("resistance")) {
        // updateState(key, new QuantityType<>(new DecimalType(rspVal), MEGA(SmartHomeUnits.OHM)));
        // } else {
        // updateState(key, new QuantityType<Dimensionless>(new DecimalType(rspVal), SmartHomeUnits.ONE));
        // }
        // }
        // } catch (Exception e) {
        // logger.error("error updating value", e);
        // }
    }

    public void updateAll() {
        if (this.isOnline()) {
            for (ChannelUID key : this.measuredChannels) {
                update(key.getId());
            }
        }
    }

    @Override
    public void channelLinked(ChannelUID channelUID) {
        super.channelLinked(channelUID);
        measuredChannels.add(channelUID);
    }

    @Override
    public void channelUnlinked(ChannelUID channelUID) {
        super.channelUnlinked(channelUID);
        measuredChannels.remove(channelUID);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand for channel {}: {}", channelUID.getId(), command.toString());
        if (command instanceof RefreshType && this.isOnline()) {
            // String idChannelUID = channelUID.getId();
            // if (this.map.containsKey(idChannelUID)) {
            // update(idChannelUID);
            // } else {
            // logger.error("Channel not configured: {}", idChannelUID);
            // }
        }
    }

    public boolean isOnline() {
        @Nullable
        Thing thing = this.getThing();

        if (thing.getStatus() == ThingStatus.ONLINE) {
            return true;
        }

        // not online: if last connect-try is at least 2 minute ago, try to reconnect
        this.initialize();

        // if still not online:
        return false;
    }

    @Override
    public void dispose() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }
}
