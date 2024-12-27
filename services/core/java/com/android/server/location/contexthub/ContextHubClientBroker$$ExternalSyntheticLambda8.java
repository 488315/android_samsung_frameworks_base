package com.android.server.location.contexthub;

import android.hardware.location.IContextHubClientCallback;

public final /* synthetic */ class ContextHubClientBroker$$ExternalSyntheticLambda8
        implements ContextHubClientBroker.CallbackConsumer {
    @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
    public final void accept(IContextHubClientCallback iContextHubClientCallback) {
        iContextHubClientCallback.onHubReset();
    }
}
