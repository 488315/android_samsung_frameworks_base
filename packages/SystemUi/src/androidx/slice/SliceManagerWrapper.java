package androidx.slice;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceManagerWrapper extends SliceManager {
    public final android.app.slice.SliceManager mManager;

    public SliceManagerWrapper(Context context) {
        this((android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class));
    }

    public SliceManagerWrapper(android.app.slice.SliceManager sliceManager) {
        this.mManager = sliceManager;
    }
}
