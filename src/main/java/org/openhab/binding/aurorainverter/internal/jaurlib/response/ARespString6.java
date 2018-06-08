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
 * @author Stefano Brega (12/12/14) - Initial contribution
 * @author Gerald Heilmann (08/06/18) - adaptations for using with OpenHAB
 */
public class ARespString6 extends AuroraResponse {

    public void set(String productNumber) {
        setCode(new MbCode(0));
        setSubCode((char) 0);
        setParam1((char) 0);
        setParam2((char) 0);
        setParam3((char) 0);
        setParam4((char) 0);
        if (productNumber == null || productNumber.length() == 0) {
            return;
        }
        switch (productNumber.length()) {
            // no, it's not a mistake
            default:
            case 6:
                setParam4(productNumber.charAt(5));
            case 5:
                setParam3(productNumber.charAt(4));
            case 4:
                setParam2(productNumber.charAt(3));
            case 3:
                setParam1(productNumber.charAt(2));
            case 2:
                setSubCode(productNumber.charAt(1));
            case 1:
                setCode(new MbCode(productNumber.charAt(0)));
                break;
        }
    }

    public String get() {
        char param0 = (char) code.getValue().byteValue();
        String result = "" + param0 + getSubCode() + getParam1() + getParam2() + getParam3() + getParam4();
        return result.trim();
    }

    @Override
    public String toString() {
        return description.isEmpty() ? super.toString() : description + ": " + get();
    }

    @Override
    public String getValue() {
        return get();
    }
}
