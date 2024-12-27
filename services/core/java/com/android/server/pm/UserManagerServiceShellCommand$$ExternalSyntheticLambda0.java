package com.android.server.pm;

import android.app.ActivityThread;

import com.android.server.power.ShutdownThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class UserManagerServiceShellCommand$$ExternalSyntheticLambda0
        implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        ShutdownThread.reboot(
                ActivityThread.currentActivityThread().getSystemUiContext(),
                "To switch headless / full system user mode",
                false);
    }
}
