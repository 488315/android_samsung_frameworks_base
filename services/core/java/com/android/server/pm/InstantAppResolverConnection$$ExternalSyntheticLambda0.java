package com.android.server.pm;

import android.util.Slog;

import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
