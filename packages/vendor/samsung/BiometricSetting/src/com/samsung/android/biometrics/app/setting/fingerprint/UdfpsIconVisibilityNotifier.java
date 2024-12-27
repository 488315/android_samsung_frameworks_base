package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;

public final class UdfpsIconVisibilityNotifier {
    public final Context mContext;
    public boolean mLastTspVisibilityCommand = false;

    public UdfpsIconVisibilityNotifier(Context context) {
        this.mContext = context;
    }
}
