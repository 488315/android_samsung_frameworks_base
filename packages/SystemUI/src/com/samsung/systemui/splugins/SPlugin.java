package com.samsung.systemui.splugins;

import android.content.Context;

/* loaded from: classes4.dex */
public interface SPlugin {
    default int getVersion() {
        return -1;
    }

    default void onDestroy() {
    }

    default void onCreate(Context context, Context context2) {
    }
}
