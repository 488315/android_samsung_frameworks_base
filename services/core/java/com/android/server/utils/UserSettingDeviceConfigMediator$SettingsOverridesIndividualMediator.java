package com.android.server.utils;

import android.provider.DeviceConfig;
import android.util.KeyValueListParser;

public final class UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator {
    public DeviceConfig.Properties mProperties;
    public final KeyValueListParser mSettingsParser = new KeyValueListParser(',');

    public final boolean getBoolean(String str, boolean z) {
        KeyValueListParser keyValueListParser = this.mSettingsParser;
        DeviceConfig.Properties properties = this.mProperties;
        if (properties != null) {
            z = properties.getBoolean(str, z);
        }
        return keyValueListParser.getBoolean(str, z);
    }

    public final float getFloat(float f, String str) {
        KeyValueListParser keyValueListParser = this.mSettingsParser;
        DeviceConfig.Properties properties = this.mProperties;
        if (properties != null) {
            f = properties.getFloat(str, f);
        }
        return keyValueListParser.getFloat(str, f);
    }

    public final long getLong(long j, String str) {
        KeyValueListParser keyValueListParser = this.mSettingsParser;
        DeviceConfig.Properties properties = this.mProperties;
        if (properties != null) {
            j = properties.getLong(str, j);
        }
        return keyValueListParser.getLong(str, j);
    }
}
