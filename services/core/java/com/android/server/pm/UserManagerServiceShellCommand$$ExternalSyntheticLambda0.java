package com.android.server.pm;

import android.app.ActivityThread;

import com.android.server.power.ShutdownThread;

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
