package com.samsung.android.knox.sdp.core;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SdpEngine {
    private static final String CLASS_NAME = "SdpEngine";
    private static final String TAG = "SdpEngine";
    private static SdpEngine _instance;
    private IDarManagerService mService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
    private final ContextInfo mContextInfo = new ContextInfo(Binder.getCallingUid());

    private SdpEngine() {
    }

    private static void enforcePermission() {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                if (asInterface.isLicensed() == 0) {
                } else {
                    throw new SdpException(-9);
                }
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to talk with sdp service...", e);
            }
        }
    }

    public static SdpEngine getInstance() {
        enforcePermission();
        if (_instance == null) {
            _instance = new SdpEngine();
        }
        return _instance;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addEngine(SdpCreationParam sdpCreationParam, String str, String str2) {
        int addEngine;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.addEngine");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                addEngine = iDarManagerService.addEngine(sdpCreationParam, str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call addEngine", e);
            }
            if (addEngine != 0) {
                return;
            }
            Log.e("SdpEngine", "addEngine failed " + addEngine);
            throw new SdpException(addEngine);
        }
        addEngine = -13;
        if (addEngine != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void allow(String str, String str2) {
        int allow;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                allow = iDarManagerService.allow(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call allow", e);
            }
            if (allow != 0) {
                return;
            }
            Log.e("SdpEngine", "allow failed " + allow);
            throw new SdpException(allow);
        }
        allow = -13;
        if (allow != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void disallow(String str, String str2) {
        int disallow;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                disallow = iDarManagerService.disallow(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call disallow", e);
            }
            if (disallow != 0) {
                return;
            }
            Log.e("SdpEngine", "disallow failed " + disallow);
            throw new SdpException(disallow);
        }
        disallow = -13;
        if (disallow != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0015 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean exists(String str) {
        int exists;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                exists = iDarManagerService.exists(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call exists", e);
            }
            return exists != -4;
        }
        exists = -5;
        if (exists != -4) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lock(String str) {
        int lock;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.lock");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                lock = iDarManagerService.lock(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call lock", e);
            }
            if (lock != 0) {
                return;
            }
            Log.e("SdpEngine", "lock failed " + lock);
            throw new SdpException(lock);
        }
        lock = -13;
        if (lock != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void migrate(String str) {
        int migrate;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                migrate = iDarManagerService.migrate(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call migrate", e);
            }
            if (migrate != 0) {
                return;
            }
            Log.e("SdpEngine", "migrate failed " + migrate);
            throw new SdpException(migrate);
        }
        migrate = -13;
        if (migrate != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void removeEngine(String str) {
        int removeEngine;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.removeEngine");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                removeEngine = iDarManagerService.removeEngine(str);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call removeEngine", e);
            }
            if (removeEngine != 0) {
                return;
            }
            Log.e("SdpEngine", "removeEngine failed " + removeEngine);
            throw new SdpException(removeEngine);
        }
        removeEngine = -13;
        if (removeEngine != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void resetPassword(String str, String str2, String str3) {
        int resetPassword;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.resetPassword");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                resetPassword = iDarManagerService.resetPassword(str, str2, str3);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call resetPassword", e);
            }
            if (resetPassword != 0) {
                return;
            }
            Log.e("SdpEngine", "resetPassword failed " + resetPassword);
            throw new SdpException(resetPassword);
        }
        resetPassword = -13;
        if (resetPassword != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setPassword(String str, String str2) {
        int password;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.setPassword");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                password = iDarManagerService.setPassword(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call setPassword", e);
            }
            if (password != 0) {
                return;
            }
            Log.e("SdpEngine", "setPassword failed " + password);
            throw new SdpException(password);
        }
        password = -13;
        if (password != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void unlock(String str, String str2) {
        int unlock;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpEngine.unlock");
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                unlock = iDarManagerService.unlock(str, str2);
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to call unlock", e);
            }
            if (unlock == 0) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("unlock failed ", unlock, "SdpEngine");
                if (unlock <= 0) {
                    throw new SdpException(unlock);
                }
                throw new SdpException(-8, unlock);
            }
            return;
        }
        unlock = -13;
        if (unlock == 0) {
        }
    }
}
