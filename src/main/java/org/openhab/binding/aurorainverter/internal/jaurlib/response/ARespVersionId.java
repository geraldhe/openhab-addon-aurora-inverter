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
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;

/**
 * @author Stefano Brega (27/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class ARespVersionId extends AuroraResponse {

    public ARespVersionId(MbCode code) {
        super(code);
    }

    public ARespVersionId() {
        super();
    }

    @Override
    public String toString() {
        String localDescription = getValue();
        return description.isEmpty() ? super.toString() : description + " " + localDescription;
    }

    public String getType() {
        AIType type = AIType.fromCode(getParam4());
        return type == null ? "UNKNOWN" : type.toString();
    }

    public String getTransformerInfo() {
        AITransformerType type = AITransformerType.fromCode(getParam3());
        return type == null ? "UNKNOWN" : type.toString();
    }

    public String getNationality() {
        AINationEnum type = AINationEnum.fromCode(getParam2());
        return type == null ? "UNKNOWN" : type.toString();
    }

    public String getModelName() {
        AIModelsEnum type = AIModelsEnum.fromCode(getParam1());
        return type == null ? "UNKNOWN" : type.toString();
    }

    @Override
    @Nullable
    public String getValue() {
        String localDescription = "Model: " + getModelName() + ", Nationality: " + getNationality() + ", Transformer "
                + getTransformerInfo() + ", Type: " + getType();
        return localDescription;
    }
}
