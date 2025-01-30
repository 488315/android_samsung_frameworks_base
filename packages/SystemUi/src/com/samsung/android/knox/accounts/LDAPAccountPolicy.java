package com.samsung.android.knox.accounts;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.ILDAPAccountPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LDAPAccountPolicy {
    public static final String ACTION_CREATE_LDAPACCOUNT_INTERNAL = "com.samsung.android.knox.intent.action.CREATE_LDAPACCOUNT_INTERNAL";
    public static final String ACTION_LDAP_CREATE_ACCT_RESULT = "com.samsung.android.knox.intent.action.LDAP_CREATE_ACCT_RESULT";
    public static final String ACTION_LDAP_CREATE_ACCT_RESULT_INTERNAL = "com.samsung.android.knox.intent.action.LDAP_CREATE_ACCT_RESULT_INTERNAL";
    public static final int ERROR_LDAP_ALREADY_EXISTS = -1;
    public static final int ERROR_LDAP_CONNECTION = -7;
    public static final int ERROR_LDAP_DOES_NOT_EXIST = -2;
    public static final int ERROR_LDAP_INVALID_CREDENTIALS = -3;
    public static final int ERROR_LDAP_NONE = 0;
    public static final int ERROR_LDAP_SERVER_BUSY = -4;
    public static final int ERROR_LDAP_SERVER_DOWN = -5;
    public static final int ERROR_LDAP_TIMEOUT = -6;
    public static final int ERROR_LDAP_UNKNOWN = -8;
    public static final String EXTRA_LDAP_ACCT_ID = "com.samsung.android.knox.intent.extra.LDAP_ACCT_ID";
    public static final String EXTRA_LDAP_RESULT = "com.samsung.android.knox.intent.extra.LDAP_RESULT";
    public static final String EXTRA_LDAP_USER_ID = "com.samsung.android.knox.intent.extra.LDAP_USER_ID";
    public static String TAG = "LDAPAccountPolicy";
    public ILDAPAccountPolicy lService;
    public final Context mContext;
    public ContextInfo mContextInfo;

    public LDAPAccountPolicy(ContextInfo contextInfo, Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public final void createLDAPAccount(LDAPAccount lDAPAccount) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LDAPAccountPolicy.createLDAPAccount");
        try {
            if (getService() != null) {
                this.lService.createLDAPAccount(this.mContextInfo, lDAPAccount);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to LDAP Settings service ", e);
        }
    }

    public final boolean deleteLDAPAccount(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LDAPAccountPolicy.deleteLDAPAccount");
        try {
            if (getService() != null) {
                return this.lService.deleteLDAPAccount(this.mContextInfo, j);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to LDAP Settings service ", e);
            return false;
        }
    }

    public final List<LDAPAccount> getAllLDAPAccounts() {
        try {
            if (getService() != null) {
                return this.lService.getAllLDAPAccounts(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to LDAP Settings service ", e);
            return null;
        }
    }

    public final LDAPAccount getLDAPAccount(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LDAPAccountPolicy.getLDAPAccount");
        try {
            if (getService() != null) {
                return this.lService.getLDAPAccount(this.mContextInfo, j);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to LDAP Settings service ", e);
            return null;
        }
    }

    public final ILDAPAccountPolicy getService() {
        if (this.lService == null) {
            this.lService = ILDAPAccountPolicy.Stub.asInterface(ServiceManager.getService("ldap_account_policy"));
        }
        return this.lService;
    }
}
