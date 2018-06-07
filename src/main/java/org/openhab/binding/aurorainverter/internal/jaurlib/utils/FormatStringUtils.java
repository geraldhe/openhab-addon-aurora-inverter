package org.openhab.binding.aurorainverter.internal.jaurlib.utils;

/**
 * Created by stefano on 27/12/14.
 */
public class FormatStringUtils {

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
