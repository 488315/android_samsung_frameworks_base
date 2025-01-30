package com.samsung.systemui.splugins;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SPlugin {
    default int getVersion() {
        return -1;
    }

    default void onDestroy() {
    }

    default void onCreate(Context context, Context context2) {
    }
}
