package com.android.systemui.statusbar.pipeline.shared;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public final class ConnectivityConstantsImpl implements ConnectivityConstants, Dumpable {
    public final boolean hasDataCapabilities;
    public final boolean shouldShowActivityConfig;

    public ConnectivityConstantsImpl(Context context, DumpManager dumpManager, TelephonyManager telephonyManager) {
        dumpManager.registerNormalDumpable("ConnectivityConstants", this);
        this.hasDataCapabilities = telephonyManager.isDataCapable();
        this.shouldShowActivityConfig = context.getResources().getBoolean(R.bool.config_showActivity);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("hasDataCapabilities="), this.hasDataCapabilities, printWriter, "shouldShowActivityConfig="), this.shouldShowActivityConfig, printWriter);
    }
}
