package com.samsung.android.globalactions.util;

/* loaded from: classes5.dex */
public interface SamsungGlobalActionsAnalytics {
    public static final String DID_BIKE_MODE = "Bike mode";
    public static final String DID_BIXBY_KEY = "Bixby key";
    public static final String DID_EMERGENCY_MODE = "Emergency mode";
    public static final String DID_EMERGENCY_SOS = "Emergency SOS";
    public static final String DID_LOCK_DOWN = "Lock down";
    public static final String DID_MOBILE_DATA = "Mobile data";
    public static final String DID_POWER_KEY = "Power key";
    public static final String DID_POWER_OFF = "Power off";
    public static final String DID_PRO_KIOSK = "Pro kiosk";
    public static final String DID_RESTART = "Restart";
    public static final String DID_SIDE_KEY_SETTINGS = "Side key settings";
    public static final String DID_SIDE_KEY_TYPE = "Side key type";
    public static final String EID_DEVICE_OPTIONS = "6111";
    public static final String EID_FRONT_COVER_DEVICE_OPTIONS = "5021";
    public static final String EID_FRONT_COVER_POWER_OFF = "5019";
    public static final String EID_FRONT_COVER_RESTART = "5020";
    public static final String EID_FRONT_COVER_SECURE_LOCK_NOTI = "5025";
    public static final String EID_POWER_KEY_TYPE = "6131";
    public static final String EID_SAFE_MODE = "6121";
    public static final String EID_SIDE_KEY_TYPE = "6133";
    public static final String SID_DEVICE_OPTIONS = "611";
    public static final String SID_FRONT_COVER_DEVICE_OPTIONS = "503";
    public static final String SID_SAFE_MODE = "612";
    public static final int VID_BIKE_MODE = 6;
    public static final int VID_EMERGENCY_MODE = 3;
    public static final int VID_EMERGENCY_SOS = 9;
    public static final int VID_LOCK_DOWN = 4;
    public static final int VID_MOBILE_DATA = 5;
    public static final int VID_POWER_OFF = 1;
    public static final int VID_PRO_KIOSK = 7;
    public static final int VID_RESTART = 2;

    void sendEventLog(String str, String str2);

    void sendEventLog(String str, String str2, String str3, long j);
}
