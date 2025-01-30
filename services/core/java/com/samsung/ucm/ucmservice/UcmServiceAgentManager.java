package com.samsung.ucm.ucmservice;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.ucm.ucmservice.security.UcmSecurityHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class UcmServiceAgentManager implements IUcmAgentManagerDeleteDelegate {
    public static final Intent CREDENTIAL_AGENT_INTENT_FILTER = new Intent("com.samsung.android.knox.intent.action.UCM_AGENT");
    public static boolean DBG = "eng".equals(SystemProperties.get("ro.build.type"));
    public Context mContext;
    public UcmSecurityHelper mSecurityHelper;
    public List mActiveAgentList = new ArrayList();
    public final Object mServicesLock = new Object();
    public boolean mNeedToBindESE = false;

    public UcmServiceAgentManager(Context context, UcmSecurityHelper ucmSecurityHelper) {
        this.mContext = context;
        this.mSecurityHelper = ucmSecurityHelper;
    }

    public List getActiveAgentList() {
        if (this.mActiveAgentList.isEmpty()) {
            Log.i("UcmService.UcmAgentManager", "AgentList is empty, refreshAgent");
            refreshAgentList(null);
        }
        return this.mActiveAgentList;
    }

    public static List getAllPlugins(PackageManager packageManager) {
        Log.i("UcmService.UcmAgentManager", "getAllPlugins");
        List<ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(CREDENTIAL_AGENT_INTENT_FILTER, 128, 0);
        ArrayList arrayList = new ArrayList(queryIntentServicesAsUser.size());
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo != null && serviceInfo.applicationInfo != null) {
                Log.i("UcmService.UcmAgentManager", "found " + serviceInfo.packageName);
                arrayList.add(resolveInfo);
            }
        }
        return arrayList;
    }

    public synchronized void refreshAgentList(Bundle bundle) {
        try {
            Log.i("UcmService.UcmAgentManager", "*****refreshAgentList is called***");
        } catch (Exception e) {
            Log.i("UcmService.UcmAgentManager", "The exception occurs " + e.getMessage());
        }
        if (!isSystemReady()) {
            Log.i("UcmService.UcmAgentManager", "refreshAgentList system ready is not called yet. Ignoring agent refresh logic");
            return;
        }
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(this.mActiveAgentList);
        refreshAgentListInternal(arraySet, bundle);
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (ucmAgentWrapper.info != null) {
                Log.i("UcmService.UcmAgentManager", "  Removing unwanted agent- " + ucmAgentWrapper.info.f1778id);
            }
            ucmAgentWrapper.unbind();
            this.mActiveAgentList.remove(ucmAgentWrapper);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x01a9 A[Catch: Exception -> 0x021b, TryCatch #1 {Exception -> 0x021b, blocks: (B:15:0x0088, B:18:0x00a0, B:95:0x00a6, B:21:0x00be, B:31:0x0136, B:33:0x013e, B:35:0x0142, B:38:0x0148, B:80:0x014b, B:39:0x0152, B:53:0x015c, B:55:0x0160, B:62:0x0166, B:64:0x017a, B:67:0x01a9, B:70:0x01c7, B:72:0x0183, B:74:0x0189, B:76:0x01a1, B:59:0x01e6, B:42:0x01ee, B:44:0x01f2), top: B:14:0x0088 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01c7 A[Catch: Exception -> 0x021b, TryCatch #1 {Exception -> 0x021b, blocks: (B:15:0x0088, B:18:0x00a0, B:95:0x00a6, B:21:0x00be, B:31:0x0136, B:33:0x013e, B:35:0x0142, B:38:0x0148, B:80:0x014b, B:39:0x0152, B:53:0x015c, B:55:0x0160, B:62:0x0166, B:64:0x017a, B:67:0x01a9, B:70:0x01c7, B:72:0x0183, B:74:0x0189, B:76:0x01a1, B:59:0x01e6, B:42:0x01ee, B:44:0x01f2), top: B:14:0x0088 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void refreshAgentListInternal(ArraySet arraySet, Bundle bundle) {
        String str;
        ServiceInfo serviceInfo;
        String string;
        Log.i("UcmService.UcmAgentManager", "*****refreshAgentListInternal is called***");
        String str2 = null;
        if (bundle != null) {
            int i = bundle.getInt("event");
            if (i == 1) {
                str2 = bundle.getString("packageName");
                string = null;
            } else {
                string = i == 2 ? bundle.getString("packageName") : null;
            }
            Log.i("UcmService.UcmAgentManager", "event " + i + ", activatedPackage-" + str2 + ",expiredPackage-" + string);
            str = string;
        } else {
            str = null;
        }
        for (ResolveInfo resolveInfo : getAllPlugins(this.mContext.getPackageManager())) {
            ComponentName componentName = UcmAgentWrapper.getComponentName(resolveInfo);
            if (componentName == null) {
                Log.i("UcmService.UcmAgentManager", "name is empty");
            } else {
                UcmAgentWrapper ucmAgentWrapper = new UcmAgentWrapper(this.mContext, this, componentName);
                String packageName = componentName.getPackageName();
                try {
                    Log.i("UcmService.UcmAgentManager", "-------Processing started for agentPackageName------" + packageName);
                    if (str != null && packageName.equals(str)) {
                        Log.i("UcmService.UcmAgentManager", "agentPackageName -" + packageName + " is not an active plugin. Its license is expired. Ignoring it...");
                    } else {
                        Log.i("UcmService.UcmAgentManager", "  agentPackageName -" + packageName + " is an active plugin");
                        Log.i("UcmService.UcmAgentManager", "  Check if caller has UCS Plugin permission...");
                        try {
                            Log.i("UcmService.UcmAgentManager", "  Agent has UCS PLUGIN permission. Processing further...");
                            boolean z = false;
                            if (isSystemStorage(packageName)) {
                                Log.i("UcmService.UcmAgentManager", "  agentPackageName " + packageName + " is system storage. Checking system signature");
                                if (this.mSecurityHelper.isSystemApp(packageName, 0)) {
                                    Log.i("UcmService.UcmAgentManager", "  Valid system storage found is " + packageName);
                                } else {
                                    Log.i("UcmService.UcmAgentManager", "  system storage found - " + packageName + " is not valid. Ignoring it...");
                                }
                            }
                            if ("com.samsung.ucs.agent.ese".equals(packageName) && !this.mNeedToBindESE) {
                                if (isEseManaged()) {
                                    this.mNeedToBindESE = true;
                                } else {
                                    Log.i("UcmService.UcmAgentManager", "Do not need to bind eSE Service");
                                }
                            }
                            if (!this.mActiveAgentList.contains(ucmAgentWrapper)) {
                                if (resolveInfo != null && (serviceInfo = resolveInfo.serviceInfo) != null && serviceInfo.metaData != null) {
                                    ucmAgentWrapper.initialize(resolveInfo, new UserHandle(0));
                                    if (this.mSecurityHelper.isSystemApp(ucmAgentWrapper.info.packageName, 0)) {
                                        Log.i("UcmService.UcmAgentManager", "  Adding system signed agent");
                                    } else if (str2 != null) {
                                        if (packageName.equals(str2)) {
                                            Log.i("UcmService.UcmAgentManager", "agentPackageName -" + packageName + " is an active plugin");
                                        }
                                        if (z) {
                                            Log.i("UcmService.UcmAgentManager", "  Adding new agent -" + ucmAgentWrapper.info.f1778id);
                                            this.mActiveAgentList.add(ucmAgentWrapper);
                                        } else {
                                            Log.i("UcmService.UcmAgentManager", " Unbind agent -" + ucmAgentWrapper.info.f1778id);
                                            ucmAgentWrapper.unbind();
                                        }
                                    } else {
                                        if (str2 == null) {
                                            Log.i("UcmService.UcmAgentManager", "Agent entry is not in PersistentServices, but has proper permissions");
                                        }
                                        if (z) {
                                        }
                                    }
                                    z = true;
                                    if (z) {
                                    }
                                }
                                Log.i("UcmService.UcmAgentManager", "resolveInfo null");
                            } else {
                                if (DBG) {
                                    Log.i("UcmService.UcmAgentManager", "agent is already added in activeAgentList");
                                }
                                try {
                                    arraySet.remove(ucmAgentWrapper);
                                } catch (Exception e) {
                                    e = e;
                                    Log.i("UcmService.UcmAgentManager", "The exception occurs " + e.getMessage());
                                }
                            }
                        } catch (Exception e2) {
                            Log.i("UcmService.UcmAgentManager", "The exception occurs " + e2.getMessage());
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }
    }

    public final boolean isEseManaged() {
        if (DBG) {
            Log.i("UcmService.UcmAgentManager", "isEseManaged");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("storagePackageName", "com.samsung.ucs.agent.ese");
                if (new EdmStorageProvider(this.mContext).getCount("UniversalCredentialInfoTable", contentValues) > 0) {
                    z = true;
                }
            } catch (Exception e) {
                Log.i("UcmService.UcmAgentManager", "The exception occurs " + e.getMessage());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (DBG) {
                Log.i("UcmService.UcmAgentManager", "isEseManaged - status : " + z);
            }
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isSystemStorage(String str) {
        return str.equals("com.samsung.ucs.agent.ese") || str.equals("com.samsung.ucs.agent.boot");
    }

    public final boolean isSystemReady() {
        return ActivityManager.isSystemReady();
    }

    public void systemReady(EdmStorageProvider edmStorageProvider) {
        checkESEPermissionInstalledMDM(edmStorageProvider);
    }

    public final void checkESEPermissionInstalledMDM(EdmStorageProvider edmStorageProvider) {
        String packageName;
        if (edmStorageProvider != null) {
            ArrayList adminUidList = edmStorageProvider.getAdminUidList();
            if (adminUidList.isEmpty()) {
                return;
            }
            Iterator it = adminUidList.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (num != null) {
                    int intValue = num.intValue();
                    Log.i("UcmService.UcmAgentManager", "check eSE Permission : " + intValue);
                    ComponentName componentNameForUid = edmStorageProvider.getComponentNameForUid(intValue);
                    if (componentNameForUid != null && (packageName = componentNameForUid.getPackageName()) != null && checkESEPermission(packageName, UserHandle.getUserId(intValue))) {
                        Log.i("UcmService.UcmAgentManager", "ESE Permission exist");
                        this.mNeedToBindESE = true;
                        return;
                    }
                }
            }
        }
    }

    public void checkESE(String str, int i) {
        if (this.mNeedToBindESE || !checkESEPermission(str, i)) {
            return;
        }
        this.mNeedToBindESE = true;
    }

    public final boolean checkESEPermission(String str, int i) {
        Log.i("UcmService.UcmAgentManager", "checkESEPermission");
        if (str == null || str.isEmpty()) {
            Log.i("UcmService.UcmAgentManager", "packagename is empty");
            return false;
        }
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT", str, i) == 0) {
            Log.i("UcmService.UcmAgentManager", "KNOX_UCM_ESE_PERMISSION is granted");
            return true;
        }
        if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_UCM_MGMT", str, i) == 0) {
            Log.i("UcmService.UcmAgentManager", "KNOX_UCM_UNIFIED_PERMISSION is granted");
            return true;
        }
        return false;
    }

    @Override // com.samsung.ucm.ucmservice.IUcmAgentManagerDeleteDelegate
    public void deleteAndRefreshAgents(UcmAgentWrapper ucmAgentWrapper) {
        Log.i("UcmService.UcmAgentManager", "deletAndRefreshAgents()");
        if (ucmAgentWrapper != null) {
            Log.i("UcmService.UcmAgentManager", "deletAndRefreshAgents() remove " + ucmAgentWrapper);
            getActiveAgentList().remove(ucmAgentWrapper);
        }
        refreshAgentList(null);
    }
}
