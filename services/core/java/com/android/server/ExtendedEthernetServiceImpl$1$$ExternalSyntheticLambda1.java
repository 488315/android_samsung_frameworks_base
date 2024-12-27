package com.android.server;

import android.os.Handler;

import java.util.concurrent.Executor;

public final /* synthetic */ class ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1
        implements Executor {
    public final /* synthetic */ Handler f$0;

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.f$0.post(runnable);
    }
}
