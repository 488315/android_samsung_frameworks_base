package com.android.systemui.statusbar.pipeline.wifi.shared;

import android.content.Context;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WifiConstants implements Dumpable {
    public final boolean alwaysShowIconIfEnabled;

    public WifiConstants(Context context, DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("WifiConstants", this);
        this.alwaysShowIconIfEnabled = context.getResources().getBoolean(R.bool.config_showWifiIndicatorWhenEnabled);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("alwaysShowIconIfEnabled="), this.alwaysShowIconIfEnabled, printWriter);
    }
}
