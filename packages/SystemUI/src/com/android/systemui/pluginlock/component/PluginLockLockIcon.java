package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.util.Log;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginLockLockIcon extends AbstractPluginLockItem {
    private static final String TAG = "PluginLockLockIcon";

    public PluginLockLockIcon(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        Log.d(TAG, "reset()");
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

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
    }
}
