package com.android.server.am;

import android.provider.DeviceConfig;

public final /* synthetic */ class SettingsToPropertiesMapper$$ExternalSyntheticLambda3
        implements DeviceConfig.OnPropertiesChangedListener {
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        com.android.aconfig_new_storage.Flags.enableAconfigStorageDaemon();
    }
}
