package com.android.server.location.contexthub;

import android.app.PendingIntent;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ContextHubClientManager$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ContextHubClientBroker contextHubClientBroker = (ContextHubClientBroker) obj;
        contextHubClientBroker.invokeCallback(
                new ContextHubClientBroker$$ExternalSyntheticLambda8());
        synchronized (contextHubClientBroker) {
            PendingIntent pendingIntent =
                    contextHubClientBroker.mPendingIntentRequest.mPendingIntent;
            if (pendingIntent != null) {
                contextHubClientBroker.doSendPendingIntent(
                        pendingIntent,
                        contextHubClientBroker.createIntent(6),
                        contextHubClientBroker);
            }
        }
        contextHubClientBroker.sendHostEndpointConnectedEvent();
    }
}
