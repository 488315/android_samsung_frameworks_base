package com.android.server.location.settings;

import java.util.concurrent.CountDownLatch;

public final /* synthetic */ class SettingsStore$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ CountDownLatch f$0;

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.countDown();
    }
}
