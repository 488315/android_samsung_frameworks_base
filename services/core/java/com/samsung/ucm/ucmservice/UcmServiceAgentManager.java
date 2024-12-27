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
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.ucm.ucmservice.security.UcmSecurityHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UcmServiceAgentManager implements IUcmAgentManagerDeleteDelegate {
    public static final Intent CREDENTIAL_AGENT_INTENT_FILTER = new Intent("com.samsung.android.knox.intent.action.UCM_AGENT");
    public static final boolean DBG = UcmServiceUtil.isDebug();
    public List mActiveAgentList;
    public Context mContext;
    public boolean mNeedToBindESE;
    public UcmSecurityHelper mSecurityHelper;

    public static boolean checkESEPermission(int i, String str) {
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

    public final List getActiveAgentList() {
        if (((ArrayList) this.mActiveAgentList).isEmpty()) {
            Log.i("UcmService.UcmAgentManager", "AgentList is empty, refreshAgent");
            refreshAgentList();
        }
        return this.mActiveAgentList;
    }

    public final boolean isEseManaged() {
        boolean z = DBG;
        if (z) {
            Log.i("UcmService.UcmAgentManager", "isEseManaged");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("storagePackageName", "com.samsung.ucs.agent.ese");
                if (new EdmStorageProvider(this.mContext).getCount("UniversalCredentialInfoTable", contentValues) > 0) {
                    z2 = true;
                }
            } catch (Exception e) {
                Log.i("UcmService.UcmAgentManager", "The exception occurs " + e.getMessage());
            }
            if (z) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("isEseManaged - status : ", "UcmService.UcmAgentManager", z2);
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void refreshAgentList() {
        try {
            Log.i("UcmService.UcmAgentManager", "*****refreshAgentList is called***");
        } catch (Exception e) {
            Log.i("UcmService.UcmAgentManager", "The exception occurs " + e.getMessage());
        }
        if (!ActivityManager.isSystemReady()) {
            Log.i("UcmService.UcmAgentManager", "refreshAgentList system ready is not called yet. Ignoring agent refresh logic");
            return;
        }
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(this.mActiveAgentList);
        refreshAgentListInternal(arraySet);
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            UcmAgentWrapper ucmAgentWrapper = (UcmAgentWrapper) it.next();
            if (ucmAgentWrapper.info != null) {
                Log.i("UcmService.UcmAgentManager", "  Removing unwanted agent- " + ucmAgentWrapper.info.id);
            }
            ucmAgentWrapper.unbind();
            ((ArrayList) this.mActiveAgentList).remove(ucmAgentWrapper);
        }
    }

    public final void refreshAgentListInternal(ArraySet arraySet) {
        ComponentName componentName;
        ServiceInfo serviceInfo;
        Log.i("UcmService.UcmAgentManager", "*****refreshAgentListInternal is called***");
        int organizationOwnedProfileUserId = UcmServiceUtil.isOrganizationOwnedProfile(this.mContext) ? UcmServiceUtil.getOrganizationOwnedProfileUserId() : 0;
        PackageManager packageManager = this.mContext.getPackageManager();
        Log.i("UcmService.UcmAgentManager", "getAllPlugins");
        List<ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(CREDENTIAL_AGENT_INTENT_FILTER, 128, organizationOwnedProfileUserId);
        ArrayList arrayList = new ArrayList(queryIntentServicesAsUser.size());
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
            if (serviceInfo2 != null && serviceInfo2.applicationInfo != null) {
                Log.i("UcmService.UcmAgentManager", "found " + serviceInfo2.packageName);
                arrayList.add(resolveInfo);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo2 = (ResolveInfo) it.next();
            boolean z = UcmAgentWrapper.DEBUG;
            if (resolveInfo2 == null || resolveInfo2.serviceInfo == null) {
                componentName = null;
            } else {
                ServiceInfo serviceInfo3 = resolveInfo2.serviceInfo;
                componentName = new ComponentName(serviceInfo3.packageName, serviceInfo3.name);
            }
            if (componentName == null) {
                Log.i("UcmService.UcmAgentManager", "name is empty");
            } else {
                UcmAgentWrapper ucmAgentWrapper = new UcmAgentWrapper(this.mContext, this, componentName);
                String packageName = componentName.getPackageName();
                try {
                    Log.i("UcmService.UcmAgentManager", "-------Processing started for agentPackageName------" + packageName);
                    Log.i("UcmService.UcmAgentManager", "  agentPackageName -" + packageName + " is an active plugin");
                    Log.i("UcmService.UcmAgentManager", "  Check if caller has UCS Plugin permission...");
                    try {
                        Log.i("UcmService.UcmAgentManager", "  Agent has UCS PLUGIN permission. Processing further...");
                        boolean equals = packageName.equals("com.samsung.ucs.agent.ese");
                        UcmSecurityHelper ucmSecurityHelper = this.mSecurityHelper;
                        if (equals || packageName.equals("com.samsung.ucs.agent.boot")) {
                            Log.i("UcmService.UcmAgentManager", "  agentPackageName " + packageName + " is system storage. Checking system signature");
                            if (ucmSecurityHelper.isSystemApp(packageName)) {
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
                        if (((ArrayList) this.mActiveAgentList).contains(ucmAgentWrapper)) {
                            if (DBG) {
                                Log.i("UcmService.UcmAgentManager", "agent is already added in activeAgentList");
                            }
                            arraySet.remove(ucmAgentWrapper);
                        } else {
                            if (resolveInfo2 != null && (serviceInfo = resolveInfo2.serviceInfo) != null && serviceInfo.metaData != null) {
                                ucmAgentWrapper.initialize(resolveInfo2, new UserHandle(organizationOwnedProfileUserId));
                                if (ucmSecurityHelper.isSystemApp(ucmAgentWrapper.info.packageName)) {
                                    Log.i("UcmService.UcmAgentManager", "  Adding system signed agent");
                                } else {
                                    Log.i("UcmService.UcmAgentManager", "Agent entry is not in PersistentServices, but has proper permissions");
                                }
                                Log.i("UcmService.UcmAgentManager", "  Adding new agent -" + ucmAgentWrapper.info.id);
                                ((ArrayList) this.mActiveAgentList).add(ucmAgentWrapper);
                            }
                            Log.i("UcmService.UcmAgentManager", "resolveInfo null");
                        }
                    } catch (Exception e) {
                        Log.i("UcmService.UcmAgentManager", "The exception occurs " + e.getMessage());
                    }
                } catch (Exception e2) {
                    RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(e2, new StringBuilder("The exception occurs "), "UcmService.UcmAgentManager");
                }
            }
        }
    }
}
