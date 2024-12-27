package com.android.internal.os;

import android.os.SystemProperties;
import android.sysprop.CryptoProperties;
import android.sysprop.HdmiProperties;

public class RoSystemProperties {
    public static final boolean CEC_AUDIO_DEVICE_FORWARD_VOLUME_KEYS_SYSTEM_AUDIO_MODE_OFF;
    public static final boolean CONFIG_AVOID_GFX_ACCEL;
    public static final boolean CONFIG_LOW_RAM;
    public static final boolean CONFIG_SMALL_BATTERY;
    public static final String CONTROL_PRIVAPP_PERMISSIONS;
    public static final boolean CONTROL_PRIVAPP_PERMISSIONS_DISABLE;
    public static final boolean CONTROL_PRIVAPP_PERMISSIONS_ENFORCE;
    public static final boolean CONTROL_PRIVAPP_PERMISSIONS_LOG;
    public static final boolean CRYPTO_ENCRYPTED;
    public static final boolean CRYPTO_FILE_ENCRYPTED;
    public static final CryptoProperties.state_values CRYPTO_STATE;
    public static final CryptoProperties.type_values CRYPTO_TYPE;
    public static final boolean DEBUGGABLE;
    public static final int FACTORYTEST;
    public static final boolean MULTIUSER_HEADLESS_SYSTEM_USER;
    public static final boolean SUPPORT_ONE_HANDED_MODE;

    static {
        boolean z = false;
        DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
        FACTORYTEST = SystemProperties.getInt("ro.factorytest", 0);
        CONTROL_PRIVAPP_PERMISSIONS = SystemProperties.get("ro.control_privapp_permissions");
        SUPPORT_ONE_HANDED_MODE = SystemProperties.getBoolean("ro.support_one_handed_mode", false);
        CEC_AUDIO_DEVICE_FORWARD_VOLUME_KEYS_SYSTEM_AUDIO_MODE_OFF =
                HdmiProperties.forward_volume_keys_when_system_audio_mode_off()
                        .orElse(false)
                        .booleanValue();
        CONFIG_AVOID_GFX_ACCEL = SystemProperties.getBoolean("ro.config.avoid_gfx_accel", false);
        CONFIG_LOW_RAM = SystemProperties.getBoolean("ro.config.low_ram", false);
        CONFIG_SMALL_BATTERY = SystemProperties.getBoolean("ro.config.small_battery", false);
        MULTIUSER_HEADLESS_SYSTEM_USER =
                SystemProperties.getBoolean("ro.fw.mu.headless_system_user", false);
        CRYPTO_STATE = CryptoProperties.state().orElse(CryptoProperties.state_values.UNSUPPORTED);
        CRYPTO_TYPE = CryptoProperties.type().orElse(CryptoProperties.type_values.NONE);
        CRYPTO_ENCRYPTED = CRYPTO_STATE == CryptoProperties.state_values.ENCRYPTED;
        CRYPTO_FILE_ENCRYPTED = CRYPTO_TYPE == CryptoProperties.type_values.FILE;
        CONTROL_PRIVAPP_PERMISSIONS_LOG = "log".equalsIgnoreCase(CONTROL_PRIVAPP_PERMISSIONS);
        CONTROL_PRIVAPP_PERMISSIONS_ENFORCE =
                "enforce".equalsIgnoreCase(CONTROL_PRIVAPP_PERMISSIONS);
        if (!CONTROL_PRIVAPP_PERMISSIONS_LOG && !CONTROL_PRIVAPP_PERMISSIONS_ENFORCE) {
            z = true;
        }
        CONTROL_PRIVAPP_PERMISSIONS_DISABLE = z;
    }
}
