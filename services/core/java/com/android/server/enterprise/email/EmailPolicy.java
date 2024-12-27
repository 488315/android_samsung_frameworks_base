package com.android.server.enterprise.email;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.accounts.IEmailPolicy;
import com.samsung.android.knox.accounts.IExchangeAccountPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class EmailPolicy extends IEmailPolicy.Stub implements EnterpriseServiceCallback {
    public final Context mContext;
    public EnterpriseDeviceManager mEDM = null;
    public final EdmStorageProvider mEdmStorageProvider;
    public final AnonymousClass1 mUserRemovedReceiver;

    public EmailPolicy(Context context) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
                    // com.android.server.enterprise.email.EmailPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                            int intExtra =
                                    intent.getIntExtra("android.intent.extra.user_handle", 0);
                            EmailPolicy emailPolicy = EmailPolicy.this;
                            emailPolicy.getClass();
                            Log.d("EmailPolicyService", "onUserRemoved() userId = " + intExtra);
                            SecContentProviderUtil.notifyPolicyChangesAllUser(
                                    emailPolicy.mContext,
                                    Uri.parse(
                                            "content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
                        }
                    }
                };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        context.registerReceiverAsUser(
                broadcastReceiver,
                UserHandle.ALL,
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED"),
                null,
                null,
                2);
    }

    public static String getSecureAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(64);
        if (lastIndexOf == -1) {
            return "*****";
        }
        return "*****" + str.substring(lastIndexOf);
    }

    public final boolean allowAccountAddition(ContextInfo contextInfo, boolean z) {
        boolean z2 = false;
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        try {
            byte[] bArr = {z ? (byte) 1 : (byte) 0};
            ContentValues contentValues = new ContentValues();
            int i = enforceEmailPermission.mContainerId;
            Context context = SettingsUtils.emails;
            contentValues.put("policyName", "com.samsung.android.email.provider");
            contentValues.put("accountObject", bArr);
            z2 =
                    this.mEdmStorageProvider.putValues(
                            enforceEmailPermission.mCallerUid,
                            enforceEmailPermission.mContainerId,
                            "ADMIN_REF",
                            contentValues);
            Log.i("EmailPolicyService", "allowAccountAddition: " + z2);
            return z2;
        } catch (Exception e) {
            Log.e("EmailPolicyService", "Exception in allowAccountAddition", e);
            return z2;
        }
    }

    public final boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        int i = enforceEmailPermission.mContainerId;
        AccountMetaData accountDetails$1 =
                SettingsUtils.getAccountDetails$1(j, this.mContext, enforceEmailPermission);
        boolean z2 = false;
        if (accountDetails$1 != null && getEDM$12() != null) {
            if (accountDetails$1.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface =
                            IExchangeAccountPolicy.Stub.asInterface(
                                    ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z2 = asInterface.allowEmailSettingsChange(enforceEmailPermission, j, z);
                    }
                } catch (RemoteException e) {
                    Log.e("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z2 =
                        putPolicyState(
                                accountDetails$1.mEmailAddress,
                                enforceEmailPermission.mCallerUid,
                                z,
                                i,
                                "EmailAllowSettingChange");
            }
        }
        Log.i("EmailPolicyService", "allowEmailSettingsChange() : " + z2);
        StringBuilder sb = new StringBuilder("allowEmailSettingsChange() = ");
        sb.append(z2);
        sb.append(" , allow = ");
        sb.append(z);
        sb.append(" , accId = ");
        BatteryService$$ExternalSyntheticOutline0.m(sb, j, "EmailPolicyService");
        return z2;
    }

    public final boolean allowPopImapEmail(ContextInfo contextInfo, boolean z) {
        boolean z2 = false;
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        try {
            byte[] bArr = {z ? (byte) 1 : (byte) 0};
            ContentValues contentValues = new ContentValues();
            contentValues.put("policyName", "allowPopImapEmail");
            contentValues.put("accountObject", bArr);
            z2 =
                    this.mEdmStorageProvider.putValues(
                            enforceEmailPermission.mCallerUid,
                            enforceEmailPermission.mContainerId,
                            "ADMIN_REF",
                            contentValues);
        } catch (Exception e) {
            Log.e("EmailPolicyService", "allowPopImapEmail() : failed.", e);
        }
        Log.i("EmailPolicyService", "allowPopImapEmail() : " + z2);
        Slog.d("EmailPolicyService", "allowPopImapEmail() = " + z2 + " , allowed = " + z);
        return z2;
    }

    public final ContextInfo enforceEmailPermission(ContextInfo contextInfo) {
        return getEDM$12()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList("com.samsung.android.knox.permission.KNOX_EMAIL")));
    }

    public final boolean getAllowEmailForwarding(ContextInfo contextInfo, String str) {
        boolean policyState =
                getPolicyState(
                        contextInfo.mContainerId,
                        Utils.getCallingOrCurrentUserId(contextInfo),
                        str,
                        "EmailAllowForward");
        Log.i("EmailPolicyService", "getAllowEmailForwarding() : " + policyState);
        Slog.d(
                "EmailPolicyService",
                "getAllowEmailForwarding() = "
                        + policyState
                        + " , emailAddress = "
                        + getSecureAddress(str));
        return policyState;
    }

    public final boolean getAllowHTMLEmail(ContextInfo contextInfo, String str) {
        boolean policyState =
                getPolicyState(
                        contextInfo.mContainerId,
                        Utils.getCallingOrCurrentUserId(contextInfo),
                        str,
                        "EmailAllowHTML");
        Log.i("EmailPolicyService", "getAllowHTMLEmail() : " + policyState);
        Slog.d(
                "EmailPolicyService",
                "getAllowHTMLEmail() = "
                        + policyState
                        + " , emailAddress = "
                        + getSecureAddress(str));
        return policyState;
    }

    public final EnterpriseDeviceManager getEDM$12() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final boolean getPolicyState(int i, int i2, String str, String str2) {
        boolean z = true;
        if (!str2.equals("EmailAllowForward")
                && !str2.equals("EmailAllowHTML")
                && !str2.equals("EmailAllowNotificationChange")
                && !str2.equals("EmailAllowSettingChange")) {
            z = false;
        }
        Slog.d("EmailPolicyService", "getDefaultValueState() : ret = " + z + ", policy = " + str2);
        boolean z2 = z ^ true;
        if (str == null || str.isEmpty() || str2.isEmpty()) {
            Log.i("EmailPolicyService", "getPolicyState() : Invalid entry");
            return z;
        }
        if (!SettingsUtils.isValidEmailAddress(str)) {
            Log.i("EmailPolicyService", "getPolicyState() : Invalid entry");
            return z;
        }
        String[] strArr = {str2};
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                i, contentValues, "containerID", "EmailAddress", str);
        contentValues.put("userID", Integer.valueOf(i2));
        try {
            ArrayList arrayList =
                    (ArrayList)
                            this.mEdmStorageProvider.getValuesList(
                                    "EmailSettingsTable", strArr, contentValues);
            if (arrayList.isEmpty()) {
                return z;
            }
            Slog.d("EmailPolicyService", "getPolicyState() : keep going check result. ");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString(str2);
                if (asString != null) {
                    if (asString.equals(z2 ? "1" : "0")) {
                        StringBuilder m =
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                        i,
                                        "getPolicyState() ret = ",
                                        " , containerId = ",
                                        " , emailAddress = ",
                                        z2);
                        m.append(getSecureAddress(str));
                        m.append(" , policy = ");
                        m.append(str2);
                        m.append(" , userId = ");
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                m, i2, "EmailPolicyService");
                        return z2;
                    }
                }
            }
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "getPolicyState() : ret = ", "EmailPolicyService", z);
            return z;
        } catch (Exception e) {
            Log.e(
                    "EmailPolicyService",
                    "getPolicyState() : Exception while getValuesList from EDMStorageProvider",
                    e);
            return z;
        }
    }

    public final boolean isAccountAdditionAllowed(ContextInfo contextInfo) {
        boolean z = true;
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                    EdmStorageProviderBase.getAdminLUIDWhereIn(i, callingOrCurrentUserId),
                    "#SelectClause#");
            Context context = SettingsUtils.emails;
            contentValues.put("policyName", "com.samsung.android.email.provider");
            List blobList =
                    this.mEdmStorageProvider.getBlobList(
                            contentValues, "ADMIN_REF", "accountObject");
            Slog.d("EmailPolicyService", "isAccountAdditionAllowed:  resultList not null");
            ArrayList arrayList = (ArrayList) blobList;
            if (arrayList.size() > 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    if (((byte[]) arrayList.get(i2))[0] == 0) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
        } catch (Exception e) {
            Log.e(
                    "EmailPolicyService",
                    "isAccountAdditionAllowed() : Exception in isAccountAdditionAllowed",
                    e);
        }
        Log.i("EmailPolicyService", "isAccountAdditionAllowed() : " + z);
        Slog.d(
                "EmailPolicyService",
                "isAccountAdditionAllowed() = " + z + " , userId = " + callingOrCurrentUserId);
        return z;
    }

    public final boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) {
        int i = contextInfo.mContainerId;
        AccountMetaData accountDetails$1 =
                SettingsUtils.getAccountDetails$1(j, this.mContext, contextInfo);
        boolean z = true;
        if (accountDetails$1 != null && getEDM$12() != null) {
            if (accountDetails$1.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface =
                            IExchangeAccountPolicy.Stub.asInterface(
                                    ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z = asInterface.isEmailNotificationsEnabled(contextInfo, j);
                    }
                } catch (RemoteException e) {
                    Log.e("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z =
                        getPolicyState(
                                i,
                                Utils.getCallingOrCurrentUserId(contextInfo),
                                accountDetails$1.mEmailAddress,
                                "EmailAllowNotificationChange");
            }
        }
        Log.i("EmailPolicyService", "isEmailNotificationsEnabled() : " + z);
        Slog.d("EmailPolicyService", "isEmailNotificationsEnabled() = " + z + " , accId = " + j);
        return z;
    }

    public final boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) {
        int i = contextInfo.mContainerId;
        AccountMetaData accountDetails$1 =
                SettingsUtils.getAccountDetails$1(j, this.mContext, contextInfo);
        boolean z = true;
        if (accountDetails$1 != null && getEDM$12() != null) {
            if (accountDetails$1.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface =
                            IExchangeAccountPolicy.Stub.asInterface(
                                    ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z = asInterface.isEmailSettingsChangeAllowed(contextInfo, j);
                    }
                } catch (RemoteException e) {
                    Log.w("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z =
                        getPolicyState(
                                i,
                                Utils.getCallingOrCurrentUserId(contextInfo),
                                accountDetails$1.mEmailAddress,
                                "EmailAllowSettingChange");
            }
        }
        Log.i("EmailPolicyService", "isEmailSettingsChangeAllowed() : " + z);
        Slog.d("EmailPolicyService", "isEmailSettingsChangeAllowed() = " + z + " , accId = " + j);
        return z;
    }

    public final boolean isPopImapEmailAllowed(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean z = true;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                    EdmStorageProviderBase.getAdminLUIDWhereIn(i, callingOrCurrentUserId),
                    "#SelectClause#");
            contentValues.put("policyName", "allowPopImapEmail");
            ArrayList arrayList =
                    (ArrayList)
                            this.mEdmStorageProvider.getBlobList(
                                    contentValues, "ADMIN_REF", "accountObject");
            if (arrayList.size() > 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    if (((byte[]) arrayList.get(i2))[0] == 0) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
        } catch (Exception e) {
            Log.e("EmailPolicyService", "Exception in isPopImapEmailAllowed", e);
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isPopImapEmailAllowed() : ", "EmailPolicyService", z);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final boolean putPolicyState(String str, int i, boolean z, int i2, String str2) {
        if (str == null || str.isEmpty()) {
            Log.e("EmailPolicyService", "putPolicyState: Invalid entry");
            return false;
        }
        if (!SettingsUtils.isValidEmailAddress(str)) {
            Log.e("EmailPolicyService", "putPolicyState: Invalid entry");
            return false;
        }
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "adminUid", i2, "containerID");
        contentValues.put("EmailAddress", str);
        contentValues.put(str2, z ? "1" : "0");
        ContentValues contentValues2 = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues2, "adminUid", i2, "containerID");
        contentValues2.put("EmailAddress", str);
        boolean putValues =
                this.mEdmStorageProvider.putValues(
                        "EmailSettingsTable", contentValues, contentValues2);
        Log.i("EmailPolicyService", "putPolicyState() : ret = " + putValues);
        StringBuilder sb = new StringBuilder("putPolicyState() ret = ");
        sb.append(putValues);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                i, i2, " , admin = ", " , containerId = ", sb);
        sb.append(" , emailAddress = ");
        sb.append(getSecureAddress(str));
        sb.append(" , state = ");
        sb.append(z);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                sb, " , policy = ", str2, "EmailPolicyService");
        return putValues;
    }

    public final boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        boolean putPolicyState =
                putPolicyState(
                        str,
                        enforceEmailPermission.mCallerUid,
                        z,
                        enforceEmailPermission.mContainerId,
                        "EmailAllowForward");
        Log.i("EmailPolicyService", "setAllowEmailForwarding() : " + putPolicyState);
        Slog.d(
                "EmailPolicyService",
                "setAllowEmailForwarding() = "
                        + putPolicyState
                        + " , emailAddress = "
                        + getSecureAddress(str)
                        + " , allow = "
                        + z);
        return putPolicyState;
    }

    public final boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        boolean putPolicyState =
                putPolicyState(
                        str,
                        enforceEmailPermission.mCallerUid,
                        z,
                        enforceEmailPermission.mContainerId,
                        "EmailAllowHTML");
        Log.i("EmailPolicyService", "setAllowHTMLEmail() : " + putPolicyState);
        Slog.d(
                "EmailPolicyService",
                "setAllowHTMLEmail() = "
                        + putPolicyState
                        + " , emailAddress = "
                        + getSecureAddress(str)
                        + " , allow = "
                        + z);
        return putPolicyState;
    }

    public final boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        int i = enforceEmailPermission.mContainerId;
        AccountMetaData accountDetails$1 =
                SettingsUtils.getAccountDetails$1(j, this.mContext, enforceEmailPermission);
        boolean z2 = false;
        if (accountDetails$1 != null && getEDM$12() != null) {
            if (accountDetails$1.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface =
                            IExchangeAccountPolicy.Stub.asInterface(
                                    ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z2 = asInterface.setEmailNotificationsState(enforceEmailPermission, j, z);
                    }
                } catch (RemoteException e) {
                    Log.e("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z2 =
                        putPolicyState(
                                accountDetails$1.mEmailAddress,
                                enforceEmailPermission.mCallerUid,
                                z,
                                i,
                                "EmailAllowNotificationChange");
            }
        }
        Log.i("EmailPolicyService", "setEmailNotificationsState() : " + z2);
        StringBuilder sb = new StringBuilder("setEmailNotificationsState() = ");
        sb.append(z2);
        sb.append(" , enable = ");
        sb.append(z);
        sb.append(" , accId = ");
        BatteryService$$ExternalSyntheticOutline0.m(sb, j, "EmailPolicyService");
        if (z2) {
            SecContentProviderUtil.notifyPolicyChangesAllUser(
                    this.mContext,
                    Uri.parse(
                            "content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
        }
        return z2;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        SecContentProviderUtil.notifyPolicyChangesAllUser(
                this.mContext,
                Uri.parse(
                        "content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
    }
}
