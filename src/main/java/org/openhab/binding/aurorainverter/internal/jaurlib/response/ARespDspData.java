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
package org.openhab.binding.aurorainverter.internal.jaurlib.response;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Stefano Brega (02/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
@NonNullByDefault
public class ARespDspData extends AuroraResponse {

    @Override
    @Nullable
    public String getValue() {
        return String.valueOf(getFloatParam());
    }

    public ARespDspData() {
        super();
    }

    @Override
    public String toString() {
        return description.isEmpty() ? super.toString() : description + ": " + this.getFloatParam();
    }
}
