package com.android.server.ondeviceintelligence;

import android.os.Bundle;
import android.service.ondeviceintelligence.IOnDeviceIntelligenceService;
import android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService;
import android.service.ondeviceintelligence.IProcessingUpdateStatusCallback;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3(
            Bundle bundle, IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) {
        this.f$1 = bundle;
        this.f$0 = iProcessingUpdateStatusCallback;
    }

    public /* synthetic */ OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3(
            OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService, Bundle bundle) {
        this.f$0 = onDeviceIntelligenceManagerService;
        this.f$1 = bundle;
    }

    public /* synthetic */ OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3(
            String str, AndroidFuture androidFuture) {
        this.f$0 = str;
        this.f$1 = androidFuture;
    }

    public final void runNoResult(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService =
                        (OnDeviceIntelligenceManagerService) this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                onDeviceIntelligenceManagerService.getClass();
                ((IOnDeviceSandboxedInferenceService) obj)
                        .updateProcessingState(
                                bundle, new OnDeviceIntelligenceManagerService.AnonymousClass6());
                break;
            case 1:
                ((IOnDeviceSandboxedInferenceService) obj)
                        .updateProcessingState(
                                (Bundle) this.f$1, (IProcessingUpdateStatusCallback) this.f$0);
                break;
            default:
                ((IOnDeviceIntelligenceService) obj)
                        .getReadOnlyFileDescriptor((String) this.f$0, (AndroidFuture) this.f$1);
                break;
        }
    }
}
