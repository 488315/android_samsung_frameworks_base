package com.android.server.inputmethod;

import android.app.KeyguardManager;
import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemInputMethodManagerServiceUtil {
    public final Context mContext;
    public KeyguardManager mKeyguardManager;
    public final InputMethodManagerService mService;
    public boolean mSpenLastUsed;

    public SemInputMethodManagerServiceUtil(
            Context context, InputMethodManagerService inputMethodManagerService) {
        this.mContext = context;
        this.mService = inputMethodManagerService;
    }

    public final boolean isKeyguardLocked() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager =
                    (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        }
        KeyguardManager keyguardManager = this.mKeyguardManager;
        return keyguardManager != null && keyguardManager.isKeyguardLocked();
    }
}
