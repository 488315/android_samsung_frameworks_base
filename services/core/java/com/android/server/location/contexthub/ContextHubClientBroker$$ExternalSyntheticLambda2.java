package com.android.server.location.contexthub;

import android.hardware.location.IContextHubClientCallback;

public final /* synthetic */ class ContextHubClientBroker$$ExternalSyntheticLambda2
        implements ContextHubClientBroker.CallbackConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ long f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ContextHubClientBroker$$ExternalSyntheticLambda2(int i, int i2, long j) {
        this.$r8$classId = i2;
        this.f$0 = j;
        this.f$1 = i;
    }

    @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
    public final void accept(IContextHubClientCallback iContextHubClientCallback) {
        switch (this.$r8$classId) {
            case 0:
                iContextHubClientCallback.onClientAuthorizationChanged(this.f$0, this.f$1);
                break;
            default:
                iContextHubClientCallback.onNanoAppAborted(this.f$0, this.f$1);
                break;
        }
    }
}
