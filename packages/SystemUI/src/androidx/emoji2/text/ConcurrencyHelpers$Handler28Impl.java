package androidx.emoji2.text;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public class ConcurrencyHelpers$Handler28Impl {
    private ConcurrencyHelpers$Handler28Impl() {
    }

    public static Handler createAsync(Looper looper) {
        return Handler.createAsync(looper);
    }
}
