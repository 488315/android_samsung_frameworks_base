package com.samsung.android.widget;

import android.content.Context;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import java.util.List;

/* loaded from: classes6.dex */
public class SemLockPatternUtils {
    private static final String TAG = "SemLockPatternUtils";
    private LockPatternUtils mLockPatternUtils;

    public SemLockPatternUtils(Context context) {
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public boolean isSecure(int userId) {
        return this.mLockPatternUtils.isSecure(userId);
    }

    public int getKeyguardStoredPasswordQuality(int userHandle) {
        return this.mLockPatternUtils.getKeyguardStoredPasswordQuality(userHandle);
    }

    public boolean isLockScreenDisabled(int userId) {
        return this.mLockPatternUtils.isLockScreenDisabled(userId);
    }

    public void setLockScreenDisabled(boolean disable, int userId) {
        this.mLockPatternUtils.setLockScreenDisabled(disable, userId);
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public void clearEncryptionPassword() {
    }

    public boolean isFmmLockEnabled(int userid) {
        return this.mLockPatternUtils.isFMMLockEnabled(userid);
    }

    public boolean isRemoteMobileManagerLockEnabled(int userId) {
        return this.mLockPatternUtils.isRMMLockEnabled(userId);
    }

    public boolean isKnoxguardLockEnabled(int userId) {
        return this.mLockPatternUtils.isKnoxguardLockEnabled(userId);
    }

    public boolean isCarrierLockEnabled(int userId) {
        return this.mLockPatternUtils.isCarrierLockEnabled(userId);
    }

    public int getCredentialTypeForUser(int userId) {
        return this.mLockPatternUtils.getCredentialTypeForUser(userId);
    }

    public boolean setLockCredential(String currentPassword, int currentLockType, String newPassword, int newLockType, int userId) {
        LockscreenCredential currentCredential = createCredential(currentPassword, currentLockType);
        LockscreenCredential newCredential = createCredential(newPassword, newLockType);
        try {
            boolean result = this.mLockPatternUtils.setLockCredential(newCredential, currentCredential, userId);
            return result;
        } catch (Exception e) {
            Log.i(TAG, "setLockCredential : catch exception", e);
            return false;
        }
    }

    public boolean verifyCredential(String password, int userId) {
        int credentialType = this.mLockPatternUtils.getCredentialTypeForUser(userId);
        LockscreenCredential credential = createCredential(password, credentialType);
        if (credential.isNone()) {
            Log.i(TAG, "verifyCredential : credential is none.");
            return false;
        }
        try {
            VerifyCredentialResponse response = this.mLockPatternUtils.verifyCredential(credential, userId, 0);
            if (response.getResponseCode() != 0) {
                Log.i(TAG, "verifyCredential : return " + response.getResponseCode());
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.i(TAG, "verifyCredential : catch exception", e);
            return false;
        }
    }

    public long getLockoutAttemptDeadline(int userId) {
        return this.mLockPatternUtils.getLockoutAttemptDeadline(userId);
    }

    public long getLockoutAttemptTimeout(int userId) {
        return this.mLockPatternUtils.getLockoutAttemptTimeout(userId);
    }

    public boolean clearLock(String currentPassword, int userId) {
        int credentialType = this.mLockPatternUtils.getCredentialTypeForUser(userId);
        LockscreenCredential currentCredential = createCredential(currentPassword, credentialType);
        LockscreenCredential newCredential = LockscreenCredential.createNone();
        try {
            boolean result = this.mLockPatternUtils.setLockCredential(newCredential, currentCredential, userId);
            return result;
        } catch (Exception e) {
            Log.i(TAG, "clearLock : catch exception", e);
            return false;
        }
    }

    private LockscreenCredential createCredential(String password, int credentialType) {
        switch (credentialType) {
            case 1:
                List<LockPatternView.Cell> pattern = LockPatternUtils.byteArrayToPattern(password.getBytes());
                return LockscreenCredential.createPattern(pattern);
            case 2:
            case 4:
                return LockscreenCredential.createPasswordOrNone(password);
            case 3:
                return LockscreenCredential.createPinOrNone(password);
            case 5:
            default:
                Log.i(TAG, "createCredential : wrong credential type : " + credentialType);
                return LockscreenCredential.createNone();
            case 6:
                return LockscreenCredential.createSmartcardPassword(password.getBytes());
        }
    }

    public int getBiometricTypeForUser(int userId) {
        return this.mLockPatternUtils.getBiometricType(userId);
    }

    public int getStrongAuthForUser(int userId) {
        return this.mLockPatternUtils.getStrongAuthForUser(userId);
    }

    public String getDeviceOwnerInfo() {
        return this.mLockPatternUtils.getDeviceOwnerInfo();
    }

    public void setDeviceOwnerInfo(String info) {
        this.mLockPatternUtils.setDeviceOwnerInfo(info);
    }

    public String getOwnerInfo(int userId) {
        return this.mLockPatternUtils.getOwnerInfo(userId);
    }

    public void setOwnerInfo(String info, int userId) {
        this.mLockPatternUtils.setOwnerInfo(info, userId);
    }

    public void setOwnerInfoEnabled(boolean enable, int userId) {
        this.mLockPatternUtils.setOwnerInfoEnabled(enable, userId);
    }
}
