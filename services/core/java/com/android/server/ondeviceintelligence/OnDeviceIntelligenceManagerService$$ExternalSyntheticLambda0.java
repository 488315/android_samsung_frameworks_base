package com.android.server.ondeviceintelligence;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.DeviceConfig;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda0
        implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OnDeviceIntelligenceManagerService f$0;

    public /* synthetic */ OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda0(
            OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = onDeviceIntelligenceManagerService;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        int i = this.$r8$classId;
        OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService = this.f$0;
        onDeviceIntelligenceManagerService.getClass();
        switch (i) {
            case 0:
                Log.d("OnDeviceIntelligenceManagerService", "sendUpdatedConfig");
                PersistableBundle persistableBundle = new PersistableBundle();
                for (String str : properties.getKeyset()) {
                    persistableBundle.putString(str, properties.getString(str, ""));
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("device_config_update", persistableBundle);
                onDeviceIntelligenceManagerService.ensureRemoteInferenceServiceInitialized();
                onDeviceIntelligenceManagerService.mRemoteInferenceService.run(
                        new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3(
                                onDeviceIntelligenceManagerService, bundle));
                break;
            default:
                if (properties.getKeyset().contains("service_enabled")) {
                    onDeviceIntelligenceManagerService.mIsServiceEnabled =
                            DeviceConfig.getBoolean(
                                    "ondeviceintelligence", "service_enabled", true);
                    break;
                }
                break;
        }
    }
}
