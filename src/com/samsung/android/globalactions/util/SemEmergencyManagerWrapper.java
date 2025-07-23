package com.samsung.android.globalactions.util;

import android.content.Context;
import com.samsung.android.emergencymode.SemEmergencyManager;

/* loaded from: classes6.dex */
public class SemEmergencyManagerWrapper {
    private final Context mContext;
    private SemEmergencyManager mSemEmergencyManager;

    public SemEmergencyManagerWrapper(Context context) {
        this.mContext = context;
        this.mSemEmergencyManager = SemEmergencyManager.getInstance(context);
    }

    public boolean canSetMode() {
        return this.mSemEmergencyManager.canSetMode();
    }

    public boolean isModifying() {
        return this.mSemEmergencyManager.isModifying();
    }

    public boolean isEmergencyMode() {
        return SemEmergencyManager.isEmergencyMode(this.mContext);
    }
}
