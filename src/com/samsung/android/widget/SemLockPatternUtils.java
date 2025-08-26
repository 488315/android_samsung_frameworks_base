package com.samsung.android.widget;

import android.content.Context;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;

/* loaded from: classes6.dex */
public class SemLockPatternUtils {
    private static final String TAG = "SemLockPatternUtils";
    private LockPatternUtils mLockPatternUtils;

    public SemLockPatternUtils(Context context) {
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public boolean isSecure(int i) {
        return this.mLockPatternUtils.isSecure(i);
    }

    public int getKeyguardStoredPasswordQuality(int i) {
        return this.mLockPatternUtils.getKeyguardStoredPasswordQuality(i);
    }

    public boolean isLockScreenDisabled(int i) {
        return this.mLockPatternUtils.isLockScreenDisabled(i);
    }

    public void setLockScreenDisabled(boolean z, int i) {
        this.mLockPatternUtils.setLockScreenDisabled(z, i);
    }

    public boolean isFmmLockEnabled(int i) {
        return this.mLockPatternUtils.isFMMLockEnabled(i);
    }

    public boolean isRemoteMobileManagerLockEnabled(int i) {
        return this.mLockPatternUtils.isRMMLockEnabled(i);
    }

    public boolean isKnoxguardLockEnabled(int i) {
        return this.mLockPatternUtils.isKnoxguardLockEnabled(i);
    }

    public boolean isCarrierLockEnabled(int i) {
        return this.mLockPatternUtils.isCarrierLockEnabled(i);
    }

    public int getCredentialTypeForUser(int i) {
        return this.mLockPatternUtils.getCredentialTypeForUser(i);
    }

    public boolean setLockCredential(String str, int i, String str2, int i2, int i3) {
        LockscreenCredential lockscreenCredentialCreateCredential = createCredential(str, i);
        try {
            return this.mLockPatternUtils.setLockCredential(createCredential(str2, i2), lockscreenCredentialCreateCredential, i3);
        } catch (Exception e) {
            Log.i(TAG, "setLockCredential : catch exception", e);
            return false;
        }
    }

    public boolean verifyCredential(String str, int i) {
        LockscreenCredential lockscreenCredentialCreateCredential = createCredential(str, this.mLockPatternUtils.getCredentialTypeForUser(i));
        if (lockscreenCredentialCreateCredential.isNone()) {
            Log.i(TAG, "verifyCredential : credential is none.");
            return false;
        }
        try {
            VerifyCredentialResponse verifyCredentialResponseVerifyCredential = this.mLockPatternUtils.verifyCredential(lockscreenCredentialCreateCredential, i, 0);
            if (verifyCredentialResponseVerifyCredential.getResponseCode() == 0) {
                return true;
            }
            Log.i(TAG, "verifyCredential : return " + verifyCredentialResponseVerifyCredential.getResponseCode());
            return false;
        } catch (Exception e) {
            Log.i(TAG, "verifyCredential : catch exception", e);
            return false;
        }
    }

    public long getLockoutAttemptDeadline(int i) {
        return this.mLockPatternUtils.getLockoutAttemptDeadline(i);
    }

    public long getLockoutAttemptTimeout(int i) {
        return this.mLockPatternUtils.getLockoutAttemptTimeout(i);
    }

    public boolean clearLock(String str, int i) {
        LockscreenCredential lockscreenCredentialCreateCredential = createCredential(str, this.mLockPatternUtils.getCredentialTypeForUser(i));
        try {
            return this.mLockPatternUtils.setLockCredential(LockscreenCredential.createNone(), lockscreenCredentialCreateCredential, i);
        } catch (Exception e) {
            Log.i(TAG, "clearLock : catch exception", e);
            return false;
        }
    }

    private LockscreenCredential createCredential(String str, int i) {
        if (i == 1) {
            return LockscreenCredential.createPattern(LockPatternUtils.byteArrayToPattern(str.getBytes()));
        }
        if (i != 2) {
            if (i == 3) {
                return LockscreenCredential.createPinOrNone(str);
            }
            if (i != 4) {
                if (i == 6) {
                    return LockscreenCredential.createSmartcardPassword(str.getBytes());
                }
                Log.i(TAG, "createCredential : wrong credential type : " + i);
                return LockscreenCredential.createNone();
            }
        }
        return LockscreenCredential.createPasswordOrNone(str);
    }

    public int getBiometricTypeForUser(int i) {
        return this.mLockPatternUtils.getBiometricType(i);
    }

    public int getStrongAuthForUser(int i) {
        return this.mLockPatternUtils.getStrongAuthForUser(i);
    }

    public String getDeviceOwnerInfo() {
        return this.mLockPatternUtils.getDeviceOwnerInfo();
    }

    public void setDeviceOwnerInfo(String str) {
        this.mLockPatternUtils.setDeviceOwnerInfo(str);
    }

    public String getOwnerInfo(int i) {
        return this.mLockPatternUtils.getOwnerInfo(i);
    }

    public void setOwnerInfo(String str, int i) {
        this.mLockPatternUtils.setOwnerInfo(str, i);
    }

    public void setOwnerInfoEnabled(boolean z, int i) {
        this.mLockPatternUtils.setOwnerInfoEnabled(z, i);
    }
}
