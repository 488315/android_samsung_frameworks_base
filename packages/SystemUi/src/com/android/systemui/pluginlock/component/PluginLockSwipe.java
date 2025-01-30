package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.util.Log;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockSwipe extends AbstractPluginLockItem {
    public int mNonSwipeMode;
    public int mNonSwipeModeAngle;

    public PluginLockSwipe(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mNonSwipeMode = 0;
        this.mNonSwipeModeAngle = 45;
    }

    public final void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d("PluginLockSwipe", "apply()");
        if (dynamicLockData == null || !dynamicLockData2.getNonSwipeModeData().equals(dynamicLockData.getNonSwipeModeData())) {
            this.mNonSwipeMode = dynamicLockData2.getNonSwipeModeData().getMode().intValue();
            this.mNonSwipeModeAngle = dynamicLockData2.getNonSwipeModeData().getAngle().intValue();
        }
    }
}
