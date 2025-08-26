package com.samsung.android.knox.accounts;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.IEmailPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* loaded from: classes4.dex */
public class EmailPolicy {
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

    public boolean allowAccountAddition(boolean z) {
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

    public boolean allowEmailSettingsChange(boolean z, long j) {
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

    public boolean allowPopImapEmail(boolean z) {
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

    public boolean getAllowEmailForwarding(String str) {
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

    public boolean getAllowHtmlEmail(String str) {
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

    public boolean isAccountAdditionAllowed() {
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

    public boolean isEmailNotificationsEnabled(long j) {
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

    public boolean isEmailSettingsChangeAllowed(long j) {
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

    public boolean isPopImapEmailAllowed() {
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

    public boolean setAllowEmailForwarding(String str, boolean z) {
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

    public boolean setAllowHtmlEmail(String str, boolean z) {
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

    public boolean setEmailNotificationsState(boolean z, long j) {
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
