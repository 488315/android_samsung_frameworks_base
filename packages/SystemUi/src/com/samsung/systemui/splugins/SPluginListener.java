package com.samsung.systemui.splugins;

import android.content.Context;
import com.samsung.systemui.splugins.SPlugin;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SPluginListener<T extends SPlugin> {
    void onPluginConnected(T t, Context context);

    default void onPluginLoadFailed(int i) {
    }

    default void onPluginDisconnected(T t, int i) {
    }
}
