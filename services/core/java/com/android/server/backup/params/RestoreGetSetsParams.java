package com.android.server.backup.params;

import android.app.backup.IRestoreObserver;

import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.restore.ActiveRestoreSession;
import com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda0;
import com.android.server.backup.transport.TransportConnection;

public final class RestoreGetSetsParams {
    public final OnTaskFinishedListener listener;
    public final TransportConnection mTransportConnection;
    public final IRestoreObserver observer;
    public final ActiveRestoreSession session;

    public RestoreGetSetsParams(
            TransportConnection transportConnection,
            ActiveRestoreSession activeRestoreSession,
            IRestoreObserver iRestoreObserver,
            ActiveRestoreSession$$ExternalSyntheticLambda0
                    activeRestoreSession$$ExternalSyntheticLambda0) {
        this.mTransportConnection = transportConnection;
        this.session = activeRestoreSession;
        this.observer = iRestoreObserver;
        this.listener = activeRestoreSession$$ExternalSyntheticLambda0;
    }
}
