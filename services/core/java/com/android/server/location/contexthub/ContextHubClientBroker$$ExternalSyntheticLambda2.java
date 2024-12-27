package com.android.server.location.contexthub;

import android.hardware.location.IContextHubClientCallback;
import com.android.server.location.contexthub.ContextHubClientBroker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ContextHubClientBroker$$ExternalSyntheticLambda2 implements ContextHubClientBroker.CallbackConsumer {
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
