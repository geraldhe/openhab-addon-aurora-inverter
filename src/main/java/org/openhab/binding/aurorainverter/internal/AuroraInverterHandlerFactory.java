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

import static org.openhab.binding.aurorainverter.internal.AuroraInverterBindingConstants.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.aurorainverter.handler.AuroraInverterBridgeHandler;
import org.openhab.binding.aurorainverter.handler.AuroraInverterInverterHandler;
import org.openhab.core.io.transport.serial.SerialPortManager;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link AuroraInverterHandlerFactory} is responsible for creating things
 * and thing handlers.
 *
 * @author Gerald Heilmann - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.aurorainverter", service = ThingHandlerFactory.class)
public class AuroraInverterHandlerFactory extends BaseThingHandlerFactory {

    @Activate
    public AuroraInverterHandlerFactory(final @Reference SerialPortManager serialPortManager) {
        this.serialPortManager = serialPortManager;
    }

    private final SerialPortManager serialPortManager;

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Stream
            .of(THING_TYPE_INVERTER, THING_TYPE_BRIDGE).collect(Collectors.toSet());

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_BRIDGE.equals(thingTypeUID)) {
            return new AuroraInverterBridgeHandler((Bridge) thing, serialPortManager);
        }

        if (THING_TYPE_INVERTER.equals(thingTypeUID)) {
            return new AuroraInverterInverterHandler(thing);
        }

        return null;
    }
}
