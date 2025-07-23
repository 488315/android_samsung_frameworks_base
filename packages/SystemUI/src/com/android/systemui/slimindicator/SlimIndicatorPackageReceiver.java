package com.android.systemui.slimindicator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SlimIndicatorPackageReceiver extends SlimIndicatorReceiver {
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
            MediaSessions$H$$ExternalSyntheticOutline0.m("onReceive - action:", action, ",  pkgName: ", schemeSpecificPart, this.TAG);
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
