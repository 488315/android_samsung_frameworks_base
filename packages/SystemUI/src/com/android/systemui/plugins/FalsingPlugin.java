package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = FalsingPlugin.ACTION, version = 2)
@DependsOn(target = FalsingManager.class)
/* loaded from: classes2.dex */
public interface FalsingPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.FALSING_PLUGIN";
    public static final int VERSION = 2;

    default FalsingManager getFalsingManager(Context context) {
        return null;
    }

    default void dataCollected(boolean z, byte[] bArr) {
    }
}
