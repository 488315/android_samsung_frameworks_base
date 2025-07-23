package com.samsung.systemui.splugins;

import android.content.Context;
import com.samsung.systemui.splugins.SPlugin;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface SPluginListener<T extends SPlugin> {
    void onPluginConnected(T t, Context context);

    default void onPluginLoadFailed(int i) {
    }

    default void onPluginDisconnected(T t, int i) {
    }
}
