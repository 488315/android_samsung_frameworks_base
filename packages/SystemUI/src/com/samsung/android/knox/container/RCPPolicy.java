package com.samsung.android.knox.container;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.container.IRCPPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RCPPolicy {
    public static final String CALENDAR = "Calendar";
    public static final String CONTACTS = "Contacts";
    public static final String EXPORT_DATA = "knox-export-data";
    public static final String IMPORT_DATA = "knox-import-data";
    public static final String NOTIFICATIONS = "Notifications";
    public static final String SANITIZE_DATA = "knox-sanitize-data";
    public static final String TAG = "RCPPolicy";
    public static IRCPPolicy gRCPService;
    public ContextInfo mContextInfo;

    public RCPPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static synchronized IRCPPolicy getRCPPolicyService() {
        IRCPPolicy iRCPPolicy;
        synchronized (RCPPolicy.class) {
            try {
                if (gRCPService == null) {
                    gRCPService = IRCPPolicy.Stub.asInterface(ServiceManager.getService("mum_container_rcp_policy"));
                }
                iRCPPolicy = gRCPService;
            } catch (Throwable th) {
                throw th;
            }
        }
        return iRCPPolicy;
    }

    public boolean allowMoveAppsToContainer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowMoveAppsToContainer");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowMoveAppsToContainer(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean allowMoveFilesToContainer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowMoveFilesToContainer");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowMoveFilesToContainer(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean allowMoveFilesToOwner(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowMoveFilesToOwner");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowMoveFilesToOwner(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean allowShareClipboardDataToContainer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowShareClipboardDataToContainer " + z);
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        boolean z2 = false;
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            z2 = rCPPolicyService.allowShareClipboardDataToContainer(this.mContextInfo, z);
            Log.d(TAG, "retVal after MUM is " + z2);
            return z2;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return z2;
        }
    }

    public boolean allowShareClipboardDataToOwner(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowShareClipboardDataToOwner");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowShareClipboardDataToOwner(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean getAllowChangeDataSyncPolicy(String str, String str2) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.getAllowChangeDataSyncPolicy(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public List<String> getListFromAllowChangeDataSyncPolicy(String str, boolean z) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return null;
        }
        try {
            return rCPPolicyService.getListFromAllowChangeDataSyncPolicy(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return null;
        }
    }

    public String getNotificationSyncPolicy(String str, String str2) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return null;
        }
        try {
            return rCPPolicyService.getNotificationSyncPolicy(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return null;
        }
    }

    public List<String> getPackagesFromNotificationSyncPolicy(String str, String str2) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return null;
        }
        try {
            return rCPPolicyService.getPackagesFromNotificationSyncPolicy(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return null;
        }
    }

    public boolean isMoveAppsToContainerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isMoveAppsToContainerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean isMoveFilesToContainerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isMoveFilesToContainerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean isMoveFilesToOwnerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isMoveFilesToOwnerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean isShareClipboardDataToContainerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        boolean z = false;
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            z = rCPPolicyService.isShareClipboardDataToContainerAllowed(this.mContextInfo);
            Log.d(TAG, "retVal after MUM is " + z);
            return z;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return z;
        }
    }

    public boolean isShareClipboardDataToOwnerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isShareClipboardDataToOwnerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean setAllowChangeDataSyncPolicy(List<String> list, String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.setAllowChangeDataSyncPolicy");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.setAllowChangeDataSyncPolicy(this.mContextInfo, list, str, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public boolean setNotificationSyncPolicy(List<String> list, String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.setNotificationSyncPolicy");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.setNotificationSyncPolicy(this.mContextInfo, list, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }
}
