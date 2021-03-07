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
package org.openhab.binding.aurorainverter.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link AuroraInverterBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Gerald Heilmann - Initial contribution
 */
@NonNullByDefault
public class AuroraInverterBindingConstants {

    private static final String BINDING_ID = "aurorainverter";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    public static final ThingTypeUID THING_TYPE_INVERTER = new ThingTypeUID(BINDING_ID, "inverter");

    // List of all Channel ids
    public static final String CHANNEL_INVERTER_VERSION = "version";

    public static final String CHANNEL_CUMULATED_ENERGY_DAILY = "cumulatedEnergyDaily";
    public static final String CHANNEL_CUMULATED_ENERGY_LAST7DAYS = "cumulatedEnergyLast7Days";
    public static final String CHANNEL_CUMULATED_ENERGY_MONTHLY = "cumulatedEnergyMonthly";
    public static final String CHANNEL_CUMULATED_ENERGY_PARTIAL = "cumulatedEnergyPartial";
    public static final String CHANNEL_CUMULATED_ENERGY_TOTAL = "cumulatedEnergyTotal";
    public static final String CHANNEL_CUMULATED_ENERGY_WEEKLY = "cumulatedEnergyWeekly";
    public static final String CHANNEL_CUMULATED_ENERGY_YEARLY = "cumulatedEnergyYearly";

