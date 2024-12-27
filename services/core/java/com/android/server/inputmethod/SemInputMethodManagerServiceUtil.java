package com.android.server.inputmethod;

import android.app.KeyguardManager;
import android.content.Context;

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
