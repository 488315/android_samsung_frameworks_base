package com.android.server.backup.transport;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.internal.util.Preconditions;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TransportConnectionManager {
    public final Context mContext;
    public final Function mIntentFunction;
    public final Map mTransportClientsCallerMap;
    public int mTransportClientsCreated;
    public final Object mTransportClientsLock;
    public final TransportStats mTransportStats;
    public final int mUserId;

    public TransportConnectionManager(int i, Context context, TransportStats transportStats) {
        TransportConnectionManager$$ExternalSyntheticLambda0
                transportConnectionManager$$ExternalSyntheticLambda0 =
                        new TransportConnectionManager$$ExternalSyntheticLambda0();
        this.mTransportClientsLock = new Object();
        this.mTransportClientsCreated = 0;
        this.mTransportClientsCallerMap = new WeakHashMap();
        this.mUserId = i;
        this.mContext = context;
        this.mTransportStats = transportStats;
        this.mIntentFunction = transportConnectionManager$$ExternalSyntheticLambda0;
    }

    public final void disposeOfTransportClient(
            TransportConnection transportConnection, String str) {
        transportConnection.unbind(str);
        synchronized (transportConnection.mStateLock) {
            Preconditions.checkState(
                    transportConnection.mState < 2, "Can't mark as disposed if still bound");
            transportConnection.mCloseGuard.close();
        }
        synchronized (this.mTransportClientsLock) {
            TransportUtils.log(
                    3,
                    "TransportConnectionManager",
                    TransportUtils.formatMessage(null, str, "Disposing of " + transportConnection));
            ((WeakHashMap) this.mTransportClientsCallerMap).remove(transportConnection);
        }
    }

    public final TransportConnection getTransportClient(
            ComponentName componentName, Bundle bundle, String str) {
        TransportConnection transportConnection;
        Intent intent = (Intent) this.mIntentFunction.apply(componentName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        synchronized (this.mTransportClientsLock) {
            transportConnection =
                    new TransportConnection(
                            this.mUserId,
                            this.mContext,
                            this.mTransportStats,
                            intent,
                            componentName,
                            Integer.toString(this.mTransportClientsCreated),
                            str,
                            new Handler(Looper.getMainLooper()));
            ((WeakHashMap) this.mTransportClientsCallerMap).put(transportConnection, str);
            this.mTransportClientsCreated++;
            TransportUtils.log(
                    3,
                    "TransportConnectionManager",
                    TransportUtils.formatMessage(null, str, "Retrieving " + transportConnection));
        }
        return transportConnection;
    }
}
