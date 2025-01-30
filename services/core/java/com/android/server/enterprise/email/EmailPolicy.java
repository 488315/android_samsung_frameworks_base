package com.android.server.enterprise.email;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.server.enterprise.EnterpriseServiceCallback;
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

/* loaded from: classes2.dex */
public class EmailPolicy extends IEmailPolicy.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDeviceManager mEDM = null;
    public BroadcastReceiver mUserRemovedReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.email.EmailPolicy.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                EmailPolicy.this.onUserRemoved(intent.getIntExtra("android.intent.extra.user_handle", 0));
            }
        }
    };

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceEmailPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EMAIL")));
    }

    public EmailPolicy(Context context) {
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        registerUserRemovedReceiver();
    }

    public boolean allowAccountAddition(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        int i = 1;
        boolean z2 = false;
        try {
            byte[] bArr = new byte[1];
            if (!z) {
                i = 0;
            }
            bArr[0] = (byte) i;
            ContentValues contentValues = new ContentValues();
            contentValues.put("policyName", SettingsUtils.getEmailPackageName(enforceEmailPermission.mContainerId));
            contentValues.put("accountObject", bArr);
            z2 = this.mEdmStorageProvider.putValues(enforceEmailPermission.mCallerUid, enforceEmailPermission.mContainerId, "ADMIN_REF", contentValues);
            Log.i("EmailPolicyService", "allowAccountAddition: " + z2);
            return z2;
        } catch (Exception e) {
            Log.e("EmailPolicyService", "Exception in allowAccountAddition", e);
            return z2;
        }
    }

    public boolean isAccountAdditionAllowed(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean z = true;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(i, callingOrCurrentUserId), "#SelectClause#");
            contentValues.put("policyName", SettingsUtils.getEmailPackageName(i));
            List blobList = this.mEdmStorageProvider.getBlobList("ADMIN_REF", "accountObject", contentValues);
            if (blobList != null) {
                Slog.d("EmailPolicyService", "isAccountAdditionAllowed:  resultList not null");
            }
            if (blobList != null && blobList.size() > 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= blobList.size()) {
                        break;
                    }
                    if (((byte[]) blobList.get(i2))[0] == 0) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
        } catch (Exception e) {
            Log.e("EmailPolicyService", "isAccountAdditionAllowed() : Exception in isAccountAdditionAllowed", e);
        }
        Log.i("EmailPolicyService", "isAccountAdditionAllowed() : " + z);
        Slog.d("EmailPolicyService", "isAccountAdditionAllowed() = " + z + " , userId = " + callingOrCurrentUserId);
        return z;
    }

    public boolean allowPopImapEmail(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        int i = 1;
        boolean z2 = false;
        try {
            byte[] bArr = new byte[1];
            if (!z) {
                i = 0;
            }
            bArr[0] = (byte) i;
            ContentValues contentValues = new ContentValues();
            contentValues.put("policyName", "allowPopImapEmail");
            contentValues.put("accountObject", bArr);
            z2 = this.mEdmStorageProvider.putValues(enforceEmailPermission.mCallerUid, enforceEmailPermission.mContainerId, "ADMIN_REF", contentValues);
        } catch (Exception e) {
            Log.e("EmailPolicyService", "allowPopImapEmail() : failed.", e);
        }
        Log.i("EmailPolicyService", "allowPopImapEmail() : " + z2);
        Slog.d("EmailPolicyService", "allowPopImapEmail() = " + z2 + " , allowed = " + z);
        return z2;
    }

    public boolean isPopImapEmailAllowed(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        boolean z = true;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(i, callingOrCurrentUserId), "#SelectClause#");
            contentValues.put("policyName", "allowPopImapEmail");
            List blobList = this.mEdmStorageProvider.getBlobList("ADMIN_REF", "accountObject", contentValues);
            if (blobList != null && blobList.size() > 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= blobList.size()) {
                        break;
                    }
                    if (((byte[]) blobList.get(i2))[0] == 0) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
        } catch (Exception e) {
            Log.e("EmailPolicyService", "Exception in isPopImapEmailAllowed", e);
        }
        Log.i("EmailPolicyService", "isPopImapEmailAllowed() : " + z);
        return z;
    }

    public boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        boolean putPolicyState = putPolicyState(enforceEmailPermission.mCallerUid, enforceEmailPermission.mContainerId, str, z, "EmailAllowForward");
        Log.i("EmailPolicyService", "setAllowEmailForwarding() : " + putPolicyState);
        Slog.d("EmailPolicyService", "setAllowEmailForwarding() = " + putPolicyState + " , emailAddress = " + getSecureAddress(str) + " , allow = " + z);
        return putPolicyState;
    }

    public boolean getAllowEmailForwarding(ContextInfo contextInfo, String str) {
        boolean policyState = getPolicyState(contextInfo.mContainerId, str, "EmailAllowForward", Utils.getCallingOrCurrentUserId(contextInfo));
        Log.i("EmailPolicyService", "getAllowEmailForwarding() : " + policyState);
        Slog.d("EmailPolicyService", "getAllowEmailForwarding() = " + policyState + " , emailAddress = " + getSecureAddress(str));
        return policyState;
    }

    public boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        boolean putPolicyState = putPolicyState(enforceEmailPermission.mCallerUid, enforceEmailPermission.mContainerId, str, z, "EmailAllowHTML");
        Log.i("EmailPolicyService", "setAllowHTMLEmail() : " + putPolicyState);
        Slog.d("EmailPolicyService", "setAllowHTMLEmail() = " + putPolicyState + " , emailAddress = " + getSecureAddress(str) + " , allow = " + z);
        return putPolicyState;
    }

    public boolean getAllowHTMLEmail(ContextInfo contextInfo, String str) {
        boolean policyState = getPolicyState(contextInfo.mContainerId, str, "EmailAllowHTML", Utils.getCallingOrCurrentUserId(contextInfo));
        Log.i("EmailPolicyService", "getAllowHTMLEmail() : " + policyState);
        Slog.d("EmailPolicyService", "getAllowHTMLEmail() = " + policyState + " , emailAddress = " + getSecureAddress(str));
        return policyState;
    }

    public final boolean putPolicyState(int i, int i2, String str, boolean z, String str2) {
        if (str == null || str.isEmpty()) {
            Log.e("EmailPolicyService", "putPolicyState: Invalid entry");
            return false;
        }
        if (!SettingsUtils.isValidEmailAddress(str)) {
            Log.e("EmailPolicyService", "putPolicyState: Invalid entry");
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("containerID", Integer.valueOf(i2));
        contentValues.put("EmailAddress", str);
        contentValues.put(str2, z ? "1" : "0");
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        contentValues2.put("containerID", Integer.valueOf(i2));
        contentValues2.put("EmailAddress", str);
        boolean putValues = this.mEdmStorageProvider.putValues("EmailSettingsTable", contentValues, contentValues2);
        Log.i("EmailPolicyService", "putPolicyState() : ret = " + putValues);
        Slog.d("EmailPolicyService", "putPolicyState() ret = " + putValues + " , admin = " + i + " , containerId = " + i2 + " , emailAddress = " + getSecureAddress(str) + " , state = " + z + " , policy = " + str2);
        return putValues;
    }

    public final boolean getPolicyState(int i, String str, String str2, int i2) {
        boolean defaultValueState = getDefaultValueState(str2);
        boolean z = !defaultValueState;
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            Log.i("EmailPolicyService", "getPolicyState() : Invalid entry");
            return defaultValueState;
        }
        if (!SettingsUtils.isValidEmailAddress(str)) {
            Log.i("EmailPolicyService", "getPolicyState() : Invalid entry");
            return defaultValueState;
        }
        String[] strArr = {str2};
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("EmailAddress", str);
        contentValues.put("userID", Integer.valueOf(i2));
        try {
            List valuesList = this.mEdmStorageProvider.getValuesList("EmailSettingsTable", strArr, contentValues);
            if (valuesList == null || valuesList.isEmpty()) {
                return defaultValueState;
            }
            Slog.d("EmailPolicyService", "getPolicyState() : keep going check result. ");
            Iterator it = valuesList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString(str2);
                if (asString != null) {
                    if (asString.equals(z ? "1" : "0")) {
                        Slog.d("EmailPolicyService", "getPolicyState() ret = " + z + " , containerId = " + i + " , emailAddress = " + getSecureAddress(str) + " , policy = " + str2 + " , userId = " + i2);
                        return z;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("getPolicyState() : ret = ");
            sb.append(!z);
            Log.i("EmailPolicyService", sb.toString());
            return !z;
        } catch (Exception e) {
            Log.e("EmailPolicyService", "getPolicyState() : Exception while getValuesList from EDMStorageProvider", e);
            return defaultValueState;
        }
    }

    public final boolean getDefaultValueState(String str) {
        if (str != null) {
            r2 = str.equals("EmailAllowForward") || str.equals("EmailAllowHTML") || str.equals("EmailAllowNotificationChange") || str.equals("EmailAllowSettingChange");
            Slog.d("EmailPolicyService", "getDefaultValueState() : ret = " + r2 + ", policy = " + str);
        }
        return r2;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
    }

    public boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        int i = enforceEmailPermission.mContainerId;
        boolean z2 = false;
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailPermission, j, this.mContext, false);
        if (accountDetails != null && getEDM() != null) {
            if (accountDetails.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface = IExchangeAccountPolicy.Stub.asInterface(ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z2 = asInterface.setEmailNotificationsState(enforceEmailPermission, j, z);
                    }
                } catch (RemoteException e) {
                    Log.e("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z2 = putPolicyState(enforceEmailPermission.mCallerUid, i, accountDetails.mEmailAddress, z, "EmailAllowNotificationChange");
            }
        }
        Log.i("EmailPolicyService", "setEmailNotificationsState() : " + z2);
        Slog.d("EmailPolicyService", "setEmailNotificationsState() = " + z2 + " , enable = " + z + " , accId = " + j);
        if (z2) {
            SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
        }
        return z2;
    }

    public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) {
        int i = contextInfo.mContainerId;
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(contextInfo, j, this.mContext, false);
        boolean z = true;
        if (accountDetails != null && getEDM() != null) {
            if (accountDetails.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface = IExchangeAccountPolicy.Stub.asInterface(ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z = asInterface.isEmailNotificationsEnabled(contextInfo, j);
                    }
                } catch (RemoteException e) {
                    Log.e("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z = getPolicyState(i, accountDetails.mEmailAddress, "EmailAllowNotificationChange", Utils.getCallingOrCurrentUserId(contextInfo));
            }
        }
        Log.i("EmailPolicyService", "isEmailNotificationsEnabled() : " + z);
        Slog.d("EmailPolicyService", "isEmailNotificationsEnabled() = " + z + " , accId = " + j);
        return z;
    }

    public boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailPermission = enforceEmailPermission(contextInfo);
        int i = enforceEmailPermission.mContainerId;
        boolean z2 = false;
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailPermission, j, this.mContext, false);
        if (accountDetails != null && getEDM() != null) {
            if (accountDetails.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface = IExchangeAccountPolicy.Stub.asInterface(ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z2 = asInterface.allowEmailSettingsChange(enforceEmailPermission, j, z);
                    }
                } catch (RemoteException e) {
                    Log.e("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z2 = putPolicyState(enforceEmailPermission.mCallerUid, i, accountDetails.mEmailAddress, z, "EmailAllowSettingChange");
            }
        }
        Log.i("EmailPolicyService", "allowEmailSettingsChange() : " + z2);
        Slog.d("EmailPolicyService", "allowEmailSettingsChange() = " + z2 + " , allow = " + z + " , accId = " + j);
        return z2;
    }

    public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) {
        int i = contextInfo.mContainerId;
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(contextInfo, j, this.mContext, false);
        boolean z = true;
        if (accountDetails != null && getEDM() != null) {
            if (accountDetails.mIsEAS) {
                try {
                    IExchangeAccountPolicy asInterface = IExchangeAccountPolicy.Stub.asInterface(ServiceManager.getService("eas_account_policy"));
                    if (asInterface != null) {
                        z = asInterface.isEmailSettingsChangeAllowed(contextInfo, j);
                    }
                } catch (RemoteException e) {
                    Log.w("EmailPolicyService", "Failed talking with exchange account policy", e);
                }
            } else {
                z = getPolicyState(i, accountDetails.mEmailAddress, "EmailAllowSettingChange", Utils.getCallingOrCurrentUserId(contextInfo));
            }
        }
        Log.i("EmailPolicyService", "isEmailSettingsChangeAllowed() : " + z);
        Slog.d("EmailPolicyService", "isEmailSettingsChangeAllowed() = " + z + " , accId = " + j);
        return z;
    }

    public final void registerUserRemovedReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverAsUser(this.mUserRemovedReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public final void onUserRemoved(int i) {
        Log.d("EmailPolicyService", "onUserRemoved() userId = " + i);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/EmailPolicy/isEmailNotificationsEnabled"));
    }

    public String getSecureAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(64);
        if (lastIndexOf == -1) {
            return "*****";
        }
        return "*****" + str.substring(lastIndexOf);
    }
}
