package com.android.server.enterprise.container;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.IRCPPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class KnoxMUMRCPPolicyService extends IRCPPolicy.Stub implements EnterpriseServiceCallback {
    public static final boolean isEngMode = "eng".equals(SystemProperties.get("ro.build.type"));
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDeviceManager mEDM = null;
    public List providerList1 = null;
    public List providerList2 = null;

    public boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) {
        return true;
    }

    public boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) {
        return true;
    }

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

    public KnoxMUMRCPPolicyService(Context context) {
        this.mEdmStorageProvider = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo, List list) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, list);
    }

    public final void enforceSystemUid() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Not a system service. This API only allowed by SYSTEM_UID");
        }
    }

    public final String getDefaultRCPPolicy(int i, String str) {
        return ((SemPersonaManager) this.mContext.getSystemService("persona")).exists(i) ? "false" : "true";
    }

    public final String getPropertyValue(String str, int i, String str2, String str3) {
        ArrayList adminLUidListAsUser;
        enforceSystemUid();
        if (str2 == null || str2.isEmpty() || str3 == null || str3.isEmpty() || i == 0 || (adminLUidListAsUser = this.mEdmStorageProvider.getAdminLUidListAsUser(i)) == null || adminLUidListAsUser.isEmpty()) {
            return null;
        }
        String[] strArr = {"propertyValue"};
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str2);
        contentValues.put("propertyName", str3);
        Iterator it = adminLUidListAsUser.iterator();
        while (it.hasNext()) {
            contentValues.put("adminUid", (Long) it.next());
            List valuesList = this.mEdmStorageProvider.getValuesList(str, strArr, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                return ((ContentValues) valuesList.get(0)).getAsString("propertyValue");
            }
        }
        return getDefaultRCPPolicy(i, str3);
    }

    public final List getListFromSyncPolicy(ContextInfo contextInfo, String str, String str2, String str3) {
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER_RCP")));
        if (isEngMode) {
            Log.d("KnoxMUMRCPPolicyService", "admin uid from context info: " + enforceSecurityPermission.mCallerUid);
        }
        if (str2 != null && !str2.isEmpty() && str3 != null && !str3.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(enforceSecurityPermission.mCallerUid));
            contentValues.put("propertyName", str2);
            contentValues.put("propertyValue", str3);
            List valuesList = this.mEdmStorageProvider.getValuesList(str, new String[]{"name"}, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                ArrayList arrayList = new ArrayList();
                Iterator it = valuesList.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ContentValues) it.next()).getAsString("name"));
                }
                return arrayList;
            }
        }
        return null;
    }

    public boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List list, String str, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "setAllowChangeDataSyncPolicy");
        return false;
    }

    public boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2) {
        int userId;
        Log.d("KnoxMUMRCPPolicyService", "getAllowChangeDataSyncPolicy");
        if (contextInfo != null) {
            userId = contextInfo.mContainerId;
        } else {
            userId = UserHandle.getUserId(Binder.getCallingUid());
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String propertyValue = getPropertyValue("RCP_DATA", userId, str, str2);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (propertyValue == null) {
            return false;
        }
        return Boolean.parseBoolean(propertyValue);
    }

    public List getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "getListFromAllowChangeDataSyncPolicy");
        return getListFromSyncPolicy(contextInfo, "RCP_DATA", str, Boolean.toString(z));
    }

    public boolean setNotificationSyncPolicy(ContextInfo contextInfo, List list, String str, String str2) {
        Log.d("KnoxMUMRCPPolicyService", "setNotificationSyncPolicy");
        return false;
    }

    public String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) {
        Log.d("KnoxMUMRCPPolicyService", "getNotificationSyncPolicy");
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String propertyValue = getPropertyValue("RCP_NOTIFICATION", userId, str, str2);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return propertyValue;
    }

    public List getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) {
        return getListFromSyncPolicy(contextInfo, "RCP_NOTIFICATION", str, str2);
    }

    public final int isMigrationStateSet(int i) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "rcp_profile_migration_completed", 0, i);
    }

    public boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) {
        Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed ........ ");
        if (contextInfo == null) {
            Log.d("KnoxMUMRCPPolicyService", "received ContextInfo equals null");
            return false;
        }
        if (isMigrationStateSet(contextInfo.mContainerId) == 0) {
            String[] strArr = {"propertyValue"};
            ContentValues contentValues = new ContentValues();
            contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
            contentValues.put("propertyName", "EnforceMoveFilesToContainer");
            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId);
            contentValues.put("adminUid", Integer.valueOf(mUMContainerOwnerUid));
            if (isEngMode) {
                Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed: CONTAINER_ID - ADMIN_UID - CONTAINER_PROPERTY_NAME:" + contextInfo.mContainerId + " " + mUMContainerOwnerUid + " EnforceMoveFilesToContainer");
            }
            List valuesList = this.mEdmStorageProvider.getValuesList("CONTAINER_POLICY", strArr, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed: " + ((ContentValues) valuesList.get(0)).getAsBoolean("propertyValue"));
                String asString = ((ContentValues) valuesList.get(0)).getAsString("propertyValue");
                return asString != null && asString.equalsIgnoreCase("1");
            }
            Log.e("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed failed to get value from DB > returning false");
            return false;
        }
        try {
            return getEDM().getProfilePolicy().getRestriction(contextInfo, "restriction_property_move_files_to_profile");
        } catch (NullPointerException unused) {
            Log.e("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.e("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed : SecurityException occurred");
            return false;
        }
    }

    public boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "allowMoveFilesToContainer ........ ");
        boolean restriction = getEDM().getProfilePolicy().setRestriction(contextInfo, "restriction_property_move_files_to_profile", z);
        if (restriction) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMRCPPolicyService", String.format(z ? "Admin %d has allowed moving files to container." : "Admin %d has disallowed moving files to container.", Integer.valueOf(contextInfo.mCallerUid)), UserHandle.getUserId(contextInfo.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return restriction;
    }

    public boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) {
        Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed ........ ");
        if (contextInfo == null) {
            Log.d("KnoxMUMRCPPolicyService", "received ContextInfo equals null");
            return false;
        }
        if (isMigrationStateSet(contextInfo.mContainerId) == 0) {
            String[] strArr = {"propertyValue"};
            ContentValues contentValues = new ContentValues();
            contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
            contentValues.put("propertyName", "EnforceMoveFilesToOwner");
            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId);
            contentValues.put("adminUid", Integer.valueOf(mUMContainerOwnerUid));
            boolean z = isEngMode;
            if (z) {
                Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed: CONTAINER_ID - ADMIN_UID - CONTAINER_PROPERTY_NAME:" + contextInfo.mContainerId + " " + mUMContainerOwnerUid + " EnforceMoveFilesToOwner");
            }
            List valuesList = this.mEdmStorageProvider.getValuesList("CONTAINER_POLICY", strArr, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                if (z) {
                    Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed: " + ((ContentValues) valuesList.get(0)).getAsString("propertyValue"));
                }
                String asString = ((ContentValues) valuesList.get(0)).getAsString("propertyValue");
                return asString != null && asString.equalsIgnoreCase("1");
            }
            Log.e("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed failed to get value from DB > returning false");
            return false;
        }
        try {
            return getEDM().getProfilePolicy().getRestriction(contextInfo, "restriction_property_move_files_to_owner");
        } catch (NullPointerException unused) {
            Log.e("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.e("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed : SecurityException occurred");
            return false;
        }
    }

    public boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "allowMoveFilesToOwner ........ ");
        boolean restriction = getEDM().getProfilePolicy().setRestriction(contextInfo, "restriction_property_move_files_to_owner", z);
        if (restriction) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMRCPPolicyService", String.format(z ? "Admin %d has allowed moving files to owner." : "Admin %d has disallowed moving files to owner.", Integer.valueOf(contextInfo.mCallerUid)), UserHandle.getUserId(contextInfo.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return restriction;
    }

    public boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) {
        if (contextInfo != null) {
            String[] strArr = {"propertyValue"};
            ContentValues contentValues = new ContentValues();
            contentValues.put("propertyName", "EnforceMoveAppsToContainer");
            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId);
            contentValues.put("adminUid", Integer.valueOf(mUMContainerOwnerUid));
            contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
            boolean z = isEngMode;
            if (z) {
                Log.d("KnoxMUMRCPPolicyService", "getEnforceAuthForContainer: CONTAINER_ID - ADMIN_UID - CONTAINER_PROPERTY_NAME:" + contextInfo.mContainerId + " " + mUMContainerOwnerUid + " EnforceMoveAppsToContainer");
            }
            List valuesList = this.mEdmStorageProvider.getValuesList("CONTAINER_POLICY", strArr, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                if (z) {
                    Log.d("KnoxMUMRCPPolicyService", "getEnforceAuthForContainer: " + ((ContentValues) valuesList.get(0)).getAsString("propertyValue"));
                }
                String asString = ((ContentValues) valuesList.get(0)).getAsString("propertyValue");
                return asString != null && asString.equalsIgnoreCase("1");
            }
            Log.e("KnoxMUMRCPPolicyService", "getEnforceAuthForContainer failed to get value from DB > returning true");
            return false;
        }
        Log.e("KnoxMUMRCPPolicyService", "getEnforceAuthForContainer failed > returning true");
        return false;
    }

    public boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) {
        boolean putValuesNoUpdate;
        enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo == null) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues.put("propertyName", "EnforceMoveAppsToContainer");
        if (isEngMode) {
            Log.d("KnoxMUMRCPPolicyService", "setEnforceAuthForContainer: CONTAINER_ID - ADMIN_UID - CONTAINER_PROPERTY_NAME:" + contextInfo.mContainerId + " " + contextInfo.mCallerUid + " EnforceMoveAppsToContainer");
        }
        int count = this.mEdmStorageProvider.getCount("CONTAINER_POLICY", contentValues);
        StringBuilder sb = new StringBuilder();
        sb.append("setEnforceAuthForContainer: already row present ? ");
        sb.append(count > 0);
        Log.d("KnoxMUMRCPPolicyService", sb.toString());
        if (count > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("propertyValue", Boolean.valueOf(z));
            putValuesNoUpdate = this.mEdmStorageProvider.putValues("CONTAINER_POLICY", contentValues2, contentValues);
        } else {
            contentValues.put("propertyValue", Boolean.valueOf(z));
            putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("CONTAINER_POLICY", contentValues);
        }
        if (!putValuesNoUpdate) {
            Log.e("KnoxMUMRCPPolicyService", "setEnforceAuthForContainer failed : result = " + putValuesNoUpdate);
            return false;
        }
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.user_handle", contextInfo.mContainerId);
        intent.setAction("samsung.knox.intent.action.RCP_POLICY_CHANGED");
        Bundle bundle = new Bundle();
        bundle.putInt("personaId", contextInfo.mContainerId);
        bundle.putString("policyName", "EnforceMoveAppsToContainer");
        bundle.putBoolean("policyValue", z);
        intent.putExtra("MoveAppsToContainerBundle", bundle);
        Log.d("KnoxMUMRCPPolicyService", "move apps to container allow ? : " + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMRCPPolicyService", String.format(z ? "Admin %d has allowed moving applications to container." : "Admin %d has disallowed moving applications to container.", Integer.valueOf(contextInfo.mCallerUid)), UserHandle.getCallingUserId());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        boolean z = false;
        if (contextInfo != null && !userManager.hasUserRestriction("no_cross_profile_copy_paste", new UserHandle(contextInfo.mContainerId))) {
            z = true;
        }
        Log.i("KnoxMUMRCPPolicyService", "isShareClipboardDataToOwnerAllowed:" + z);
        return z;
    }

    public boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) {
        Bundle sendPolicyUpdate;
        enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER_RCP")));
        if (contextInfo == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", contextInfo.mContainerId);
        bundle.putString("knox.container.proxy.EXTRA_KEY", "no_cross_profile_copy_paste");
        if (z) {
            sendPolicyUpdate = ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_CLEAR_USER_RESTRICTION", bundle);
        } else {
            sendPolicyUpdate = ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADD_USER_RESTRICTION", bundle);
        }
        if (sendPolicyUpdate != null && sendPolicyUpdate.getInt("android.intent.extra.RETURN_RESULT", -1) == 0) {
            Log.d("KnoxMUMRCPPolicyService", "share cp to owner allow ? : " + z);
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMRCPPolicyService", String.format(z ? "Admin %d has allowed sharing clipboard to owner from Workspace." : "Admin %d has disallowed sharing clipboard to owner from Workspace.", Integer.valueOf(contextInfo.mCallerUid)), contextInfo.mContainerId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    public void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) {
        Intent intent = new Intent("samsung.knox.intent.action.NOTIFICATION_POLICY_CHANGED");
        intent.putExtra("personaId", i);
        intent.putExtra("policy", str);
        this.mContext.sendBroadcast(intent, "com.samsung.permission.READ_KNOX_NOTIFICATION");
    }

    public void sendRCPPolicyChangedBroadcast(int i) {
        Log.d("KnoxMUMRCPPolicyService", "sendRCPPolicyChangedBroadcast , personaID : ");
    }

    public void sendRCPPolicyChangeBroadcast(String str, String str2, int i) {
        Log.d("KnoxMUMRCPPolicyService", "sendRCPPolicyChangedBroadcast , mAppName : " + str + " , mPolicyType : " + str2 + " , mPersonaID : " + i);
    }
}
