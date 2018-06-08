/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.openhab.binding.aurorainverter.internal.jaurlib.modbus.MbCode;

/**
 * @author Stefano Brega (27/11/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
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
    public String getValue() {
        String localDescription = "Model: " + getModelName() + ", Nationality: " + getNationality() + ", Transformer "
                + getTransformerInfo() + ", Type: " + getType();
        return localDescription;
    }
}
