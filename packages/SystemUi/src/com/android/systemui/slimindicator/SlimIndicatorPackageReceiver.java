package com.android.systemui.slimindicator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            CustomizationProvider$$ExternalSyntheticOutline0.m135m("onReceive - action:", action, ",  pkgName: ", schemeSpecificPart, this.TAG);
        }
        if (action != null && schemeSpecificPart != null && "android.intent.action.PACKAGE_REMOVED".equals(action) && "com.samsung.android.goodlock".equals(schemeSpecificPart)) {
            this.mSettingsBackUpManager.onPluginDisconnected();
        }
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void register() {
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(this, this.mFilter, null, UserHandle.ALL);
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
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this);
    }
}
