package com.android.server.inputmethod;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Slog;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OverlayableSystemBooleanResourceWrapper implements AutoCloseable {
    public final AtomicReference mCleanerRef;
    public final int mUserId;
    public final AtomicBoolean mValueRef;

    public OverlayableSystemBooleanResourceWrapper(
            int i, AtomicBoolean atomicBoolean, AtomicReference atomicReference) {
        this.mUserId = i;
        this.mValueRef = atomicBoolean;
        this.mCleanerRef = atomicReference;
    }

    public static boolean evaluate(Context context) {
        try {
            return context.getPackageManager()
                    .getResourcesForApplication("android")
                    .getBoolean(R.bool.config_keyguardUserSwitcher);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(
                    "OverlayableSystemBooleanResourceWrapper",
                    "getResourcesForApplication(\"android\") failed",
                    e);
            return false;
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        Runnable runnable = (Runnable) this.mCleanerRef.getAndSet(null);
        if (runnable != null) {
            runnable.run();
        }
    }
}
