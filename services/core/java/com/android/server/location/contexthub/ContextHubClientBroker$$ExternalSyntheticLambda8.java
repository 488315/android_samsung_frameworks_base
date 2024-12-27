package com.android.server.location.contexthub;

import android.hardware.location.IContextHubClientCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ContextHubClientBroker$$ExternalSyntheticLambda8
        implements ContextHubClientBroker.CallbackConsumer {
    @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
    public final void accept(IContextHubClientCallback iContextHubClientCallback) {
        iContextHubClientCallback.onHubReset();
    }
}
