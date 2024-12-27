package com.android.server.am;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SettingsToPropertiesMapper$$ExternalSyntheticLambda3
        implements DeviceConfig.OnPropertiesChangedListener {
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        com.android.aconfig_new_storage.Flags.enableAconfigStorageDaemon();
    }
}
