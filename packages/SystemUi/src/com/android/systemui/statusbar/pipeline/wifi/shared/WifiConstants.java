package com.android.systemui.statusbar.pipeline.wifi.shared;

import android.content.Context;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiConstants implements Dumpable {
    public final boolean alwaysShowIconIfEnabled;

    public WifiConstants(Context context, DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("WifiConstants", this);
        this.alwaysShowIconIfEnabled = context.getResources().getBoolean(R.bool.config_showWifiIndicatorWhenEnabled);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("alwaysShowIconIfEnabled="), this.alwaysShowIconIfEnabled, printWriter);
    }
}
