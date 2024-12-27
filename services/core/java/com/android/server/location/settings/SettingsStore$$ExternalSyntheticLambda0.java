package com.android.server.location.settings;

import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SettingsStore$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ CountDownLatch f$0;

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.countDown();
    }
}
