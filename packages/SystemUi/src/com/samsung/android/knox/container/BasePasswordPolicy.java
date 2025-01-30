package com.samsung.android.knox.container;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BasePasswordPolicy {
    public static String TAG = "BasePasswordPolicy";
    public ContextInfo mContextInfo;
    public IDevicePolicyManager mDPMService;
    public IPasswordPolicy mService;

    public BasePasswordPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean clearResetPasswordToken(ComponentName componentName) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.clearResetPasswordToken");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearResetPasswordToken(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final int getCurrentFailedPasswordAttempts() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getCurrentFailedPasswordAttempts(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return -1;
        }
    }

    public final IDevicePolicyManager getDPMService() {
        if (this.mDPMService == null) {
            this.mDPMService = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        return this.mDPMService;
    }

    public final int getKeyguardDisabledFeatures(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getKeyguardDisabledFeatures(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getMaximumFailedPasswordsForWipe(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaximumFailedPasswordsForWipe(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final long getMaximumTimeToLock(ComponentName componentName) {
        if (getService() == null) {
            return 0L;
        }
        try {
            return this.mService.getMaximumTimeToLock(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0L;
        }
    }

    public final long getPasswordExpiration(ComponentName componentName) {
        if (getService() == null) {
            return 0L;
        }
        try {
            return this.mService.getPasswordExpiration(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0L;
        }
    }

    public final long getPasswordExpirationTimeout(ComponentName componentName) {
        if (getService() == null) {
            return 0L;
        }
        try {
            return this.mService.getPasswordExpirationTimeout(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0L;
        }
    }

    public final int getPasswordHistoryLength(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordHistoryLength(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final long getPasswordMaximumLength(ComponentName componentName) {
        return 256L;
    }

    public final int getPasswordMinimumLength(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumLength(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordMinimumLetters(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumLetters(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordMinimumLowerCase(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumLowerCase(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordMinimumNonLetter(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumNonLetter(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordMinimumNumeric(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumNumeric(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordMinimumSymbols(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumSymbols(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordMinimumUpperCase(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordMinimumUpperCase(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final int getPasswordQuality(ComponentName componentName) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordQuality(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return 0;
        }
    }

    public final IPasswordPolicy getService() {
        if (this.mService == null) {
            this.mService = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
        }
        return this.mService;
    }

    public final List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2) {
        if (getDPMService() != null) {
            try {
                return this.mDPMService.getTrustAgentConfiguration(componentName, componentName2, UserHandle.getUserId(this.mContextInfo.mCallerUid), false);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
        return new ArrayList();
    }

    public final boolean isActivePasswordSufficient() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isActivePasswordSufficient(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return false;
        }
    }

    public final boolean isResetPasswordTokenActive(ComponentName componentName) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isResetPasswordTokenActive(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final boolean resetPassword(String str, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.resetPassword");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.resetPassword(this.mContextInfo, str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
            return false;
        }
    }

    public final boolean resetPasswordWithToken(ComponentName componentName, String str, byte[] bArr, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.resetPasswordWithToken");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.resetPasswordWithToken(this.mContextInfo, componentName, str, bArr, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final void setKeyguardDisabledFeatures(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setKeyguardDisabledFeatures");
        if (getService() == null || componentName == null) {
            return;
        }
        try {
            this.mService.setKeyguardDisabledFeatures(this.mContextInfo, componentName, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device policy service", e);
        }
    }

    public final void setMaximumFailedPasswordsForWipe(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setMaximumFailedPasswordsForWipe");
        if (getService() == null || componentName == null) {
            return;
        }
        try {
            this.mService.setMaximumFailedPasswordsForWipe(this.mContextInfo, componentName, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
        }
    }

    public final void setMaximumTimeToLock(ComponentName componentName, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setMaximumTimeToLock");
        if (getService() != null) {
            try {
                this.mService.setMaximumTimeToLock(this.mContextInfo, componentName, j);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public final void setPasswordExpirationTimeout(ComponentName componentName, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordExpirationTimeout");
        if (getService() != null) {
            try {
                this.mService.setPasswordExpirationTimeout(this.mContextInfo, componentName, j);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordHistoryLength(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordHistoryLength");
        if (getService() != null) {
            try {
                this.mService.setPasswordHistoryLength(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumLength(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumLength");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumLength(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumLetters(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumLetters");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumLetters(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumLowerCase(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumLowerCase");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumLowerCase(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumNonLetter(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumNonLetter");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumNonLetter(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumNumeric(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumNumeric");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumNumeric(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumSymbols(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumSymbols");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumSymbols(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordMinimumUpperCase(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordMinimumUpperCase");
        if (getService() != null) {
            try {
                this.mService.setPasswordMinimumUpperCase(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final void setPasswordQuality(ComponentName componentName, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setPasswordQuality");
        if (getService() != null) {
            try {
                this.mService.setPasswordQuality(this.mContextInfo, componentName, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with password policy", e);
            }
        }
    }

    public final boolean setResetPasswordToken(ComponentName componentName, byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setResetPasswordToken");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setResetPasswordToken(this.mContextInfo, componentName, bArr);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with password policy", e);
            return false;
        }
    }

    public final void setTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BasePasswordPolicy.setTrustAgentConfiguration");
        if (getService() != null) {
            try {
                getService().setTrustAgentConfiguration(this.mContextInfo, componentName, componentName2, persistableBundle);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }
}
