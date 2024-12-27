package com.android.systemui.slimindicator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.systemui.Dependency;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SlimIndicatorIconBlacklistReceiver extends SlimIndicatorReceiver {
    public final String TAG;

    public SlimIndicatorIconBlacklistReceiver(SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager) {
        super(slimIndicatorSettingsBackUpManager);
        this.TAG = "[QuickStar]SlimIndicatorIconBlacklistReceiver";
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        String schemeSpecificPart = intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;
        if ("com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR".equals(action)) {
            MWBixbyController$$ExternalSyntheticOutline0.m("onReceive - action:", action, ",  pkgName: ", schemeSpecificPart, this.TAG);
            String stringExtra = intent.getStringExtra(SPluginSlimIndicatorModel.INTENT_EXTRA_ICON_BLACKLIST);
            if (TextUtils.isEmpty(stringExtra)) {
                this.mSettingsBackUpManager.getClass();
                ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setIconBlacklist(SPluginSlimIndicatorModel.DB_KEY_DEFAULT_NULL);
            } else {
                this.mSettingsBackUpManager.getClass();
                ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setIconBlacklist(stringExtra);
            }
        }
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void register() {
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(this, this.mFilter, null, UserHandle.ALL, 2, "android.permission.WRITE_SECURE_SETTINGS");
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void setFilter() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR");
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void unregister() {
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this);
    }
}
