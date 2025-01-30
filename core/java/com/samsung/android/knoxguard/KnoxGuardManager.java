package com.samsung.android.knoxguard;

import android.content.ComponentName;
import android.p009os.Bundle;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.util.Log;
import com.samsung.android.knoxguard.IKnoxGuardManager;
import java.util.List;

/* loaded from: classes5.dex */
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

    public void callKGsv() {
        if (getService() != null) {
            try {
                this.mService.callKGsv();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
    }

    public void registerIntent(String preFix, List<String> actionList) {
        if (getService() != null) {
            try {
                this.mService.registerIntent(preFix, actionList);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
    }

    public boolean setAdminRemovable(boolean removable) {
        if (getService() != null) {
            try {
                return this.mService.setAdminRemovable(removable);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean allowFirmwareRecovery(boolean allow) {
        if (getService() != null) {
            try {
                return this.mService.allowFirmwareRecovery(allow);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean allowOTAUpgrade(boolean allow) {
        if (getService() != null) {
            try {
                return this.mService.allowOTAUpgrade(allow);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean allowSafeMode(boolean allow) {
        if (getService() != null) {
            try {
                return this.mService.allowSafeMode(allow);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean addPackagesToForceStopBlockList(List<String> packageList) {
        if (getService() != null) {
            try {
                return this.mService.addPackagesToForceStopBlockList(packageList);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean addPackagesToClearCacheBlockList(List<String> packageList) {
        if (getService() != null) {
            try {
                return this.mService.addPackagesToClearCacheBlockList(packageList);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean setApplicationUninstallationDisabled(String packageName) {
        if (getService() != null) {
            try {
                return this.mService.setApplicationUninstallationDisabled(packageName);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
                return false;
            }
        }
        return false;
    }

    public void setAirplaneMode(boolean enabled) {
        if (getService() != null) {
            try {
                this.mService.setAirplaneMode(enabled);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
    }

    public void setActiveAdmin(ComponentName adminReceiver) {
        if (getService() != null) {
            try {
                this.mService.setActiveAdmin(adminReceiver);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public void removeActiveAdmin(ComponentName adminReceiver) {
        if (getService() != null) {
            try {
                this.mService.removeActiveAdmin(adminReceiver);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public void setRuntimePermission(String packageName, String permission, UserHandle userhandle) {
        if (getService() != null) {
            try {
                this.mService.setRuntimePermission(packageName, permission, userhandle);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public void revokeRuntimePermission(String packageName, String permission, UserHandle userhandle) {
        if (getService() != null) {
            try {
                this.mService.revokeRuntimePermission(packageName, permission, userhandle);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public void setRemoteLockToLockscreen(int type, boolean state, String msg, String number, String email, boolean emergencycallbutton, String name, int failcount, long timeout, int blockcount, boolean skippin, Bundle bundle) {
        if (getService() != null) {
            try {
                this.mService.setRemoteLockToLockscreen(type, state, msg, number, email, emergencycallbutton, name, failcount, timeout, blockcount, skippin, bundle);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public void setRemoteLockToLockscreen(int type, boolean state, String msg, String number, String email, boolean emergencycallbutton, String name, int failcount, long timeout, int blockcount, boolean skippin, Bundle bundle, boolean skipSupportContainer) {
        if (getService() != null) {
            try {
                this.mService.setRemoteLockToLockscreenWithSkipSupport(type, state, msg, number, email, emergencycallbutton, name, failcount, timeout, blockcount, skippin, bundle, skipSupportContainer);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public boolean isSkipSupportContainerSupported() {
        if (getService() != null) {
            try {
                return this.mService.isSkipSupportContainerSupported();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return false;
            }
        }
        return false;
    }

    public String getPBAUniqueNumber() {
        if (getService() == null) {
            return "";
        }
        try {
            return this.mService.getPBAUniqueNumber();
        } catch (RemoteException e) {
            Log.m103w(TAG, "Failed talking with Knox Guard service", e);
            return "";
        }
    }

    public boolean showInstallmentStatus() {
        if (getService() != null) {
            try {
                return this.mService.showInstallmentStatus();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return false;
            }
        }
        return false;
    }

    public boolean shouldBlockCustomRom() {
        if (getService() != null) {
            try {
                return this.mService.shouldBlockCustomRom();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return false;
            }
        }
        return false;
    }

    public void setKnoxGuardExemptRule(boolean add) {
        if (getService() != null) {
            try {
                this.mService.setKnoxGuardExemptRule(add);
                return;
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return;
            }
        }
        Log.m102w(TAG, "No Knox Guard Service found");
    }

    public void bindToLockScreen() {
        if (getService() != null) {
            try {
                this.mService.bindToLockScreen();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public int getKGServiceVersion() {
        if (getService() != null) {
            try {
                return this.mService.getKGServiceVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return 0;
            }
        }
        return 0;
    }

    public void unRegisterIntent() {
        if (getService() != null) {
            try {
                this.mService.unRegisterIntent();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
            }
        }
    }

    public String getNonce(String serverMsg, String serverToken) {
        if (getService() == null) {
            return "";
        }
        try {
            return this.mService.getNonce(serverMsg, serverToken);
        } catch (RemoteException e) {
            Log.m103w(TAG, "Failed talking with Knox Guard service", e);
            return "";
        }
    }

    public int verifyHOTPsecret(String s) {
        if (getService() != null) {
            try {
                return this.mService.verifyHOTPsecret(s);
            } catch (RemoteException e) {
                Log.m103w(TAG, "failed talking with KnoxGuard KGTA processcommand", e);
                return -1000;
            }
        }
        Log.m102w(TAG, "failed talking with KnoxGuard KGTA, service not exist");
        return -1000;
    }

    public int verifyHOTPDHChallenge(String hub, String sigature, String challenge) {
        if (getService() != null) {
            try {
                return this.mService.verifyHOTPDHChallenge(hub, sigature, challenge);
            } catch (RemoteException e) {
                Log.m103w(TAG, "failed talking with KnoxGuard KGTA processcommand", e);
                return -1000;
            }
        }
        Log.m102w(TAG, "failed talking with KnoxGuard KGTA, service not exist");
        return -1000;
    }

    public int verifyHOTPPin(String pin) {
        if (getService() != null) {
            try {
                return this.mService.verifyHOTPPin(pin);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return -1;
            }
        }
        Log.m102w(TAG, "failed talking with KnoxGuard KGTA, service not exist");
        return -1;
    }

    public int getTAState() {
        return getTAStateSetError(true);
    }

    public int getTAStateSetError(boolean setError) {
        if (getService() != null) {
            try {
                return this.mService.getTAStateSetError(setError);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1000;
            }
        }
        return -1000;
    }

    public String getKGPolicy() {
        if (getService() != null) {
            try {
                return this.mService.getKGPolicy();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public int verifyCompleteToken(String serverResponse) {
        if (getService() == null) {
            return -1000;
        }
        try {
            int res = this.mService.verifyCompleteToken(serverResponse);
            return res;
        } catch (RemoteException e) {
            Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public String generateHotpDHRequest() {
        if (getService() != null) {
            try {
                return this.mService.generateHotpDHRequest();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public String getHotpChallenge() {
        if (getService() != null) {
            try {
                return this.mService.getHotpChallenge();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public String verifyRegistrationInfo(String regInfo, String regInfoSig) {
        if (getService() != null) {
            try {
                return this.mService.verifyRegistrationInfo(regInfo, regInfoSig);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public String verifyPolicy(String policy, String sigature) {
        if (getService() != null) {
            try {
                return this.mService.verifyPolicy(policy, sigature);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public int unlockScreen() {
        if (getService() == null) {
            return -1000;
        }
        try {
            int res = this.mService.unlockScreen();
            return res;
        } catch (RemoteException e) {
            Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public int lockScreen(String actionName, String clientName, String phoneNumber, String emailAddress, String message, boolean skipPin, boolean skipSupport, Bundle bundle) {
        if (getService() == null) {
            return -1000;
        }
        try {
            int res = this.mService.lockScreen(actionName, clientName, phoneNumber, emailAddress, message, skipPin, skipSupport, bundle);
            return res;
        } catch (RemoteException e) {
            Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            return -1000;
        }
    }

    public String getLockAction() {
        if (getService() != null) {
            try {
                return this.mService.getLockAction();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public String getClientData() {
        if (getService() != null) {
            try {
                return this.mService.getClientData();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public int setClientData(String clientData) {
        if (getService() != null) {
            try {
                return this.mService.setClientData(clientData);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1;
            }
        }
        return -1;
    }

    public String getKGID() {
        if (getService() != null) {
            try {
                return this.mService.getKGID();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public int resetRPMB() {
        if (getService() != null) {
            try {
                return this.mService.resetRPMB();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1;
            }
        }
        return -1;
    }

    public int resetRPMB2(String optional) {
        if (getService() != null) {
            try {
                return this.mService.resetRPMB2(optional);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1;
            }
        }
        return -1;
    }

    public int setCheckingState() {
        if (getService() != null) {
            try {
                return this.mService.setCheckingState();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1;
            }
        }
        return -1;
    }

    public String verifyKgRot() {
        if (getService() != null) {
            try {
                return this.mService.verifyKgRot();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return "";
            }
        }
        return "";
    }

    public String getStringSystemProperty(String key, String def) {
        if (getService() != null) {
            try {
                return this.mService.getStringSystemProperty(key, def);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with Knox Guard service", e);
            }
        }
        return def;
    }

    public int getTAError() {
        if (getService() != null) {
            try {
                return this.mService.getTAError();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1;
            }
        }
        return -1;
    }

    public String getTAInfo(int infoFlag) {
        if (getService() != null) {
            try {
                return this.mService.getTAInfo(infoFlag);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return null;
            }
        }
        return null;
    }

    public int provisionCert(String enrollCert, String hotpCert, String policyCert, String blCert) {
        if (getService() != null) {
            try {
                return this.mService.provisionCert(enrollCert, hotpCert, policyCert, blCert);
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
                return -1;
            }
        }
        return -1;
    }

    public boolean isVpnExceptionRequired() {
        if (getService() != null) {
            try {
                return this.mService.isVpnExceptionRequired();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return false;
            }
        }
        return false;
    }

    public void setClientHealthOK() {
        if (getService() != null) {
            try {
                this.mService.setClientHealthOK();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard KGTA processCommand", e);
            }
        }
    }

    public Bundle getKGServiceInfo() {
        if (getService() != null) {
            try {
                return this.mService.getKGServiceInfo();
            } catch (RemoteException e) {
                Log.m103w(TAG, "Failed talking with KnoxGuard service", e);
                return null;
            }
        }
        return null;
    }
}
