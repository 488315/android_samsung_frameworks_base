package com.android.server.ondeviceintelligence;

import android.os.Bundle;
import android.os.RemoteCallback;

import com.android.internal.infra.AndroidFuture;

public final /* synthetic */ class OnDeviceIntelligenceManagerService$7$$ExternalSyntheticLambda3
        implements RemoteCallback.OnResultListener {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ RemoteCallback f$1;

    public /* synthetic */ OnDeviceIntelligenceManagerService$7$$ExternalSyntheticLambda3(
            RemoteCallback remoteCallback, AndroidFuture androidFuture) {
        this.f$1 = remoteCallback;
        this.f$0 = androidFuture;
    }

    public /* synthetic */ OnDeviceIntelligenceManagerService$7$$ExternalSyntheticLambda3(
            OnDeviceIntelligenceManagerService.AnonymousClass7 anonymousClass7,
            RemoteCallback remoteCallback) {
        this.f$0 = anonymousClass7;
        this.f$1 = remoteCallback;
    }

    public final void onResult(Bundle bundle) {
        switch (this.$r8$classId) {
            case 0:
                OnDeviceIntelligenceManagerService.AnonymousClass7 anonymousClass7 =
                        (OnDeviceIntelligenceManagerService.AnonymousClass7) this.f$0;
                OnDeviceIntelligenceManagerService.this.callbackExecutor.execute(
                        new OnDeviceIntelligenceManagerService$3$$ExternalSyntheticLambda0(
                                anonymousClass7, bundle, this.f$1));
                break;
            default:
                RemoteCallback remoteCallback = this.f$1;
                AndroidFuture androidFuture = (AndroidFuture) this.f$0;
                remoteCallback.sendResult(bundle);
                androidFuture.complete((Object) null);
                break;
        }
    }
}
