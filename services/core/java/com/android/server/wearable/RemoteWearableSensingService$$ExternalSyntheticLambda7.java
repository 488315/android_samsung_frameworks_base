package com.android.server.wearable;

import android.service.wearable.IWearableSensingService;
import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteWearableSensingService$$ExternalSyntheticLambda7 implements ServiceConnector.VoidJob {
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
