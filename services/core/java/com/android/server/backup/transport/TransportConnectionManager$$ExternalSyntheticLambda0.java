package com.android.server.backup.transport;

import android.content.ComponentName;
import android.content.Intent;

import com.android.server.backup.TransportManager;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TransportConnectionManager$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new Intent(TransportManager.SERVICE_ACTION_TRANSPORT_HOST)
                .setComponent((ComponentName) obj);
    }
}
