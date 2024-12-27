package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.util.Log;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

public class PluginLockSecure extends AbstractPluginLockItem {
    private static final String TAG = "PluginLockSecure";
    private int mSecureMode;

    public PluginLockSecure(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mSecureMode = 0;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "apply()");
        this.mSecureMode = dynamicLockData2.getCaptureData().getType().intValue();
    }

    public int getSecureMode() {
        return this.mSecureMode;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        Log.d(TAG, "recover()");
        reset(false);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        Log.d(TAG, "reset()");
        if (z) {
            return;
        }
        this.mSecureMode = 0;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "update()");
        apply(dynamicLockData, dynamicLockData2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }
}
