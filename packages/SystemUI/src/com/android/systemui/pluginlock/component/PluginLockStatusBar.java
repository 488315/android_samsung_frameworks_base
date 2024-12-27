package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.util.Log;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockStatusBar extends AbstractPluginLockItem {
    private static final String TAG = "PluginLockStatusBar";
    private PluginLockStatusBarCallback mCallback;

    public PluginLockStatusBar(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
    }

    private void updateVisibility(DynamicLockData dynamicLockData) {
        boolean isStatusBarIconVisible = dynamicLockData.isStatusBarIconVisible();
        boolean isStatusBarNetworkVisible = dynamicLockData.isStatusBarNetworkVisible();
        PluginLockStatusBarCallback pluginLockStatusBarCallback = this.mCallback;
        if (pluginLockStatusBarCallback != null) {
            pluginLockStatusBarCallback.onVisibilityUpdated(isStatusBarIconVisible ? 0 : 4, isStatusBarNetworkVisible ? 0 : 4);
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "apply()");
        updateVisibility(dynamicLockData2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        Log.d(TAG, "recover()");
        reset(false);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        Log.d(TAG, "reset()");
        PluginLockStatusBarCallback pluginLockStatusBarCallback = this.mCallback;
        if (pluginLockStatusBarCallback != null) {
            pluginLockStatusBarCallback.onVisibilityUpdated(0, 0);
        }
    }

    public void setCallback(PluginLockStatusBarCallback pluginLockStatusBarCallback) {
        this.mCallback = pluginLockStatusBarCallback;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "update()");
        updateVisibility(dynamicLockData2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }
}
