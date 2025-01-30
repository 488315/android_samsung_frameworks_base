package com.google.android.setupcompat.util;

import android.util.Log;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Logger {
    public final String prefix;

    public Logger(Class<?> cls) {
        this(cls.getSimpleName());
    }

    public final void atInfo(String str) {
        if (Log.isLoggable("SetupLibrary", 4)) {
            Log.i("SetupLibrary", this.prefix.concat(str));
        }
    }

    /* renamed from: e */
    public final void m237e(String str) {
        Log.e("SetupLibrary", this.prefix.concat(str));
    }

    /* renamed from: w */
    public final void m239w(String str) {
        Log.w("SetupLibrary", this.prefix.concat(str));
    }

    public Logger(String str) {
        this.prefix = PathParser$$ExternalSyntheticOutline0.m29m("[", str, "] ");
    }

    /* renamed from: e */
    public final void m238e(String str, Throwable th) {
        Log.e("SetupLibrary", this.prefix.concat(str), th);
    }
}
