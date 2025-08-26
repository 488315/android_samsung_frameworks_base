package androidx.compose.runtime.internal;

import android.os.Looper;

/* loaded from: classes.dex */
public abstract class Thread_androidKt {
    public static final long MainThreadId;

    static {
        long id;
        try {
            id = Looper.getMainLooper().getThread().getId();
        } catch (Exception unused) {
            id = -1;
        }
        MainThreadId = id;
    }
}
