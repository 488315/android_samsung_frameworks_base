package com.android.internal.config.sysui;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

public class SystemUiSystemPropertiesFlags {
    private static final FlagResolver MAIN_RESOLVER;
    public static final Flag TEAMFOOD = devFlag("persist.sysui.teamfood");
    public static FlagResolver TEST_RESOLVER;

    public interface FlagResolver {
        int getIntValue(Flag flag);

        String getStringValue(Flag flag);

        boolean isEnabled(Flag flag);
    }

    public static final class NotificationFlags {
        public static final Flag LOG_DND_STATE_EVENTS =
                SystemUiSystemPropertiesFlags.releasedFlag(
                        "persist.sysui.notification.log_dnd_state_events");
        public static final Flag RANKING_UPDATE_ASHMEM =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.sysui.notification.ranking_update_ashmem");
        public static final Flag PROPAGATE_CHANNEL_UPDATES_TO_CONVERSATIONS =
                SystemUiSystemPropertiesFlags.releasedFlag(
                        "persist.sysui.notification.propagate_channel_updates_to_conversations");
        public static final Flag NOTIF_COOLDOWN_T1 =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.debug.sysui.notification.notif_cooldown_t1", 60000);
        public static final Flag NOTIF_COOLDOWN_T2 =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.debug.sysui.notification.notif_cooldown_t2", 10000);
        public static final Flag NOTIF_VOLUME1 =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.debug.sysui.notification.notif_volume1", 30);
        public static final Flag NOTIF_VOLUME2 =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.debug.sysui.notification.notif_volume2", 0);
        public static final Flag NOTIF_COOLDOWN_COUNTER_RESET =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.debug.sysui.notification.notif_cooldown_counter_reset", 10);
        public static final Flag NOTIF_AVALANCHE_TIMEOUT =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.debug.sysui.notification.notif_avalanche_timeout", 120000);
        public static final Flag DEBUG_SHORT_BITMAP_DURATION =
                SystemUiSystemPropertiesFlags.devFlag(
                        "persist.sysui.notification.debug_short_bitmap_duration");
    }

    static {
        MAIN_RESOLVER = Build.IS_DEBUGGABLE ? new DebugResolver() : new ProdResolver();
        TEST_RESOLVER = null;
    }

    public static FlagResolver getResolver() {
        if (Build.IS_DEBUGGABLE && TEST_RESOLVER != null) {
            Log.i("SystemUiSystemPropertiesFlags", "Returning debug resolver " + TEST_RESOLVER);
            return TEST_RESOLVER;
        }
        return MAIN_RESOLVER;
    }

    public static Flag devFlag(String name) {
        return new Flag(name, false, (Flag) null);
    }

    public static Flag devFlag(String name, int defaultValue) {
        return new Flag(name, defaultValue, (Flag) null);
    }

    public static Flag devFlag(String name, String defaultValue) {
        return new Flag(name, defaultValue, (Flag) null);
    }

    public static Flag teamfoodFlag(String name) {
        return new Flag(name, false, TEAMFOOD);
    }

    public static Flag releasedFlag(String name) {
        return new Flag(name, true, (Flag) null);
    }

    public static final class Flag {
        public final Flag mDebugDefault;
        public final int mDefaultIntValue;
        public final String mDefaultStringValue;
        public final boolean mDefaultValue;
        public final String mSysPropKey;

        public Flag(String sysPropKey, boolean defaultValue, Flag debugDefault) {
            this.mSysPropKey = sysPropKey;
            this.mDefaultValue = defaultValue;
            this.mDebugDefault = debugDefault;
            this.mDefaultIntValue = 0;
            this.mDefaultStringValue = null;
        }

        public Flag(String sysPropKey, int defaultValue, Flag debugDefault) {
            this.mSysPropKey = sysPropKey;
            this.mDefaultIntValue = defaultValue;
            this.mDebugDefault = debugDefault;
            this.mDefaultValue = false;
            this.mDefaultStringValue = null;
        }

        public Flag(String sysPropKey, String defaultValue, Flag debugDefault) {
            this.mSysPropKey = sysPropKey;
            this.mDefaultStringValue = defaultValue;
            this.mDebugDefault = debugDefault;
            this.mDefaultValue = false;
            this.mDefaultIntValue = 0;
        }
    }

    public static final class ProdResolver implements FlagResolver {
        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public boolean isEnabled(Flag flag) {
            return flag.mDefaultValue;
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public int getIntValue(Flag flag) {
            return flag.mDefaultIntValue;
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public String getStringValue(Flag flag) {
            return flag.mDefaultStringValue;
        }
    }

    public static class DebugResolver implements FlagResolver {
        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public final boolean isEnabled(Flag flag) {
            if (flag.mDebugDefault == null) {
                return getBoolean(flag.mSysPropKey, flag.mDefaultValue);
            }
            return getBoolean(flag.mSysPropKey, isEnabled(flag.mDebugDefault));
        }

        public boolean getBoolean(String key, boolean defaultValue) {
            return SystemProperties.getBoolean(key, defaultValue);
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public int getIntValue(Flag flag) {
            if (flag.mDebugDefault == null) {
                return SystemProperties.getInt(flag.mSysPropKey, flag.mDefaultIntValue);
            }
            return SystemProperties.getInt(flag.mSysPropKey, getIntValue(flag.mDebugDefault));
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public String getStringValue(Flag flag) {
            if (flag.mDebugDefault == null) {
                return SystemProperties.get(flag.mSysPropKey, flag.mDefaultStringValue);
            }
            return SystemProperties.get(flag.mSysPropKey, getStringValue(flag.mDebugDefault));
        }
    }
}
