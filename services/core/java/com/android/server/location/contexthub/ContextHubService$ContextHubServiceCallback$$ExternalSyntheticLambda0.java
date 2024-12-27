package com.android.server.location.contexthub;

import java.util.function.Consumer;

public final /* synthetic */
class ContextHubService$ContextHubServiceCallback$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((ContextHubClientBroker) obj).sendHostEndpointConnectedEvent();
    }
}
