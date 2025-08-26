package com.samsung.systemui.splugins;

import android.content.Context;
import com.samsung.systemui.splugins.SPlugin;

/* loaded from: classes4.dex */
public interface SPluginListener<T extends SPlugin> {
    void onPluginConnected(T t, Context context);

    default void onPluginLoadFailed(int i) {
    }

    default void onPluginDisconnected(T t, int i) {
    }
}
