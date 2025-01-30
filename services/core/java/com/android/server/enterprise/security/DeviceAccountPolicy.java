package com.android.server.enterprise.security;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.util.Slog;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.auditlog.AuditLogService;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
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

/* loaded from: classes2.dex */
public class DeviceAccountPolicy extends IDeviceAccountPolicy.Stub implements EnterpriseServiceCallback {
    public static String[] mSupportedAccountTypes = {"com.samsung.android.email", "com.samsung.android.exchange", "com.samsung.android.ldap", "com.osp.app.signin", "com.google", "com.google.android.gm.legacyimap", "com.google.android.gm.pop3", "com.google.android.gm.exchange", "com.google.work", "com.facebook.auth.login"};
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDeviceManager mEDM = null;
    public AuditLogService mAuditLogService = null;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final AuditLogService getAuditLogService() {
        if (this.mAuditLogService == null) {
            this.mAuditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        }
        return this.mAuditLogService;
    }

    public DeviceAccountPolicy(Context context) {
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final boolean checkAccountMatch(Iterator it, String str) {
        while (it.hasNext()) {
            String str2 = (String) it.next();
            try {
            } catch (Exception unused) {
                Slog.d("DeviceAccountPolicy", "checkAccountMatch() : invalid matched. match = " + str + " ,target =" + str2);
            }
            if (str.matches(str2)) {
                Slog.d("DeviceAccountPolicy", "checkAccountMatch() : matched. match = " + str + " ,target =" + str2);
                return true;
            }
            continue;
        }
        return false;
    }

    public final boolean addAccountsToBWLInternal(ContextInfo contextInfo, String str, List list, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (!getSupportedAccountTypes().contains(str) || list == null) {
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
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the addition blacklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
                        } else if (str2.equals("ADDITION_WHITELIST")) {
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the addition whitelist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_BLACKLIST")) {
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the removal blacklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_WHITELIST")) {
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has added account %s to the removal whitelist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
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

    public final boolean removeAccountsFromBWLInternal(ContextInfo contextInfo, String str, List list, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        int i = enforceSecurityPermission.mCallerUid;
        if (!getSupportedAccountTypes().contains(str) || list == null) {
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
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from addition blacklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
                        } else if (str2.equals("ADDITION_WHITELIST")) {
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from addition whitelist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_BLACKLIST")) {
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from removal blacklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
                        } else if (str2.equals("REMOVAL_WHITELIST")) {
                            getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed account %s from removal whitelist.", Integer.valueOf(enforceSecurityPermission.mCallerUid), str3), callingOrCurrentUserId);
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

    public final List getAccountsFromBWLInternal(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        if (!getSupportedAccountTypes().contains(str)) {
            Log.i("DeviceAccountPolicy", "getAccountsFromBWLInternal() : no support type.");
            return null;
        }
        Map loadAccounts = loadAccounts(Utils.getCallingOrCurrentUserId(enforceSecurityPermission), str, str2);
        if (loadAccounts == null) {
            Log.i("DeviceAccountPolicy", "getAccountsFromBWLInternal() : Account list for " + str2 + " is null.");
            return null;
        }
        Set<Integer> keySet = loadAccounts.keySet();
        ArrayList arrayList = new ArrayList(keySet.size());
        for (Integer num : keySet) {
            AccountControlInfo accountControlInfo = new AccountControlInfo();
            accountControlInfo.adminPackageName = getPackageNameForUid(num.intValue());
            accountControlInfo.entries = new ArrayList((Collection) loadAccounts.get(num));
            arrayList.add(accountControlInfo);
        }
        return arrayList;
    }

    public final boolean clearAccountsFromBWLInternal(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo);
        int i = enforceSecurityPermission.mCallerUid;
        String[] strArr = {"adminUid", "type", "listType"};
        String[] strArr2 = {String.valueOf(i), str, str2};
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceSecurityPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("type", str);
            contentValues.put("listType", str2);
            List valuesList = this.mEdmStorageProvider.getValuesList("AccountBlackWhiteList", strArr, contentValues);
            if (valuesList != null && !valuesList.isEmpty()) {
                boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("AccountBlackWhiteList", strArr, strArr2);
                if (deleteDataByFields) {
                    if (str2.equals("ADDITION_BLACKLIST")) {
                        getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from addition blacklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), callingOrCurrentUserId);
                    } else if (str2.equals("ADDITION_WHITELIST")) {
                        getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from addition whitelist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), callingOrCurrentUserId);
                    } else if (str2.equals("REMOVAL_BLACKLIST")) {
                        getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from removal blacklist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), callingOrCurrentUserId);
                    } else if (str2.equals("REMOVAL_WHITELIST")) {
                        getAuditLogService().AuditLoggerAsUser(enforceSecurityPermission, 5, 1, true, Process.myPid(), "DeviceAccountPolicy", String.format("Admin %d has removed all accounts from removal whitelist.", Integer.valueOf(enforceSecurityPermission.mCallerUid)), callingOrCurrentUserId);
                    }
                }
                return deleteDataByFields;
            }
            Slog.d("DeviceAccountPolicy", "clearAccountsFromBWLInternal() : no accounts.");
            return true;
        } catch (Exception e) {
            Log.e("DeviceAccountPolicy", "Handled Exception in clearAccountsFromBWLInternal()", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized Map loadAccounts(int i, String str, String str2) {
        Slog.d("DeviceAccountPolicy", "loadAccounts() : userId  = " + i);
        ArrayList longListAsUser = this.mEdmStorageProvider.getLongListAsUser("AccountBlackWhiteList", "adminUid", i);
        if (longListAsUser.size() == 0) {
            Log.i("DeviceAccountPolicy", "loadAccounts() : admin is null ");
            return null;
        }
        TreeSet treeSet = new TreeSet(longListAsUser);
        HashMap hashMap = new HashMap(treeSet.size());
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            hashMap.put(Integer.valueOf(EdmStorageProviderBase.getAdminUidFromLUID(((Long) it.next()).longValue())), new TreeSet());
        }
        String[] strArr = {"adminUid", "name"};
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("containerID", (Integer) 0);
            contentValues.put("userID", Integer.valueOf(i));
            contentValues.put("type", str);
            contentValues.put("listType", str2);
            List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("AccountBlackWhiteList", strArr, contentValues);
            if (valuesList.isEmpty()) {
                Log.i("DeviceAccountPolicy", "loadAccounts() : list empty ");
            }
            for (ContentValues contentValues2 : valuesList) {
                Long asLong = contentValues2.getAsLong("adminUid");
                if (asLong != null) {
                    ((Set) hashMap.get(Integer.valueOf(EdmStorageProviderBase.getAdminUidFromLUID(asLong.longValue())))).add(contentValues2.getAsString("name"));
                } else {
                    Log.i("DeviceAccountPolicy", "loadAccounts() : can not get admin. ");
                }
            }
        } catch (Exception e) {
            Log.e("DeviceAccountPolicy", "Handled Exception in loadAccounts()", e);
        }
        return hashMap;
    }

    public List getSupportedAccountTypes() {
        return Arrays.asList(mSupportedAccountTypes);
    }

    public boolean addAccountsToRemovalBlackList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, str, list, "REMOVAL_BLACKLIST");
    }

    public boolean removeAccountsFromRemovalBlackList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, str, list, "REMOVAL_BLACKLIST");
    }

    public List getAccountsFromRemovalBlackLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "REMOVAL_BLACKLIST");
    }

    public boolean clearAccountsFromRemovalBlackList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "REMOVAL_BLACKLIST");
    }

    public boolean addAccountsToRemovalWhiteList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, str, list, "REMOVAL_WHITELIST");
    }

    public boolean removeAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, str, list, "REMOVAL_WHITELIST");
    }

    public List getAccountsFromRemovalWhiteLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "REMOVAL_WHITELIST");
    }

    public boolean clearAccountsFromRemovalWhiteList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "REMOVAL_WHITELIST");
    }

    public boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
        return isAccountRemovalAllowedAsUser(str, str2, z, Utils.getCallingOrCurrentUserId(new ContextInfo(Binder.getCallingUid())));
    }

    public boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) {
        Map loadAccounts = loadAccounts(i, str, "REMOVAL_BLACKLIST");
        Map loadAccounts2 = loadAccounts(i, str, "REMOVAL_WHITELIST");
        if (loadAccounts == null) {
            Log.i("DeviceAccountPolicy", "isAccountRemovalAllowedAsUser() : no BlackList.");
            return true;
        }
        for (Integer num : loadAccounts.keySet()) {
            if (loadAccounts2 != null && checkAccountMatch(((Set) loadAccounts2.get(num)).iterator(), str2)) {
                Slog.d("DeviceAccountPolicy", "isAccountRemovalAllowedAsUser() : no WhiteList.");
            } else if (checkAccountMatch(((Set) loadAccounts.get(num)).iterator(), str2)) {
                if (z) {
                    RestrictionToastManager.show(R.string.autofill_save_type_payment_card);
                }
                Slog.d("DeviceAccountPolicy", "isAccountRemovalAllowedAsUser() : account has blocked. userId = " + num);
                return false;
            }
        }
        return true;
    }

    public boolean addAccountsToAdditionBlackList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, str, list, "ADDITION_BLACKLIST");
    }

    public boolean removeAccountsFromAdditionBlackList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, str, list, "ADDITION_BLACKLIST");
    }

    public List getAccountsFromAdditionBlackLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "ADDITION_BLACKLIST");
    }

    public boolean clearAccountsFromAdditionBlackList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "ADDITION_BLACKLIST");
    }

    public boolean addAccountsToAdditionWhiteList(ContextInfo contextInfo, String str, List list) {
        return addAccountsToBWLInternal(contextInfo, str, list, "ADDITION_WHITELIST");
    }

    public boolean removeAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str, List list) {
        return removeAccountsFromBWLInternal(contextInfo, str, list, "ADDITION_WHITELIST");
    }

    public List getAccountsFromAdditionWhiteLists(ContextInfo contextInfo, String str) {
        return getAccountsFromBWLInternal(contextInfo, str, "ADDITION_WHITELIST");
    }

    public boolean clearAccountsFromAdditionWhiteList(ContextInfo contextInfo, String str) {
        return clearAccountsFromBWLInternal(contextInfo, str, "ADDITION_WHITELIST");
    }

    public boolean isAccountAdditionAllowed(String str, String str2, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(Binder.getCallingUid()));
        Map loadAccounts = loadAccounts(callingOrCurrentUserId, str, "ADDITION_BLACKLIST");
        Map loadAccounts2 = loadAccounts(callingOrCurrentUserId, str, "ADDITION_WHITELIST");
        if (loadAccounts == null) {
            Log.i("DeviceAccountPolicy", "isAccountAdditionAllowed() : no BlackList.");
            return true;
        }
        for (Integer num : loadAccounts.keySet()) {
            if (loadAccounts2 == null || !checkAccountMatch(((Set) loadAccounts2.get(num)).iterator(), str2)) {
                if (checkAccountMatch(((Set) loadAccounts.get(num)).iterator(), str2)) {
                    if (z) {
                        RestrictionToastManager.show(R.string.autofill_save_type_password);
                    }
                    Slog.d("DeviceAccountPolicy", "isAccountAdditionAllowed() : account has blocked. userId = " + num);
                    return false;
                }
            }
        }
        return true;
    }

    public final String getPackageNameForUid(int i) {
        return this.mEdmStorageProvider.getPackageNameForUid(i);
    }
}
