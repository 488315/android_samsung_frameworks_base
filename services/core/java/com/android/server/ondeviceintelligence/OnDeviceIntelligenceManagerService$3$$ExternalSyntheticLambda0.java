package com.android.server.ondeviceintelligence;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.service.ondeviceintelligence.IProcessingUpdateStatusCallback;

import com.android.internal.infra.AndroidFuture;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$3$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Bundle f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ OnDeviceIntelligenceManagerService$3$$ExternalSyntheticLambda0(
            OnDeviceIntelligenceManagerService.AnonymousClass3 anonymousClass3,
            Bundle bundle,
            IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) {
        this.f$0 = anonymousClass3;
        this.f$1 = bundle;
        this.f$2 = iProcessingUpdateStatusCallback;
    }

    public /* synthetic */ OnDeviceIntelligenceManagerService$3$$ExternalSyntheticLambda0(
            OnDeviceIntelligenceManagerService.AnonymousClass7 anonymousClass7,
            Bundle bundle,
            RemoteCallback remoteCallback) {
        this.f$0 = anonymousClass7;
        this.f$1 = bundle;
        this.f$2 = remoteCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OnDeviceIntelligenceManagerService.AnonymousClass3 anonymousClass3 =
                        (OnDeviceIntelligenceManagerService.AnonymousClass3) this.f$0;
                Bundle bundle = this.f$1;
                IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback =
                        (IProcessingUpdateStatusCallback) this.f$2;
                anonymousClass3.getClass();
                AndroidFuture androidFuture = null;
                try {
                    BundleUtil.sanitizeStateParams(bundle);
                    OnDeviceIntelligenceManagerService.this
                            .ensureRemoteInferenceServiceInitialized();
                    androidFuture =
                            OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.post(
                                    new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3(
                                            bundle, iProcessingUpdateStatusCallback));
                    androidFuture.whenCompleteAsync(
                            new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
                                    3, bundle),
                            OnDeviceIntelligenceManagerService.this.resourceClosingExecutor);
                    return;
                } catch (Throwable th) {
                    if (androidFuture == null) {
                        OnDeviceIntelligenceManagerService.this.resourceClosingExecutor.execute(
                                new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                        3, bundle));
                    }
                    throw th;
                }
            default:
                OnDeviceIntelligenceManagerService.AnonymousClass7 anonymousClass7 =
                        (OnDeviceIntelligenceManagerService.AnonymousClass7) this.f$0;
                Bundle bundle2 = this.f$1;
                RemoteCallback remoteCallback = (RemoteCallback) this.f$2;
                anonymousClass7.getClass();
                if (bundle2 == null) {
                    try {
                        remoteCallback.sendResult((Bundle) null);
                    } catch (Throwable th2) {
                        OnDeviceIntelligenceManagerService.this.resourceClosingExecutor.execute(
                                new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                        4, bundle2));
                        throw th2;
                    }
                }
                Iterator<String> it = bundle2.keySet().iterator();
                while (it.hasNext()) {
                    BundleUtil.validatePfdReadOnly(
                            (ParcelFileDescriptor)
                                    bundle2.getParcelable(it.next(), ParcelFileDescriptor.class));
                }
                remoteCallback.sendResult(bundle2);
                OnDeviceIntelligenceManagerService.this.resourceClosingExecutor.execute(
                        new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                4, bundle2));
                return;
        }
    }
}
