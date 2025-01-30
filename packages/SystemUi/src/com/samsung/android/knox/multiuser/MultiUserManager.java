package com.samsung.android.knox.multiuser;

import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.multiuser.IMultiUserManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MultiUserManager {
    public static final String TAG = "MultiUserManager";
    public static MultiUserManager gMultiUserMgrInst = null;
    public static boolean isMuSupportInfoReady = false;
    public static boolean isMuSupported = false;
    public static IMultiUserManager mService;
    public static final Object mSync = new Object();
    public final Context mContext;
    public ContextInfo mContextInfo;

    public MultiUserManager(ContextInfo contextInfo, Context context) {
        mService = IMultiUserManager.Stub.asInterface(ServiceManager.getService("multi_user_manager_service"));
        this.mContext = context;
        this.mContextInfo = contextInfo;
        getMuSupportInfo();
    }

    public static MultiUserManager createInstance(ContextInfo contextInfo, Context context) {
        return new MultiUserManager(contextInfo, context);
    }

    public static MultiUserManager getInstance(Context context) {
        MultiUserManager multiUserManager;
        synchronized (mSync) {
            if (gMultiUserMgrInst == null) {
                gMultiUserMgrInst = new MultiUserManager(new ContextInfo(Process.myUid()), context);
            }
            multiUserManager = gMultiUserMgrInst;
        }
        return multiUserManager;
    }

    public static IMultiUserManager getService() {
        if (mService == null) {
            mService = IMultiUserManager.Stub.asInterface(ServiceManager.getService("multi_user_manager_service"));
        }
        return mService;
    }

    public final boolean allowMultipleUsers(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "MultiUserManager.allowMultipleUsers");
        if (getService() != null) {
            try {
                int allowMultipleUsers = mService.allowMultipleUsers(this.mContextInfo, z);
                if (-1 != allowMultipleUsers) {
                    return allowMultipleUsers == 1;
                }
                throw new UnsupportedOperationException("Not Supported in this device");
            } catch (RemoteException e) {
                MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed talking with multi user service"), TAG);
            }
        }
        return false;
    }

    public final boolean allowUserCreation(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "allowUserCreation");
        EnterpriseLicenseManager.log(this.mContextInfo, "MultiUserManager.allowUserCreation");
        if (getService() != null) {
            try {
                enforceMultiUserSupport();
                return mService.allowUserCreation(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return false;
    }

    public final boolean allowUserRemoval(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "allowUserRemoval");
        EnterpriseLicenseManager.log(this.mContextInfo, "MultiUserManager.allowUserRemoval");
        if (getService() != null) {
            try {
                enforceMultiUserSupport();
                return mService.allowUserRemoval(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return false;
    }

    public final int createUser(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "createUser");
        EnterpriseLicenseManager.log(this.mContextInfo, "MultiUserManager.createUser");
        if (getService() != null) {
            try {
                enforceMultiUserSupport();
                return mService.createUser(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return -1;
    }

    public final void enforceMultiUserSupport() {
        if (!getMuSupportInfo()) {
            throw new UnsupportedOperationException("This device does not support multiple users");
        }
    }

    public final synchronized boolean getMuSupportInfo() {
        if (!isMuSupportInfoReady && getService() != null) {
            try {
                isMuSupported = mService.multipleUsersSupported(this.mContextInfo);
                isMuSupportInfoReady = true;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return isMuSupported;
    }

    public final int[] getUsers() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getUsers");
        EnterpriseLicenseManager.log(this.mContextInfo, "MultiUserManager.getUsers");
        if (getService() != null) {
            try {
                enforceMultiUserSupport();
                return mService.getUsers(this.mContextInfo);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return null;
    }

    public final boolean isUserCreationAllowed() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isUserCreationAllowed");
        return isUserCreationAllowed(false);
    }

    public final boolean isUserRemovalAllowed() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isUserRemovalAllowed");
        return isUserRemovalAllowed(false);
    }

    public final boolean multipleUsersAllowed() {
        if (UserManager.supportsMultipleUsers()) {
            return multipleUsersAllowed(false);
        }
        throw new UnsupportedOperationException("Not Supported in this device");
    }

    public final boolean multipleUsersSupported() {
        if (getService() == null) {
            return false;
        }
        try {
            return mService.multipleUsersSupported(this.mContextInfo);
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed talking with multi user service"), TAG);
            return false;
        }
    }

    public final boolean removeUser(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removeUser");
        EnterpriseLicenseManager.log(this.mContextInfo, "MultiUserManager.removeUser");
        if (getService() != null) {
            try {
                enforceMultiUserSupport();
                return mService.removeUser(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return false;
    }

    public final boolean isUserCreationAllowed(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isUserCreationAllowed");
        if (getService() != null) {
            try {
                return mService.isUserCreationAllowed(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return true;
    }

    public final boolean isUserRemovalAllowed(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isUserRemovalAllowed");
        if (getService() != null) {
            try {
                return mService.isUserRemovalAllowed(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed talking with multi user service. " + e.getMessage());
            }
        }
        return true;
    }

    public final boolean multipleUsersAllowed(boolean z) {
        if (getService() != null) {
            try {
                return mService.multipleUsersAllowed(this.mContextInfo, z) == 1;
            } catch (RemoteException e) {
                MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed talking with multi user service"), TAG);
            }
        }
        return true;
    }

    public static MultiUserManager getInstance(ContextInfo contextInfo, Context context) {
        MultiUserManager multiUserManager;
        synchronized (mSync) {
            if (gMultiUserMgrInst == null) {
                gMultiUserMgrInst = new MultiUserManager(contextInfo, context);
            }
            multiUserManager = gMultiUserMgrInst;
        }
        return multiUserManager;
    }
}
