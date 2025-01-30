package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
