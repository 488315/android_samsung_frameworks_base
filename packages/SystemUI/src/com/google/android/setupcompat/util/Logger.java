package com.google.android.setupcompat.util;

import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public final class Logger {
    public final String prefix;

    public Logger(Class<?> cls) {
        this(cls.getSimpleName());
    }

    public final void atDebug(String str) {
        if (Log.isLoggable("SetupLibrary", 3)) {
            Log.d("SetupLibrary", this.prefix.concat(str));
        }
    }

    public final void atInfo(String str) {
        if (Log.isLoggable("SetupLibrary", 4)) {
            Log.i("SetupLibrary", this.prefix.concat(str));
        }
    }

    public final void e(String str) {
        Log.e("SetupLibrary", this.prefix.concat(str));
    }

    public final void w(String str) {
        Log.w("SetupLibrary", this.prefix.concat(str));
    }

    public Logger(String str) {
        this.prefix = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", str, "] ");
    }

    public final void e(String str, Throwable th) {
        Log.e("SetupLibrary", this.prefix.concat(str), th);
    }
}
