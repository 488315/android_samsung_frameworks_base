package com.android.server.enterprise.security;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.util.Slog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.auditlog.AuditLogService;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.accounts.AccountControlInfo;
import com.samsung.android.knox.accounts.IDeviceAccountPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceAccountPolicy extends IDeviceAccountPolicy.Stub implements EnterpriseServiceCallback {
    public static final String[] mSupportedAccountTypes = {"com.samsung.android.email", "com.samsung.android.exchange", "com.samsung.android.ldap", "com.osp.app.signin", "com.google", "com.google.android.gm.legacyimap", "com.google.android.gm.pop3", "com.google.android.gm.exchange", "com.google.work", "com.facebook.auth.login"};
    public AuditLogService mAuditLogService;
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;

    public static boolean checkAccountMatch(Iterator it, String str) {
        while (it.hasNext()) {
            String str2 = (String) it.next();
            try {
            } catch (Exception unused) {
                GmsAlarmManager$$ExternalSyntheticOutline0.m("checkAccountMatch() : invalid matched. match = ", str, " ,target =", str2, "DeviceAccountPolicy");
            }
            if (str.matches(str2)) {
                Slog.d("DeviceAccountPolicy", "checkAccountMatch() : matched. match = " + str + " ,target =" + str2);
                return true;
            }
            continue;
        }
        return false;
    }

    public final boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, list, str, "ADDITION_BLACKLIST");
    }

    public final boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, list, str, "ADDITION_WHITELIST");
    }

    public final boolean addAccountsToBWLInternal(ContextInfo contextInfo, List list, String str, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (!Arrays.asList(mSupportedAccountTypes).contains(str) || list == null) {
            Log.i("DeviceAccountPolicy", "addAccountsToBWLInternal() : invalid parameter.");
            return false;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = list.iterator();
                boolean z = true;
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("adminUid", Integer.valueOf(enforceSecurityPermission.mCallerUid));
                    contentValues.put("type", str);
                    contentValues.put("name", str3);
                    contentValues.put("listType", str2);
                    boolean putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("AccountBlackWhiteList", contentValues);
                    boolean z2 = z && putValuesNoUpdate;
                    if (putValuesNoUpdate) {
                        if (str2.equals("ADDITION_BLACKLIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the addition blocklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        } else if (str2.equals("ADDITION_WHITELIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the addition allowlist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_BLACKLIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the removal blocklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_WHITELIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the removal allowlist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        }
                    }
                    z = z2;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z;
            } catch (Exception e) {
                Log.e("DeviceAccountPolicy", "Handled Exception in addAccountsToBWLInternal()", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, list, str, "REMOVAL_BLACKLIST");
    }

    public final boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, list, str, "REMOVAL_WHITELIST");
    }

    public final boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "ADDITION_BLACKLIST");
    }

    public final boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "ADDITION_WHITELIST");
    }

    public final boolean clearAccountsFromBWLInternal(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        int i = enforceSecurityPermission.mCallerUid;
        String[] strArr = {"adminUid", "type", "listType"};
        String[] strArr2 = {String.valueOf(i), str, str2};
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", Integer.valueOf(i));
                contentValues.put("type", str);
                contentValues.put("listType", str2);
                if (((ArrayList) this.mEdmStorageProvider.getValuesList("AccountBlackWhiteList", strArr, contentValues)).isEmpty()) {
                    Slog.d("DeviceAccountPolicy", "clearAccountsFromBWLInternal() : no accounts.");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("AccountBlackWhiteList", strArr, strArr2);
                if (deleteDataByFields) {
                    if (str2.equals("ADDITION_BLACKLIST")) {
                        getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from addition blocklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), null, callingOrCurrentUserId);
                    } else if (str2.equals("ADDITION_WHITELIST")) {
                        getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from addition allowlist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), null, callingOrCurrentUserId);
                    } else if (str2.equals("REMOVAL_BLACKLIST")) {
                        getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from removal blocklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), null, callingOrCurrentUserId);
                    } else if (str2.equals("REMOVAL_WHITELIST")) {
                        getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from removal allowlist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), null, callingOrCurrentUserId);
                    }
                }
                return deleteDataByFields;
            } catch (Exception e) {
                Log.e("DeviceAccountPolicy", "Handled Exception in clearAccountsFromBWLInternal()", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "REMOVAL_BLACKLIST");
    }

    public final boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "REMOVAL_WHITELIST");
    }

    public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final List getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "ADDITION_BLACKLIST");
    }

    public final List getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "ADDITION_WHITELIST");
    }

    public final List getAccountsFromBWLInternal(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (!Arrays.asList(mSupportedAccountTypes).contains(str)) {
            Log.i("DeviceAccountPolicy", "getAccountsFromBWLInternal() : no support type.");
            return null;
        }
        Map loadAccounts = loadAccounts(Utils.getCallingOrCurrentUserId(enforceSecurityPermission), str, str2);
        if (loadAccounts == null) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m("getAccountsFromBWLInternal() : Account list for ", str2, " is null.", "DeviceAccountPolicy");
            return null;
        }
        HashMap hashMap = (HashMap) loadAccounts;
        Set<Integer> keySet = hashMap.keySet();
        ArrayList arrayList = new ArrayList(keySet.size());
        for (Integer num : keySet) {
            AccountControlInfo accountControlInfo = new AccountControlInfo();
            accountControlInfo.adminPackageName = this.mEdmStorageProvider.getPackageNameForUid(num.intValue());
            accountControlInfo.entries = new ArrayList((Collection) hashMap.get(num));
            arrayList.add(accountControlInfo);
        }
        return arrayList;
    }

    public final List getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "REMOVAL_BLACKLIST");
    }

    public final List getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "REMOVAL_WHITELIST");
    }

    public final AuditLogService getAuditLogService() {
        if (this.mAuditLogService == null) {
            this.mAuditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        }
        return this.mAuditLogService;
    }

    public final List getSupportedAccountTypes() {
        return Arrays.asList(mSupportedAccountTypes);
    }

    public final boolean isAccountAdditionAllowed(String str, String str2, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(Binder.getCallingUid()));
        Map loadAccounts = loadAccounts(callingOrCurrentUserId, str, "ADDITION_BLACKLIST");
        Map loadAccounts2 = loadAccounts(callingOrCurrentUserId, str, "ADDITION_WHITELIST");
        if (loadAccounts == null) {
            Log.i("DeviceAccountPolicy", "isAccountAdditionAllowed() : no BlackList.");
            return true;
        }
        HashMap hashMap = (HashMap) loadAccounts;
        for (Integer num : hashMap.keySet()) {
            if (loadAccounts2 == null || !checkAccountMatch(((Set) ((HashMap) loadAccounts2).get(num)).iterator(), str2)) {
                if (checkAccountMatch(((Set) hashMap.get(num)).iterator(), str2)) {
                    if (z) {
                        RestrictionToastManager.show(R.string.back_button_label);
                    }
                    Slog.d("DeviceAccountPolicy", "isAccountAdditionAllowed() : account has blocked. userId = " + num);
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
        return isAccountRemovalAllowedAsUser(str, str2, z, Utils.getCallingOrCurrentUserId(new ContextInfo(Binder.getCallingUid())));
    }

    public final boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) {
        Map loadAccounts = loadAccounts(i, str, "REMOVAL_BLACKLIST");
        Map loadAccounts2 = loadAccounts(i, str, "REMOVAL_WHITELIST");
        if (loadAccounts == null) {
            Log.i("DeviceAccountPolicy", "isAccountRemovalAllowedAsUser() : no BlackList.");
            return true;
        }
        HashMap hashMap = (HashMap) loadAccounts;
        for (Integer num : hashMap.keySet()) {
            if (loadAccounts2 != null && checkAccountMatch(((Set) ((HashMap) loadAccounts2).get(num)).iterator(), str2)) {
                Slog.d("DeviceAccountPolicy", "isAccountRemovalAllowedAsUser() : no WhiteList.");
            } else if (checkAccountMatch(((Set) hashMap.get(num)).iterator(), str2)) {
                if (z) {
                    RestrictionToastManager.show(R.string.badPin);
                }
                Slog.d("DeviceAccountPolicy", "isAccountRemovalAllowedAsUser() : account has blocked. userId = " + num);
                return false;
            }
        }
        return true;
    }

    public final synchronized Map loadAccounts(int i, String str, String str2) {
        Slog.d("DeviceAccountPolicy", "loadAccounts() : userId  = " + i);
        ArrayList longListAsUser = this.mEdmStorageProvider.getLongListAsUser(i, "AccountBlackWhiteList", "adminUid");
        if (longListAsUser.size() == 0) {
            Log.i("DeviceAccountPolicy", "loadAccounts() : admin is null ");
            return null;
        }
        TreeSet treeSet = new TreeSet(longListAsUser);
        HashMap hashMap = new HashMap(treeSet.size());
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            hashMap.put(Integer.valueOf((int) ((Long) it.next()).longValue()), new TreeSet());
        }
        String[] strArr = {"adminUid", "name"};
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("containerID", (Integer) 0);
            contentValues.put("userID", Integer.valueOf(i));
            contentValues.put("type", str);
            contentValues.put("listType", str2);
            ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesList("AccountBlackWhiteList", strArr, contentValues);
            if (arrayList.isEmpty()) {
                Log.i("DeviceAccountPolicy", "loadAccounts() : list empty ");
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it2.next();
                Long asLong = contentValues2.getAsLong("adminUid");
                if (asLong != null) {
                    ((Set) hashMap.get(Integer.valueOf((int) asLong.longValue()))).add(contentValues2.getAsString("name"));
                } else {
                    Log.i("DeviceAccountPolicy", "loadAccounts() : can not get admin. ");
                }
            }
        } catch (Exception e) {
            Log.e("DeviceAccountPolicy", "Handled Exception in loadAccounts()", e);
        }
        return hashMap;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    public final boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, list, str, "ADDITION_BLACKLIST");
    }

    public final boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, list, str, "ADDITION_WHITELIST");
    }

    public final boolean removeAccountsFromBWLInternal(ContextInfo contextInfo, List list, String str, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        int i = enforceSecurityPermission.mCallerUid;
        if (!Arrays.asList(mSupportedAccountTypes).contains(str) || list == null) {
            Log.i("DeviceAccountPolicy", "removeAccountsFromBWLInternal() : invalid parameter.");
            return false;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = list.iterator();
                boolean z = true;
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("AccountBlackWhiteList", new String[]{"adminUid", "type", "name", "listType"}, new String[]{String.valueOf(i), str, str3, str2});
                    boolean z2 = z && deleteDataByFields;
                    if (deleteDataByFields) {
                        if (str2.equals("ADDITION_BLACKLIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from addition blocklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        } else if (str2.equals("ADDITION_WHITELIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from addition allowlist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_BLACKLIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from removal blocklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_WHITELIST")) {
                            getAuditLogService().redactedAuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from removal allowlist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), null, callingOrCurrentUserId);
                        }
                    }
                    z = z2;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z;
            } catch (Exception e) {
                Log.e("DeviceAccountPolicy", "Handled Exception in removeAccountsFromBWLInternal()", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, list, str, "REMOVAL_BLACKLIST");
    }

    public final boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, list, str, "REMOVAL_WHITELIST");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
    }
}
