package com.samsung.android.knox.container;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.container.IRCPPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RCPPolicy {
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
            if (gRCPService == null) {
                gRCPService = IRCPPolicy.Stub.asInterface(ServiceManager.getService("mum_container_rcp_policy"));
            }
            iRCPPolicy = gRCPService;
        }
        return iRCPPolicy;
    }

    public final boolean allowMoveAppsToContainer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowMoveAppsToContainer");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowMoveAppsToContainer(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean allowMoveFilesToContainer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowMoveFilesToContainer");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowMoveFilesToContainer(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean allowMoveFilesToOwner(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowMoveFilesToOwner");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowMoveFilesToOwner(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean allowShareClipboardDataToContainer(boolean z) {
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
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return z2;
        }
    }

    public final boolean allowShareClipboardDataToOwner(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.allowShareClipboardDataToOwner");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.allowShareClipboardDataToOwner(this.mContextInfo, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean getAllowChangeDataSyncPolicy(String str, String str2) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.getAllowChangeDataSyncPolicy(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final List<String> getListFromAllowChangeDataSyncPolicy(String str, boolean z) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return null;
        }
        try {
            return rCPPolicyService.getListFromAllowChangeDataSyncPolicy(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return null;
        }
    }

    public final String getNotificationSyncPolicy(String str, String str2) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return null;
        }
        try {
            return rCPPolicyService.getNotificationSyncPolicy(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return null;
        }
    }

    public final List<String> getPackagesFromNotificationSyncPolicy(String str, String str2) {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return null;
        }
        try {
            return rCPPolicyService.getPackagesFromNotificationSyncPolicy(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return null;
        }
    }

    public final boolean isMoveAppsToContainerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isMoveAppsToContainerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean isMoveFilesToContainerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isMoveFilesToContainerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean isMoveFilesToOwnerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isMoveFilesToOwnerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean isShareClipboardDataToContainerAllowed() {
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
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return z;
        }
    }

    public final boolean isShareClipboardDataToOwnerAllowed() {
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.isShareClipboardDataToOwnerAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean setAllowChangeDataSyncPolicy(List<String> list, String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.setAllowChangeDataSyncPolicy");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.setAllowChangeDataSyncPolicy(this.mContextInfo, list, str, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }

    public final boolean setNotificationSyncPolicy(List<String> list, String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RCPPolicy.setNotificationSyncPolicy");
        IRCPPolicy rCPPolicyService = getRCPPolicyService();
        if (rCPPolicyService == null) {
            Log.d(TAG, " RCP policy service is not yet ready!!!");
            return false;
        }
        try {
            return rCPPolicyService.setNotificationSyncPolicy(this.mContextInfo, list, str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with RCP policy service: "), TAG);
            return false;
        }
    }
}
