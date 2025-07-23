package androidx.emoji2.text;

import android.os.Handler;
import android.os.Looper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ConcurrencyHelpers$Handler28Impl {
    private ConcurrencyHelpers$Handler28Impl() {
    }

    public static Handler createAsync(Looper looper) {
        return Handler.createAsync(looper);
    }
}
