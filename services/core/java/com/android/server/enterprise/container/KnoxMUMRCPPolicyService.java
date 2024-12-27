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
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxMUMRCPPolicyService extends IRCPPolicy.Stub
        implements EnterpriseServiceCallback {
    public static final boolean isEngMode = "eng".equals(SystemProperties.get("ro.build.type"));
    public final Context mContext;
    public EnterpriseDeviceManager mEDM = null;
    public final EdmStorageProvider mEdmStorageProvider;

    public KnoxMUMRCPPolicyService(Context context) {
        this.mEdmStorageProvider = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public final boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) {
        boolean putValuesNoUpdate;
        getEDM$6()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo == null) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                contextInfo.mCallerUid,
                contentValues,
                "adminUid",
                "propertyName",
                "EnforceMoveAppsToContainer");
        if (isEngMode) {
            StringBuilder sb =
                    new StringBuilder(
                            "setEnforceAuthForContainer: CONTAINER_ID - ADMIN_UID -"
                                + " CONTAINER_PROPERTY_NAME:");
            sb.append(contextInfo.mContainerId);
            sb.append(" ");
            AudioService$$ExternalSyntheticOutline0.m(
                    sb,
                    contextInfo.mCallerUid,
                    " EnforceMoveAppsToContainer",
                    "KnoxMUMRCPPolicyService");
        }
        int count = this.mEdmStorageProvider.getCount("CONTAINER_POLICY", contentValues);
        RCPManagerService$$ExternalSyntheticOutline0.m(
                "KnoxMUMRCPPolicyService",
                new StringBuilder("setEnforceAuthForContainer: already row present ? "),
                count > 0);
        if (count > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("propertyValue", Boolean.valueOf(z));
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValues(
                            "CONTAINER_POLICY", contentValues2, contentValues);
        } else {
            contentValues.put("propertyValue", Boolean.valueOf(z));
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValuesNoUpdate("CONTAINER_POLICY", contentValues);
        }
        if (!putValuesNoUpdate) {
            Log.e(
                    "KnoxMUMRCPPolicyService",
                    "setEnforceAuthForContainer failed : result = " + putValuesNoUpdate);
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
        AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "KnoxMUMRCPPolicyService",
                String.format(
                        z
                                ? "Admin %d has allowed moving applications to container."
                                : "Admin %d has disallowed moving applications to container.",
                        Integer.valueOf(contextInfo.mCallerUid)),
                UserHandle.getCallingUserId());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public final boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "allowMoveFilesToContainer ........ ");
        boolean restriction =
                getEDM$6()
                        .getProfilePolicy()
                        .setRestriction(
                                contextInfo, "restriction_property_move_files_to_profile", z);
        if (restriction) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "KnoxMUMRCPPolicyService",
                        String.format(
                                z
                                        ? "Admin %d has allowed moving files to container."
                                        : "Admin %d has disallowed moving files to container.",
                                Integer.valueOf(contextInfo.mCallerUid)),
                        UserHandle.getUserId(contextInfo.mCallerUid));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return restriction;
    }

    public final boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "allowMoveFilesToOwner ........ ");
        boolean restriction =
                getEDM$6()
                        .getProfilePolicy()
                        .setRestriction(contextInfo, "restriction_property_move_files_to_owner", z);
        if (restriction) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "KnoxMUMRCPPolicyService",
                        String.format(
                                z
                                        ? "Admin %d has allowed moving files to owner."
                                        : "Admin %d has disallowed moving files to owner.",
                                Integer.valueOf(contextInfo.mCallerUid)),
                        UserHandle.getUserId(contextInfo.mCallerUid));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return restriction;
    }

    public final boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) {
        return true;
    }

    public final boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) {
        getEDM$6()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER_RCP")));
        if (contextInfo == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", contextInfo.mContainerId);
        bundle.putString("knox.container.proxy.EXTRA_KEY", "no_cross_profile_copy_paste");
        Bundle sendPolicyUpdate =
                z
                        ? ContainerProxy.sendPolicyUpdate(
                                "knox.container.proxy.POLICY_CLEAR_USER_RESTRICTION", bundle)
                        : ContainerProxy.sendPolicyUpdate(
                                "knox.container.proxy.POLICY_ADD_USER_RESTRICTION", bundle);
        if (sendPolicyUpdate == null
                || sendPolicyUpdate.getInt("android.intent.extra.RETURN_RESULT", -1) != 0) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        Log.d("KnoxMUMRCPPolicyService", "share cp to owner allow ? : " + z);
        AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "KnoxMUMRCPPolicyService",
                String.format(
                        z
                                ? "Admin %d has allowed sharing clipboard to owner from Workspace."
                                : "Admin %d has disallowed sharing clipboard to owner from"
                                      + " Workspace.",
                        Integer.valueOf(contextInfo.mCallerUid)),
                contextInfo.mContainerId);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public final boolean getAllowChangeDataSyncPolicy(
            ContextInfo contextInfo, String str, String str2) {
        Log.d("KnoxMUMRCPPolicyService", "getAllowChangeDataSyncPolicy");
        int userId =
                contextInfo != null
                        ? contextInfo.mContainerId
                        : UserHandle.getUserId(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String propertyValue = getPropertyValue(userId, "RCP_DATA", str, str2);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (propertyValue == null) {
            return false;
        }
        return Boolean.parseBoolean(propertyValue);
    }

    public final EnterpriseDeviceManager getEDM$6() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final List getListFromAllowChangeDataSyncPolicy(
            ContextInfo contextInfo, String str, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "getListFromAllowChangeDataSyncPolicy");
        return getListFromSyncPolicy(contextInfo, "RCP_DATA", str, Boolean.toString(z));
    }

    public final List getListFromSyncPolicy(
            ContextInfo contextInfo, String str, String str2, String str3) {
        ContextInfo enforceActiveAdminPermissionByContext =
                getEDM$6()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_CONTAINER_RCP")));
        if (isEngMode) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("admin uid from context info: "),
                    enforceActiveAdminPermissionByContext.mCallerUid,
                    "KnoxMUMRCPPolicyService");
        }
        if (str2 != null && !str2.isEmpty() && str3 != null && !str3.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                    enforceActiveAdminPermissionByContext.mCallerUid,
                    contentValues,
                    "adminUid",
                    "propertyName",
                    str2);
            contentValues.put("propertyValue", str3);
            ArrayList arrayList =
                    (ArrayList)
                            this.mEdmStorageProvider.getValuesList(
                                    str, new String[] {"name"}, contentValues);
            if (arrayList.size() > 0) {
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(((ContentValues) it.next()).getAsString("name"));
                }
                return arrayList2;
            }
        }
        return null;
    }

    public final String getNotificationSyncPolicy(
            ContextInfo contextInfo, String str, String str2) {
        Log.d("KnoxMUMRCPPolicyService", "getNotificationSyncPolicy");
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String propertyValue = getPropertyValue(userId, "RCP_NOTIFICATION", str, str2);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return propertyValue;
    }

    public final List getPackagesFromNotificationSyncPolicy(
            ContextInfo contextInfo, String str, String str2) {
        return getListFromSyncPolicy(contextInfo, "RCP_NOTIFICATION", str, str2);
    }

    public final String getPropertyValue(int i, String str, String str2, String str3) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException(
                    "Not a system service. This API only allowed by SYSTEM_UID");
        }
        if (str2 == null || str2.isEmpty() || str3 == null || str3.isEmpty() || i == 0) {
            return null;
        }
        ArrayList adminLUidListAsUser = this.mEdmStorageProvider.getAdminLUidListAsUser(i);
        if (adminLUidListAsUser.isEmpty()) {
            return null;
        }
        String[] strArr = {"propertyValue"};
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str2);
        contentValues.put("propertyName", str3);
        Iterator it = adminLUidListAsUser.iterator();
        while (it.hasNext()) {
            contentValues.put("adminUid", (Long) it.next());
            ArrayList arrayList =
                    (ArrayList) this.mEdmStorageProvider.getValuesList(str, strArr, contentValues);
            if (arrayList.size() > 0) {
                return ((ContentValues) arrayList.get(0)).getAsString("propertyValue");
            }
        }
        return ((SemPersonaManager) this.mContext.getSystemService("persona")).exists(i)
                ? "false"
                : "true";
    }

    public final boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) {
        if (contextInfo == null) {
            Log.e("KnoxMUMRCPPolicyService", "getEnforceAuthForContainer failed > returning true");
            return false;
        }
        String[] strArr = {"propertyValue"};
        ContentValues m =
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        "propertyName", "EnforceMoveAppsToContainer");
        int mUMContainerOwnerUid =
                this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId);
        m.put("adminUid", Integer.valueOf(mUMContainerOwnerUid));
        m.put("cid", Integer.valueOf(contextInfo.mContainerId));
        boolean z = isEngMode;
        if (z) {
            Log.d(
                    "KnoxMUMRCPPolicyService",
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            contextInfo.mContainerId,
                            mUMContainerOwnerUid,
                            " ",
                            " EnforceMoveAppsToContainer",
                            new StringBuilder(
                                    "getEnforceAuthForContainer: CONTAINER_ID - ADMIN_UID -"
                                        + " CONTAINER_PROPERTY_NAME:")));
        }
        ArrayList arrayList =
                (ArrayList) this.mEdmStorageProvider.getValuesList("CONTAINER_POLICY", strArr, m);
        if (arrayList.size() <= 0) {
            Log.e(
                    "KnoxMUMRCPPolicyService",
                    "getEnforceAuthForContainer failed to get value from DB > returning true");
            return false;
        }
        if (z) {
            Log.d(
                    "KnoxMUMRCPPolicyService",
                    "getEnforceAuthForContainer: "
                            + ((ContentValues) arrayList.get(0)).getAsString("propertyValue"));
        }
        String asString = ((ContentValues) arrayList.get(0)).getAsString("propertyValue");
        return asString != null && asString.equalsIgnoreCase("1");
    }

    public final boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) {
        Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToContainerAllowed ........ ");
        if (contextInfo == null) {
            Log.d("KnoxMUMRCPPolicyService", "received ContextInfo equals null");
            return false;
        }
        try {
            return getEDM$6()
                    .getProfilePolicy()
                    .getRestriction(contextInfo, "restriction_property_move_files_to_profile");
        } catch (NullPointerException unused) {
            Log.e(
                    "KnoxMUMRCPPolicyService",
                    "isMoveFilesToContainerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.e(
                    "KnoxMUMRCPPolicyService",
                    "isMoveFilesToContainerAllowed : SecurityException occurred");
            return false;
        }
    }

    public final boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) {
        Log.d("KnoxMUMRCPPolicyService", "isMoveFilesToOwnerAllowed ........ ");
        if (contextInfo == null) {
            Log.d("KnoxMUMRCPPolicyService", "received ContextInfo equals null");
            return false;
        }
        try {
            return getEDM$6()
                    .getProfilePolicy()
                    .getRestriction(contextInfo, "restriction_property_move_files_to_owner");
        } catch (NullPointerException unused) {
            Log.e(
                    "KnoxMUMRCPPolicyService",
                    "isMoveFilesToOwnerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.e(
                    "KnoxMUMRCPPolicyService",
                    "isMoveFilesToOwnerAllowed : SecurityException occurred");
            return false;
        }
    }

    public final boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) {
        return true;
    }

    public final boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        boolean z = false;
        if (contextInfo != null
                && !userManager.hasUserRestriction(
                        "no_cross_profile_copy_paste", new UserHandle(contextInfo.mContainerId))) {
            z = true;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isShareClipboardDataToOwnerAllowed:", "KnoxMUMRCPPolicyService", z);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) {
        Intent intent = new Intent("samsung.knox.intent.action.NOTIFICATION_POLICY_CHANGED");
        intent.putExtra("personaId", i);
        intent.putExtra("policy", str);
        this.mContext.sendBroadcast(intent, "com.samsung.permission.READ_KNOX_NOTIFICATION");
    }

    public final boolean setAllowChangeDataSyncPolicy(
            ContextInfo contextInfo, List list, String str, boolean z) {
        Log.d("KnoxMUMRCPPolicyService", "setAllowChangeDataSyncPolicy");
        return false;
    }

    public final boolean setNotificationSyncPolicy(
            ContextInfo contextInfo, List list, String str, String str2) {
        Log.d("KnoxMUMRCPPolicyService", "setNotificationSyncPolicy");
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
