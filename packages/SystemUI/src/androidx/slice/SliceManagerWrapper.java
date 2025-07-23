package androidx.slice;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SliceManagerWrapper extends SliceManager {
    public final android.app.slice.SliceManager mManager;

    public SliceManagerWrapper(Context context) {
        this((android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class));
    }

    public SliceManagerWrapper(android.app.slice.SliceManager sliceManager) {
        this.mManager = sliceManager;
    }
}
