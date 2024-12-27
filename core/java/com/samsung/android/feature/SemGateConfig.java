package com.samsung.android.feature;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

/* loaded from: classes6.dex */
public final class SemGateConfig {
    public static final String ACTION_SCREEN_TEXT = "com.sec.android.gate.LCDTEXT";
    private static final String DEBUG_LEVEL_HIGH = "0x4948";
    private static final String DEBUG_LEVEL_LOW = "0x4f4c";
    private static final String DEBUG_LEVEL_MID = "0x494d";
    public static final String EXTRA_SCREEN_TEXT = "ENABLED";
    public static final String GATE_INTENT_ACTION = "com.sec.android.gate.GATE";
    public static final String GATE_INTENT_EXTRA_ENABLED = "ENABLED";
    public static final String GATE_SYS_PROP_GATE_ENABLED = "service.gate.enabled";
    public static final String GATE_SYS_PROP_LCDTEXT_ENABLED = "service.gate.lcdtexton";
    private static final String LOG_TAG = "GATE";
    private static boolean sGateEnabled = false;
    private static boolean sGateLcdtextEnabled = false;

    public static void setGateEnabled(boolean gateEnabled) {
        sGateEnabled = gateEnabled;
        Log.i(
                LOG_TAG,
                "SemGateConfig.setGateEnabled. GATE = "
                        + sGateEnabled
                        + ", LCDTEXT = "
                        + sGateLcdtextEnabled);
    }

    public static void setGateLcdtextEnabled(boolean lcdTextEnabled) {
        sGateLcdtextEnabled = lcdTextEnabled;
        Log.i(
                LOG_TAG,
                "SemGateConfig.setGateLcdtextEnabled. GATE = "
                        + sGateEnabled
                        + ", LCDTEXT = "
                        + sGateLcdtextEnabled);
    }

    public static boolean isGateEnabled() {
        String debugLevel = String.valueOf(SystemProperties.get("ro.debug_level"));
        if (!"user".equals(Build.TYPE)) {
            return sGateEnabled || SystemProperties.get(GATE_SYS_PROP_GATE_ENABLED).equals("1");
        }
        if (debugLevel.equals(DEBUG_LEVEL_LOW)) {
            return false;
        }
        return SystemProperties.get(GATE_SYS_PROP_GATE_ENABLED).equals("1");
    }

    public static boolean isGateLcdtextEnabled() {
        String debugLevel = String.valueOf(SystemProperties.get("ro.debug_level"));
        if (!"user".equals(Build.TYPE)) {
            return sGateLcdtextEnabled
                    || SystemProperties.get(GATE_SYS_PROP_LCDTEXT_ENABLED).equals("1");
        }
        if (debugLevel.equals(DEBUG_LEVEL_LOW)) {
            return false;
        }
        return SystemProperties.get(GATE_SYS_PROP_LCDTEXT_ENABLED).equals("1");
    }
}
