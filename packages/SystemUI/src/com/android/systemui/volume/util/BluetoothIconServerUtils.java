package com.android.systemui.volume.util;

public final class BluetoothIconServerUtils {
    public static final BluetoothIconServerUtils INSTANCE = new BluetoothIconServerUtils();
    public static final String FILE_NAME_LEFT = "icon_l";
    public static final String FILE_NAME_RIGHT = "icon_r";
    public static final String FILE_NAME_PAIR = "icon_pair";
    public static final String FILE_NAME_CASE = "icon_case";
    public static final String FILE_EXTENSION_SVG = ".svg";

    private BluetoothIconServerUtils() {
    }
}
