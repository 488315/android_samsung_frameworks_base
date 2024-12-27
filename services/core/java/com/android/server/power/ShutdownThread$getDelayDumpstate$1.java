package com.android.server.power;

public final class ShutdownThread$getDelayDumpstate$1 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        if (!ShutdownThread.BIN_TYPE_PRODUCTSHIP
                && !"recovery".equals(ShutdownThread.mReason)
                && !"recovery-update".equals(ShutdownThread.mReason)) {
            android.util.Slog.i(
                    "ShutdownDelay", "!@ShutdownThread.run() : checking timeout, done.");
            return;
        }
        android.util.Slog.i(
                "ShutdownDelay",
                "!@ShutdownThread.run() : Checking timeout, done. Try force shutdown again.");
        ShutdownThread.rebootOrShutdown(
                ShutdownThread.sInstance.mContext, ShutdownThread.mReason, ShutdownThread.mReboot);
    }
}
