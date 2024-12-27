package com.android.server.wearable;

import android.service.wearable.IWearableSensingService;

import com.android.internal.infra.ServiceConnector;

public final /* synthetic */ class RemoteWearableSensingService$$ExternalSyntheticLambda7
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId;

    public final void runNoResult(Object obj) {
        IWearableSensingService iWearableSensingService = (IWearableSensingService) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = RemoteWearableSensingService.$r8$clinit;
                iWearableSensingService.killProcess();
                break;
            case 1:
                int i2 = RemoteWearableSensingService.$r8$clinit;
                iWearableSensingService.onValidatedByHotwordDetectionService();
                break;
            default:
                int i3 = RemoteWearableSensingService.$r8$clinit;
                iWearableSensingService.stopActiveHotwordAudio();
                break;
        }
    }
}
