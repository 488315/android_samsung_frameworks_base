package com.android.server.enterprise.email;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseLDAPAccount;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.chimera.genie.GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.accounts.ILDAPAccountPolicy;
import com.samsung.android.knox.accounts.LDAPAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LDAPAccountPolicy extends ILDAPAccountPolicy.Stub
        implements EnterpriseServiceCallback {
    public final Context mContext;
    public EnterpriseDeviceManager mEDM = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LDAPIntentReceiver extends BroadcastReceiver {
        public LDAPIntentReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction()
                    .equals(
                            "com.samsung.android.knox.intent.action.LDAP_CREATE_ACCT_RESULT_INTERNAL")) {
                Log.i(
                        "LDAPAccountPolicyService",
                        "LDAPIntentReceiver: Received intent :"
                            + " ACTION_LDAP_CREATE_ACCT_RESULT_INTERNAL");
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    LDAPAccountPolicy.this.sendBroadcastCreateLDAPAcctResultIntent(
                            extras.getInt("com.samsung.android.knox.intent.extra.LDAP_USER_ID"),
                            extras.getInt("com.samsung.android.knox.intent.extra.LDAP_RESULT"),
                            extras.getLong("com.samsung.android.knox.intent.extra.LDAP_ACCT_ID"));
                }
            }
        }
    }

    public LDAPAccountPolicy(Context context) {
        this.mContext = context;
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(
                    "com.samsung.android.knox.intent.action.LDAP_CREATE_ACCT_RESULT_INTERNAL");
            context.registerReceiverAsUser(
                    new LDAPIntentReceiver(),
                    UserHandle.ALL,
                    intentFilter,
                    "com.samsung.android.knox.permission.KNOX_LDAP",
                    null,
                    2);
            Log.i("LDAPAccountPolicyService", "success to add receiver");
        } catch (Exception e) {
            Log.e("LDAPAccountPolicyService", "Regist BroadCast failed : ", e);
        }
    }

    public final synchronized void createLDAPAccount(
            ContextInfo contextInfo, LDAPAccount lDAPAccount) {
        ContextInfo enforceLDAPPermission = enforceLDAPPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceLDAPPermission);
        int i = enforceLDAPPermission.mContainerId;
        if (lDAPAccount == null) {
            Log.i("LDAPAccountPolicyService", "createLDAPAccount() : failed. ldap is not vaild.");
            return;
        }
        if (!SettingsUtils.isPackageInstalled(i, "com.samsung.android.email.provider")) {
            sendBroadcastCreateLDAPAcctResultIntent(callingOrCurrentUserId, -8, -1L);
            Log.i(
                    "LDAPAccountPolicyService",
                    "createLDAPAccount : Error :: Email app is not installed on user "
                            + callingOrCurrentUserId);
            return;
        }
        if (lDAPAccount.isSSL) {
            lDAPAccount.trustAll = 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.i("LDAPAccountPolicyService", "createLDAPAccount_new()");
        try {
            try {
                long accountEmailPassword$1 =
                        setAccountEmailPassword$1(enforceLDAPPermission, lDAPAccount.password);
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.CREATE_LDAPACCOUNT_INTERNAL");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", lDAPAccount.id);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_NAME_INTERNAL",
                        lDAPAccount.userName);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_PASSWORD_ID_INTERNAL",
                        accountEmailPassword$1);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PORT_INTERNAL", lDAPAccount.port);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.IS_SSL_INTERNAL", lDAPAccount.isSSL);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "ldap");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.IS_ANONYMOUS_INTERNAL",
                        lDAPAccount.isAnonymous);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.HOST_INTERNAL", lDAPAccount.host);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.BASE_DN_INTERNAL",
                        lDAPAccount.baseDN);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL",
                        lDAPAccount.trustAll);
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                Log.i(
                        "LDAPAccountPolicyService",
                        "createLDAPAccount_new() : successfully sent intent to Email app. ");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e) {
            Log.e(
                    "LDAPAccountPolicyService",
                    "createLDAPAccount_new() : unexpected Exception occurs. ",
                    e);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            sendBroadcastCreateLDAPAcctResultIntent(callingOrCurrentUserId, -8, -1L);
            Log.i("LDAPAccountPolicyService", "createLDAPAccount() : failed with unknown error.");
        }
    }

    public final synchronized boolean deleteLDAPAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceLDAPPermission = enforceLDAPPermission(contextInfo);
        LDAPAccount lDAPAccount = getLDAPAccount(enforceLDAPPermission, j);
        boolean z = false;
        if (lDAPAccount == null) {
            Log.i("LDAPAccountPolicyService", "deleteLDAPAccount() : ldapId is invalid");
            return false;
        }
        DeviceAccountPolicy deviceAccountPolicy =
                (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        if (deviceAccountPolicy != null) {
            if (!deviceAccountPolicy.isAccountRemovalAllowed(
                    "com.samsung.android.ldap",
                    lDAPAccount.userName + "@" + lDAPAccount.host,
                    false)) {
                Log.i(
                        "LDAPAccountPolicyService",
                        "deleteLDAPAccount() : MDM DeviceAccountPolicy restriction - cannot delete"
                            + " account : "
                                + j);
                return false;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = EmailProviderHelper.deleteLDAPAccount(j, this.mContext, enforceLDAPPermission);
            } catch (Exception e) {
                Log.e(
                        "LDAPAccountPolicyService",
                        "deleteLDAPAccount() : Failed, Exception occurs. ",
                        e);
            }
            Log.i("LDAPAccountPolicyService", "deleteLDAPAccount() : id = " + j + ", ret = " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ContextInfo enforceLDAPPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_LDAP")));
    }

    public final List getAllLDAPAccounts(ContextInfo contextInfo) {
        Log.i("LDAPAccountPolicyService", "getAllLDAPAccounts() ");
        ContextInfo enforceLDAPPermission = enforceLDAPPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ArrayList arrayList = null;
        try {
            try {
                List allLDAPAccount =
                        EmailProviderHelper.getAllLDAPAccount(this.mContext, enforceLDAPPermission);
                if (allLDAPAccount != null) {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = allLDAPAccount.iterator();
                    while (it.hasNext()) {
                        LDAPAccount lDAPAccountFromEnterpriseLDAPAccount =
                                SettingsUtils.getLDAPAccountFromEnterpriseLDAPAccount(
                                        (EnterpriseLDAPAccount) it.next());
                        if (lDAPAccountFromEnterpriseLDAPAccount != null) {
                            arrayList2.add(lDAPAccountFromEnterpriseLDAPAccount);
                        }
                    }
                    arrayList = arrayList2;
                }
            } catch (Exception e) {
                Log.e(
                        "LDAPAccountPolicyService",
                        "getAllLDAPAccounts() : Failed, Exception occurs. ",
                        e);
            }
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "LDAPAccountPolicyService",
                    new StringBuilder("getAllLDAPAccounts() ret = "),
                    arrayList != null);
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceLDAPPermission = enforceLDAPPermission(contextInfo);
        LDAPAccount lDAPAccount = null;
        if (j < 1) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "getLDAPAccount() : ldapId is invalid, id = ", j, "LDAPAccountPolicyService");
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                lDAPAccount =
                        SettingsUtils.getLDAPAccountFromEnterpriseLDAPAccount(
                                EmailProviderHelper.getEnterpriseLDAPAccount(
                                        j, this.mContext, enforceLDAPPermission));
            } catch (Exception e) {
                Log.e(
                        "LDAPAccountPolicyService",
                        "getLDAPAccount() : Failed, Exception occurs. ",
                        e);
            }
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "LDAPAccountPolicyService",
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "getLDAPAccount() id = ", j, ", ret ="),
                    lDAPAccount == null);
            return lDAPAccount;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void sendBroadcastCreateLDAPAcctResultIntent(int i, int i2, long j) {
        Intent intent =
                new Intent("com.samsung.android.knox.intent.action.LDAP_CREATE_ACCT_RESULT");
        intent.putExtra("com.samsung.android.knox.intent.extra.LDAP_RESULT", i2);
        intent.putExtra("com.samsung.android.knox.intent.extra.LDAP_ACCT_ID", j);
        intent.putExtra("com.samsung.android.knox.intent.extra.LDAP_USER_ID", i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mContext.sendBroadcastAsUser(
                        intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_LDAP");
                Log.i(
                        "LDAPAccountPolicyService",
                        "sendBroadcastCreateLDAPAcctResultIntent() success to send result Intent 2."
                            + " ");
            } catch (Exception e) {
                Log.e(
                        "LDAPAccountPolicyService",
                        "sendBroadcastCreateLDAPAcctResultIntent() failed to send result Intent 2."
                            + " ",
                        e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final long setAccountEmailPassword$1(ContextInfo contextInfo, String str) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        int i =
                this.mEDM.enforcePermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EXCHANGE")))
                        .mContainerId;
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            return -1L;
        }
        try {
            SettingsUtils.setSecurityPassword("E#" + createIDforAccount, str);
            return createIDforAccount;
        } catch (Exception e) {
            Log.e("LDAPAccountPolicyService", "setAccountEmailPassword() failed", e);
            return -1L;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        Log.i("LDAPAccountPolicyService", "systemReady()... ");
    }
}
