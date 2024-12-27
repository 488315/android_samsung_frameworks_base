package com.samsung.android.biometrics.app.setting;

import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class SysUiClientOptions {
    public final Bundle mExtraInfo;
    public final long mOpId;
    public final String mPackageName;
    public PromptInfo mPromptInfo;
    public boolean mRequireTouchBlock;
    public final int mSessionId;
    public final int mType;
    public final int mUserId;

    public SysUiClientOptions(int i, int i2, int i3, long j, String str, Bundle bundle) {
        this.mType = i;
        this.mUserId = i2;
        this.mSessionId = i3;
        this.mOpId = j;
        this.mPackageName = str == null ? "" : str;
        this.mExtraInfo = bundle == null ? new Bundle() : bundle;
    }
}
