package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes6.dex */
public class SemPersonaWrapper {
    Context mContext;
    SemPersonaManager mSemPersonaManager;

    public SemPersonaWrapper(Context context) {
        this.mContext = context;
        this.mSemPersonaManager = (SemPersonaManager) context.getSystemService("persona");
    }

    public boolean isValidVersion() {
        Bundle knoxInfo = SemPersonaManager.getKnoxInfo();
        return knoxInfo != null && "2.0".equals(knoxInfo.getString("version"));
    }

    public boolean isDOProvisioningMode() {
        return SemPersonaManager.isDoEnabled(UserHandle.myUserId());
    }
}
