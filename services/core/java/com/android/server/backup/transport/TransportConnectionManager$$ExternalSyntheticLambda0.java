package com.android.server.backup.transport;

import android.content.ComponentName;
import android.content.Intent;

import com.android.server.backup.TransportManager;

import java.util.function.Function;

public final /* synthetic */ class TransportConnectionManager$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new Intent(TransportManager.SERVICE_ACTION_TRANSPORT_HOST)
                .setComponent((ComponentName) obj);
    }
}
