package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = FalsingPlugin.ACTION, version = 2)
@DependsOn(target = FalsingManager.class)
public interface FalsingPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.FALSING_PLUGIN";
    public static final int VERSION = 2;

    default FalsingManager getFalsingManager(Context context) {
        return null;
    }

    default void dataCollected(boolean z, byte[] bArr) {
    }
}
