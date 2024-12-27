package com.android.server.pm;

import android.util.Slog;

import java.util.concurrent.TimeoutException;

public final /* synthetic */ class InstantAppResolverConnection$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ InstantAppResolverConnection f$0;

    @Override // java.lang.Runnable
    public final void run() {
        InstantAppResolverConnection instantAppResolverConnection = this.f$0;
        instantAppResolverConnection.getClass();
        try {
            if (instantAppResolverConnection.bind("Optimistic Bind") == null
                    || !InstantAppResolverConnection.DEBUG_INSTANT) {
                return;
            }
            Slog.i("PackageManager", "Optimistic bind succeeded.");
        } catch (InstantAppResolverConnection.ConnectionException
                | InterruptedException
                | TimeoutException e) {
            Slog.e("PackageManager", "Optimistic bind failed.", e);
        }
    }
}
