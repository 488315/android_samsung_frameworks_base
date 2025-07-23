package com.android.systemui.statusbar.pipeline.wifi.shared;

import android.content.Context;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WifiConstants implements Dumpable {
    public final boolean alwaysShowIconIfEnabled;

    public WifiConstants(Context context, DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("WifiConstants", this);
        this.alwaysShowIconIfEnabled = context.getResources().getBoolean(R.bool.config_showWifiIndicatorWhenEnabled);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("alwaysShowIconIfEnabled="), this.alwaysShowIconIfEnabled, printWriter);
    }
}
