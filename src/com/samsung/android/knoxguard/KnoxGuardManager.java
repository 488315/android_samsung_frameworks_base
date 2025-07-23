package com.samsung.android.knoxguard;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knoxguard.IKnoxGuardManager;
import java.util.List;

/* loaded from: classes6.dex */
public class KnoxGuardManager {
    private static final String KNOXGUARD_SERVICE = "knoxguard_service";
    private static String TAG = "KnoxGuardManager";
    private static KnoxGuardManager mKnoxGuardManager;
    private IKnoxGuardManager mService;

    private KnoxGuardManager() {
    }

    public static synchronized KnoxGuardManager getInstance() {
        KnoxGuardManager knoxGuardManager;
        synchronized (KnoxGuardManager.class) {
            if (mKnoxGuardManager == null) {
                mKnoxGuardManager = new KnoxGuardManager();
            }
            knoxGuardManager = mKnoxGuardManager;
        }
        return knoxGuardManager;
    }

    private IKnoxGuardManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxGuardManager.Stub.asInterface(ServiceManager.getService(KNOXGUARD_SERVICE));
        }
        return this.mService;
    }

    public void registerIntent(String str, List<String> list) {
        if (getService() != null) {
            try {
                this.mService.registerIntent(str, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
    }

    public void setAirplaneMode(boolean z) {
        if (getService() != null) {
            try {
                this.mService.setAirplaneMode(z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
    }

    public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) {
        if (getService() != null) {
            try {
                this.mService.setRemoteLockToLockscreen(i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) {
        if (getService() != null) {
            try {
                this.mService.setRemoteLockToLockscreenWithSkipSupport(i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, z4);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public boolean isSkipSupportContainerSupported() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isSkipSupportContainerSupported();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return false;
        }
    }

    public String getPBAUniqueNumber() {
        if (getService() != null) {
            try {
                return this.mService.getPBAUniqueNumber();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
        return "";
    }

    public boolean showInstallmentStatus() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.showInstallmentStatus();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return false;
        }
    }

    public boolean shouldBlockCustomRom() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.shouldBlockCustomRom();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return false;
        }
    }

    public void bindToLockScreen() {
        if (getService() != null) {
            try {
                this.mService.bindToLockScreen();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public int getKGServiceVersion() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getKGServiceVersion();
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return 0;
        }
    }

    public void unRegisterIntent() {
        if (getService() != null) {
            try {
                this.mService.unRegisterIntent();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public String getNonce(String str, String str2) {
        if (getService() != null) {
            try {
                return this.mService.getNonce(str, str2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
        return "";
    }

    public int verifyHOTPDHChallenge(String str, String str2, String str3) {
        if (getService() != null) {
            try {
                return this.mService.verifyHOTPDHChallenge(str, str2, str3);
            } catch (RemoteException e) {
                Log.w(TAG, "failed talking with KnoxGuard KGTA processcommand", e);
                return -1000;
            }
        }
        Log.w(TAG, "failed talking with KnoxGuard KGTA, service not exist");
        return -1000;
    }

    public int verifyHOTPPin(String str) {
        if (getService() != null) {
            try {
                return this.mService.verifyHOTPPin(str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxGuard service", e);
                return -1;
            }
        }
        Log.w(TAG, "failed talking with KnoxGuard KGTA, service not exist");
        return -1;
    }

    public int getTAState() {
        return getTAStateSetError(true);
    }

    public int getTAStateSetError(boolean z) {
        if (getService() == null) {
            return -1000;
        }
        try {
            return this.mService.getTAStateSetError(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public String getKGPolicy() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getKGPolicy();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public int verifyCompleteToken(String str) {
        if (getService() == null) {
            return -1000;
        }
        try {
            return this.mService.verifyCompleteToken(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public String generateHotpDHRequest() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.generateHotpDHRequest();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public String getHotpChallenge() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getHotpChallenge();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public String verifyRegistrationInfo(String str, String str2) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.verifyRegistrationInfo(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public String getKGPolicyCompany() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getKGPolicyCompany();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public String verifyPolicy(String str, String str2) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.verifyPolicy(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public int unlockScreen() {
        if (getService() == null) {
            return -1000;
        }
        try {
            return this.mService.unlockScreen();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) {
        if (getService() == null) {
            return -1000;
        }
        try {
            return this.mService.lockScreen(str, str2, str3, str4, str5, z, z2, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public String getLockAction() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getLockAction();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public String getClientData() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getClientData();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public int setClientData(String str) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setClientData(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1;
        }
    }

    public String getKGID() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getKGID();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public int resetRPMB() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.resetRPMB();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1;
        }
    }

    public int setCheckingState() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setCheckingState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1;
        }
    }

    public String verifyKgRot() {
        if (getService() != null) {
            try {
                return this.mService.verifyKgRot();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return "";
            }
        }
        return "";
    }

    public String getStringSystemProperty(String str, String str2) {
        if (getService() != null) {
            try {
                return this.mService.getStringSystemProperty(str, str2);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
        return str2;
    }

    public int getTAError() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getTAError();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1;
        }
    }

    public String getTAInfo(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getTAInfo(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return null;
        }
    }

    public int provisionCert(String str, String str2, String str3, String str4) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.provisionCert(str, str2, str3, str4);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1;
        }
    }

    public boolean isVpnExceptionRequired() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isVpnExceptionRequired();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return false;
        }
    }

    public Bundle getKGServiceInfo() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getKGServiceInfo();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return null;
        }
    }

    public int verifySfPolicy(String str, String str2) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.verifySfPolicy(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return -1;
        }
    }

    public String getSfPolicy() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSfPolicy();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return null;
        }
    }

    public boolean isKGAllowDO() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isKGAllowDO();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return true;
        }
    }

    public boolean isKGAllowADB() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isKGAllowADB();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxGuard service", e);
            return true;
        }
    }

    public boolean isKnoxGuardPackage(String str) {
        return "com.samsung.android.kgclient".equals(str);
    }
}
