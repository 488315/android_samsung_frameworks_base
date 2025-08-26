package com.samsung.android.knox.accounts;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.IEmailAccountPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* loaded from: classes4.dex */
public class EmailAccountPolicy {
    public static final String ACCOUNT_TYPE_IMAP = "imap";
    public static final String ACCOUNT_TYPE_POP3 = "pop3";
    public static final String ACTION_EMAIL_ACCOUNT_ADD_RESULT = "com.samsung.android.knox.intent.action.EMAIL_ACCOUNT_ADD_RESULT";
    public static final String ACTION_EMAIL_ACCOUNT_DELETE_RESULT = "com.samsung.android.knox.intent.action.EMAIL_ACCOUNT_DELETE_RESULT";
    public static final String EXTRA_ACCOUNT_ID = "com.samsung.android.knox.intent.extra.ACCOUNT_ID";
    public static final String EXTRA_EMAIL_ADDRESS = "com.samsung.android.knox.intent.extra.EMAIL_ADDRESS";
    public static final String EXTRA_INCOMING_PROTOCOL = "com.samsung.android.knox.intent.extra.INCOMING_PROTOCOL";
    public static final String EXTRA_INCOMING_SERVER_ADDRESS = "com.samsung.android.knox.intent.extra.INCOMING_SERVER_ADDRESS";
    public static final String EXTRA_RESULT = "com.samsung.android.knox.intent.extra.RESULT";
    public static String TAG = "EmailAccountPolicy";
    public ContextInfo mContextInfo;
    public IEmailAccountPolicy mService = IEmailAccountPolicy.Stub.asInterface(ServiceManager.getService("email_account_policy"));

    public EmailAccountPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public long addNewAccount(EmailAccount emailAccount) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.addNewAccount");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.addNewAccount_new(this.mContextInfo, emailAccount);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return -1L;
        }
    }

    public boolean deleteAccount(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.deleteAccount");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.deleteAccount(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public Account getAccountDetails(long j) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAccountDetails(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return null;
        }
    }

    public long getAccountId(String str, String str2, String str3) {
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.getAccountId(this.mContextInfo, str, str2, str3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return -1L;
        }
    }

    public Account[] getAllEmailAccounts() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAllEmailAccounts(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return null;
        }
    }

    public String getSecurityInComingServerPassword(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.getSecurityInComingServerPassword", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSecurityInComingServerPassword(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return null;
        }
    }

    public String getSecurityOutGoingServerPassword(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.getSecurityOutGoingServerPassword", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSecurityOutGoingServerPassword(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return null;
        }
    }

    public final IEmailAccountPolicy getService() {
        if (this.mService == null) {
            this.mService = IEmailAccountPolicy.Stub.asInterface(ServiceManager.getService("email_account_policy"));
        }
        return this.mService;
    }

    public void removePendingAccount(String str, String str2, String str3) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.removePendingAccount");
        if (getService() != null) {
            try {
                this.mService.removePendingAccount(this.mContextInfo, str, str2, str3);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with email account policy", e);
            }
        }
    }

    public void sendAccountsChangedBroadcast() {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.sendAccountsChangedBroadcast");
        if (getService() != null) {
            try {
                this.mService.sendAccountsChangedBroadcast(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with email account policy", e);
            }
        }
    }

    public boolean setAccountName(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setAccountName");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAccountName(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setAlwaysVibrateOnEmailNotification(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setAlwaysVibrateOnEmailNotification");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAlwaysVibrateOnEmailNotification(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setAsDefaultAccount(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setAsDefaultAccount");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAsDefaultAccount(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setInComingProtocol(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setInComingProtocol");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setInComingProtocol(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setInComingServerAcceptAllCertificates(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setInComingServerAcceptAllCertificates");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setInComingServerAcceptAllCertificates(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public long setInComingServerAddress(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setInComingServerAddress");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setInComingServerAddress(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return -1L;
        }
    }

    public boolean setInComingServerPassword(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setInComingServerPassword");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setInComingServerPassword(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setInComingServerPort(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setInComingServerPort");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setInComingServerPort(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setInComingServerSSL(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setInComingServerSSL");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setInComingServerSSL(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setOutGoingServerAcceptAllCertificates(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setOutGoingServerAcceptAllCertificates");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setOutGoingServerAcceptAllCertificates(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public long setOutGoingServerAddress(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setOutGoingServerAddress");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setOutGoingServerAddress(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return -1L;
        }
    }

    public boolean setOutGoingServerPassword(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setOutGoingServerPassword");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setOutGoingServerPassword(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setOutGoingServerPort(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setOutGoingServerPort");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setOutGoingServerPort(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setOutGoingServerSSL(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setOutGoingServerSSL");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setOutGoingServerSSL(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public long setSecurityInComingServerPassword(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setSecurityInComingServerPassword");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setSecurityInComingServerPassword(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setAccountCertificatePassword ", e);
            return -1L;
        }
    }

    public long setSecurityOutGoingServerPassword(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setSecurityOutGoingServerPassword");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setSecurityOutGoingServerPassword(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setSecurityOutGoingServerPassword ", e);
            return -1L;
        }
    }

    public boolean setSenderName(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setSenderName");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSenderName(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }

    public boolean setSignature(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EmailAccountPolicy.setSignature");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSignature(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with email account policy", e);
            return false;
        }
    }
}
