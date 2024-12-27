package com.android.systemui.slimindicator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.Dependency;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SlimIndicatorPackageReceiver extends SlimIndicatorReceiver {
    public final String TAG;

    public SlimIndicatorPackageReceiver(SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager) {
        super(slimIndicatorSettingsBackUpManager);
        this.TAG = "[QuickStar]SlimIndicatorPackageReceiver";
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        String schemeSpecificPart = intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;
        if ("com.samsung.android.goodlock".equals(schemeSpecificPart) || "com.samsung.android.qstuner".equals(schemeSpecificPart) || "com.samsung.systemui.lockstar".equals(schemeSpecificPart)) {
            MWBixbyController$$ExternalSyntheticOutline0.m("onReceive - action:", action, ",  pkgName: ", schemeSpecificPart, this.TAG);
        }
        if (action == null || schemeSpecificPart == null || !"android.intent.action.PACKAGE_REMOVED".equals(action) || !"com.samsung.android.goodlock".equals(schemeSpecificPart)) {
            return;
        }
        this.mSettingsBackUpManager.onPluginDisconnected();
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void register() {
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(this, this.mFilter, null, UserHandle.ALL);
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void setFilter() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        this.mFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        this.mFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        this.mFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void unregister() {
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this);
    }
}
