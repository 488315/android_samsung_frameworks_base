package com.android.systemui.statusbar.pipeline.shared;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConnectivityConstants implements Dumpable {
    public final boolean hasDataCapabilities;
    public final boolean shouldShowActivityConfig;

    public ConnectivityConstants(Context context, DumpManager dumpManager, TelephonyManager telephonyManager) {
        dumpManager.registerNormalDumpable("ConnectivityConstants", this);
        this.hasDataCapabilities = telephonyManager.isDataCapable();
        this.shouldShowActivityConfig = context.getResources().getBoolean(R.bool.config_showActivity);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("hasDataCapabilities="), this.hasDataCapabilities, printWriter, "shouldShowActivityConfig="), this.shouldShowActivityConfig, printWriter);
    }
}
