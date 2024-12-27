package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
