package androidx.slice;

import android.content.Context;

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
