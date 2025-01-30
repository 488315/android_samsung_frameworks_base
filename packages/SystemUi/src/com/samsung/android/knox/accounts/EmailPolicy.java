package com.samsung.android.knox.accounts;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.IEmailPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EmailPolicy {
    public static final String ACTION_INTERNAL_MDM_ACCOUNT_DELETE_RESULT = "edm.intent.action.sec.MDM_ACCOUNT_DELETE_RESULT";
    public static final String ACTION_INTERNAL_MDM_ACCOUNT_SETUP_RESULT = "edm.intent.action.sec.MDM_ACCOUNT_SETUP_RESULT";
    public static final String ACTION_MDM_ACCOUNT_DELETE_RESULT_INTERNAL = "com.samsung.android.knox.intent.action.MDM_ACCOUNT_DELETE_RESULT_INTERNAL";
    public static final String ACTION_MDM_ACCOUNT_SETUP_RESULT_INTERNAL = "com.samsung.android.knox.intent.action.MDM_ACCOUNT_SETUP_RESULT_INTERNAL";
    public static final String ACTION_UNLOCK_CREDENTIAL_INTERNAL = "com.android.credentials.UNLOCK";
    public static final int EMAIL_FAIL_BIND_PASSWORD = 1001;
    public static String TAG = "EmailPolicy";
    public final ContextInfo mContextInfo;
    public IEmailPolicy mService;

    public EmailPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean allowAccountAddition(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailPolicy.allowAccountAddition");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowAccountAddition(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public final boolean allowEmailSettingsChange(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailPolicy.allowEmailSettingsChange");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowEmailSettingsChange(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return false;
        }
    }

    public final boolean allowPopImapEmail(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailPolicy.allowPopImapEmail");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowPopImapEmail(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public final boolean getAllowEmailForwarding(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAllowEmailForwarding(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return true;
        }
    }

    public final boolean getAllowHtmlEmail(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAllowHTMLEmail(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return true;
        }
    }

    public final IEmailPolicy getService() {
        if (this.mService == null) {
            this.mService = IEmailPolicy.Stub.asInterface(ServiceManager.getService("email_policy"));
        }
        return this.mService;
    }

    public final boolean isAccountAdditionAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAccountAdditionAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return true;
        }
    }

    public final boolean isEmailNotificationsEnabled(long j) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isEmailNotificationsEnabled(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return true;
        }
    }

    public final boolean isEmailSettingsChangeAllowed(long j) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isEmailSettingsChangeAllowed(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return true;
        }
    }

    public final boolean isPopImapEmailAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPopImapEmailAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return true;
        }
    }

    public final boolean setAllowEmailForwarding(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailPolicy.setAllowEmailForwarding");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAllowEmailForwarding(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return false;
        }
    }

    public final boolean setAllowHtmlEmail(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailPolicy.setAllowHTMLEmail");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAllowHTMLEmail(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return false;
        }
    }

    public final boolean setEmailNotificationsState(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailPolicy.setEmailNotificationsState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setEmailNotificationsState(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email policy", e);
            return false;
        }
    }
}
