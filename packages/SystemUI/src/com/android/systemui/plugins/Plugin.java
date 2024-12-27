package com.android.systemui.plugins;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface Plugin {
    default int getVersion() {
        return -1;
    }

    default void onDestroy() {
    }

    default void onCreate(Context context, Context context2) {
    }
}
