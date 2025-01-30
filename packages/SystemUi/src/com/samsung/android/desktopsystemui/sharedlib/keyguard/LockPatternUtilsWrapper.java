package com.samsung.android.desktopsystemui.sharedlib.keyguard;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LockPatternUtilsWrapper {
    public static final int BIOMETRIC_TYPE_ALL = 257;
    public static final int BIOMETRIC_TYPE_FACE = 256;
    public static final int BIOMETRIC_TYPE_FINGERPRINT = 1;
    public static final int BIOMETRIC_TYPE_NONE = 0;
    private final Context mContext;
    private final LockPatternUtils mLockPatternUtils;

    public LockPatternUtilsWrapper(Context context) {
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public int getBiometricType() {
        return this.mLockPatternUtils.getBiometricType(this.mContext.getUserId());
    }

    public String getPasswordHint() {
        return this.mLockPatternUtils.getPasswordHint(this.mContext.getUserId());
    }

    public int getPinLength() {
        return this.mLockPatternUtils.getPinLength(this.mContext.getUserId());
    }

    public boolean getPowerButtonInstantlyLocks() {
        return this.mLockPatternUtils.getPowerButtonInstantlyLocks(this.mContext.getUserId());
    }

    public boolean isAutoPinConfirmEnabled() {
        return this.mLockPatternUtils.isAutoPinConfirmEnabled(this.mContext.getUserId());
    }

    public boolean isLockScreenDisabled() {
        return this.mLockPatternUtils.isLockScreenDisabled(this.mContext.getUserId());
    }

    public boolean isSecure() {
        return this.mLockPatternUtils.isSecure(this.mContext.getUserId());
    }

    public boolean isVisiblePatternEnabled() {
        return this.mLockPatternUtils.isVisiblePatternEnabled(this.mContext.getUserId());
    }
}