    public static final String CHANNEL_ALIM_TEMPERATURE_CENTRAL = "alimTemperatureCentral";
    public static final String CHANNEL_AVG_GRID_VOLTAGE = "avgGridVoltage";
    public static final String CHANNEL_BOOSTER_TEMPERATURE_GRID_TIED = "boosterTemperatureGridTied";
    public static final String CHANNEL_FAN_1_SPEED_CENTRAL = "fan1SpeedCentral";
    public static final String CHANNEL_FAN_1_SPEED_RPM_CENTRAL = "fan1SpeedRpmCentral";
    public static final String CHANNEL_FAN_2_SPEED_CENTRAL = "fan2SpeedCentral";
    public static final String CHANNEL_FAN_2_SPEED_RPM_CENTRAL = "fan2SpeedRpmCentral";
    public static final String CHANNEL_FAN_3_SPEED_CENTRAL = "fan3SpeedCentral";
    public static final String CHANNEL_FAN_3_SPEED_RPM_CENTRAL = "fan3SpeedRpmCentral";
    public static final String CHANNEL_FAN_4_SPEED_CENTRAL = "fan4SpeedCentral";
    public static final String CHANNEL_FAN_4_SPEED_RPM_CENTRAL = "fan4SpeedRpmCentral";
    public static final String CHANNEL_FAN_5_SPEED_CENTRAL = "fan5SpeedCentral";
    public static final String CHANNEL_FAN_5_SPEED_RPM_CENTRAL = "fan5SpeedRpmCentral";
    public static final String CHANNEL_FAN_6_SPEED_RPM_CENTRAL = "fan6SpeedRpmCentral";
    public static final String CHANNEL_FAN_7_SPEED_RPM_CENTRAL = "fan7SpeedRpmCentral";
    public static final String CHANNEL_FREQUENCY_ALL = "frequencyAll";
    public static final String CHANNEL_FREQUENCY_PHASE_R_CENTRAL_AND_3_PHASE = "frequencyPhaseRCentralAnd3Phase";
    public static final String CHANNEL_FREQUENCY_PHASE_S_CENTRAL_AND_3_PHASE = "frequencyPhaseSCentralAnd3Phase";
    public static final String CHANNEL_FREQUENCY_PHASE_T_CENTRAL_AND_3_PHASE = "frequencyPhaseTCentralAnd3Phase";
    public static final String CHANNEL_GRID_CURRENT_ALL = "gridCurrentAll";
    public static final String CHANNEL_GRID_CURRENT_PHASE_R_CENTRAL_AND_3_PHASE = "gridCurrentPhaseRCentralAnd3Phase";
    public static final String CHANNEL_GRID_CURRENT_PHASE_S_CENTRAL_AND_3_PHASE = "gridCurrentPhaseSCentralAnd3Phase";
    public static final String CHANNEL_GRID_CURRENT_PHASE_T_CENTRAL_AND_3_PHASE = "gridCurrentPhaseTCentralAnd3Phase";
    public static final String CHANNEL_GRID_FREQUENCY = "gridFrequency";
    public static final String CHANNEL_GRID_POWER_ALL = "gridPowerAll";
    public static final String CHANNEL_GRID_VOLTAGE = "gridVoltage";
    public static final String CHANNEL_GRID_VOLTAGE_ALL = "gridVoltageAll";
    public static final String CHANNEL_GRID_VOLTAGE_NEUTRAL_GRID_TIED = "gridVoltageNeutralGridTied";
    public static final String CHANNEL_GRID_VOLTAGE_NEUTRAL_PHASE_CENTRAL = "gridVoltageNeutralPhaseCentral";
    public static final String CHANNEL_GRID_VOLTAGE_PHASE_R_CENTRAL_AND_3_PHASE = "gridVoltagePhaseRCentralAnd3Phase";
    public static final String CHANNEL_GRID_VOLTAGE_PHASE_S_CENTRAL_AND_3_PHASE = "gridVoltagePhaseSCentralAnd3Phase";
    public static final String CHANNEL_GRID_VOLTAGE_PHASE_T_CENTRAL_AND_3_PHASE = "gridVoltagePhaseTCentralAnd3Phase";
    public static final String CHANNEL_HEAK_SINK_TEMPERATURE_CENTRAL = "heatSinkTemperatureCentral";
    public static final String CHANNEL_ILEAK_DCDC = "ileakDcdc";
    public static final String CHANNEL_ILEAK_INVERTER = "ileakInverter";
    public static final String CHANNEL_INPUT_1_CURRENT = "input1Current";
    public static final String CHANNEL_INPUT_1_VOLTAGE = "input1Voltage";
    public static final String CHANNEL_INPUT_2_CURRENT = "input2Current";
    public static final String CHANNEL_INPUT_2_VOLTAGE = "input2Voltage";
    public static final String CHANNEL_INVERTER_TEMPERATURE_GRID_TIED = "inverterTemperatureGridTied";
    public static final String CHANNEL_ISOLATION_RESISTANCE_ALL = "isolationResistanceAll";
    public static final String CHANNEL_PIN_1_INPUT_POWER = "pin1InputPower";
    public static final String CHANNEL_PIN_2_INPUT_POWER = "pin2InputPower";
    public static final String CHANNEL_POWER_PEAK_ALL = "powerPeakAll";
    public static final String CHANNEL_POWER_PEAK_TODAY_AL = "powerPeakTodayAl";
    public static final String CHANNEL_POWER_SATURATION_LIMIT_DER_CENTRAL = "powerSaturationLimitDerCentral";
    public static final String CHANNEL_REFERENCE_RING_BULK_CENTRAL = "referenceRingBulkCentral";
    public static final String CHANNEL_SUPERVISOR_TEMPERATURE_CENTRAL = "supervisorTemperatureCentral";
    public static final String CHANNEL_TEMPERATURE_1_CENTRAL = "temperature1Central";
    public static final String CHANNEL_TEMPERATURE_2_CENTRAL = "temperature2Central";
    public static final String CHANNEL_TEMPERATURE_3_CENTRAL = "temperature3Central";
    public static final String CHANNEL_VBULK_GRID = "vbulkGrid";
    public static final String CHANNEL_VBULK_ILEAK_DCDC = "vbulkIleakDcdc";
    public static final String CHANNEL_VBULK_MID_GRID_TIED = "vbulkMidGridTied";
    public static final String CHANNEL_VBULK_MINUS_CENTRAL = "vbulkMinusCentral";
    public static final String CHANNEL_VBULK_PLUS_CENTRAL_AND_3_PHASE = "vbulkPlusCentralAnd3Phase";
    public static final String CHANNEL_VPANEL_MICRO_CENTRAL = "vpanelMicroCentral";
    public static final String CHANNEL_WIND_GENERATOR_FREQUENCY = "windGeneratorFrequency";

    // List of all Properties
    public static final String DEVICE_PROPERTY_MODELNAME = "modelNumber";
    public static final String DEVICE_PROPERTY_NATIONALITY = "nationality";
    public static final String DEVICE_PROPERTY_TRANSFORMER = "transformer";
    public static final String DEVICE_PROPERTY_TYPE = "type";
    public static final String DEVICE_PROPERTY_MFGDATE = "manifacture date";
    public static final String DEVICE_PROPERTY_PRODUCTNUMBER = "product number";
    public static final String DEVICE_PROPERTY_SERIALNUMBER = "serial number";
    public static final String DEVICE_PROPERTY_FIRMWAREVERSION = "firmware version";
}
