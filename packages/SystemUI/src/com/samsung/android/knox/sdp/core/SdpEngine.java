package com.samsung.android.knox.sdp.core;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* loaded from: classes4.dex */
public class SdpEngine {
    private static final String CLASS_NAME = "SdpEngine";
    private static final String TAG = "SdpEngine";
    private static SdpEngine _instance;
    private IDarManagerService mService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
    private final ContextInfo mContextInfo = new ContextInfo(Binder.getCallingUid());

    private SdpEngine() {
    }

    private static void enforcePermission() throws SdpException {
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (iDarManagerServiceAsInterface != null) {
            try {
                if (iDarManagerServiceAsInterface.isLicensed() == 0) {
                } else {
                    throw new SdpException(-9);
                }
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to talk with sdp service...", e);
            }
        }
    }

    public static SdpEngine getInstance() throws SdpException {
        enforcePermission();
        if (_instance == null) {
            _instance = new SdpEngine();
        }
        return _instance;
    }

    public void addEngine(SdpCreationParam sdpCreationParam, String str, String str2) throws SdpException {
        int iAddEngine;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.addEngine");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iAddEngine = iDarManagerService.addEngine(sdpCreationParam, str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call addEngine", e);
            }
        } else {
            iAddEngine = -13;
        }
        if (iAddEngine == 0) {
            return;
        }
        Log.e("SdpEngine", "addEngine failed " + iAddEngine);
        throw new SdpException(iAddEngine);
    }

    public void allow(String str, String str2) throws SdpException {
        int iAllow;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iAllow = iDarManagerService.allow(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call allow", e);
            }
        } else {
            iAllow = -13;
        }
        if (iAllow == 0) {
            return;
        }
        Log.e("SdpEngine", "allow failed " + iAllow);
        throw new SdpException(iAllow);
    }

    public void disallow(String str, String str2) throws SdpException {
        int iDisallow;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iDisallow = iDarManagerService.disallow(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call disallow", e);
            }
        } else {
            iDisallow = -13;
        }
        if (iDisallow == 0) {
            return;
        }
        Log.e("SdpEngine", "disallow failed " + iDisallow);
        throw new SdpException(iDisallow);
    }

    public boolean exists(String str) {
        int iExists;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iExists = iDarManagerService.exists(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call exists", e);
            }
        } else {
            iExists = -5;
        }
        return iExists == -4;
    }

    public void lock(String str) throws SdpException {
        int iLock;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.lock");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iLock = iDarManagerService.lock(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call lock", e);
            }
        } else {
            iLock = -13;
        }
        if (iLock == 0) {
            return;
        }
        Log.e("SdpEngine", "lock failed " + iLock);
        throw new SdpException(iLock);
    }

    public void migrate(String str) throws SdpException {
        int iMigrate;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iMigrate = iDarManagerService.migrate(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call migrate", e);
            }
        } else {
            iMigrate = -13;
        }
        if (iMigrate == 0) {
            return;
        }
        Log.e("SdpEngine", "migrate failed " + iMigrate);
        throw new SdpException(iMigrate);
    }

    public void removeEngine(String str) throws SdpException {
        int iRemoveEngine;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.removeEngine");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iRemoveEngine = iDarManagerService.removeEngine(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call removeEngine", e);
            }
        } else {
            iRemoveEngine = -13;
        }
        if (iRemoveEngine == 0) {
            return;
        }
        Log.e("SdpEngine", "removeEngine failed " + iRemoveEngine);
        throw new SdpException(iRemoveEngine);
    }

    public void resetPassword(String str, String str2, String str3) throws SdpException {
        int iResetPassword;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.resetPassword");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iResetPassword = iDarManagerService.resetPassword(str, str2, str3);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call resetPassword", e);
            }
        } else {
            iResetPassword = -13;
        }
        if (iResetPassword == 0) {
            return;
        }
        Log.e("SdpEngine", "resetPassword failed " + iResetPassword);
        throw new SdpException(iResetPassword);
    }

    public void setPassword(String str, String str2) throws SdpException {
        int password;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.setPassword");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                password = iDarManagerService.setPassword(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call setPassword", e);
            }
        } else {
            password = -13;
        }
        if (password == 0) {
            return;
        }
        Log.e("SdpEngine", "setPassword failed " + password);
        throw new SdpException(password);
    }

    public void unlock(String str, String str2) throws SdpException {
        int iUnlock;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.unlock");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iUnlock = iDarManagerService.unlock(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call unlock", e);
            }
        } else {
            iUnlock = -13;
        }
        if (iUnlock != 0) {
            ClockEventController$$ExternalSyntheticOutline0.m(iUnlock, "unlock failed ", "SdpEngine");
            if (iUnlock <= 0) {
                throw new SdpException(iUnlock);
            }
            throw new SdpException(-8, iUnlock);
        }
    }
}
