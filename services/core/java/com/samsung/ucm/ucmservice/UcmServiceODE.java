package com.samsung.ucm.ucmservice;

import android.os.SystemProperties;
import android.util.Log;
import com.samsung.ucm.ucmservice.EFSProperties;

/* loaded from: classes2.dex */
public abstract class UcmServiceODE {
    public static int sOdeStatus = -1;

    public static void updateOdeStatus() {
        sOdeStatus = UcmServiceUtil.readIntFromFile("/efs/sec_efs/ucm_ode_mode");
        Log.d("UcmServiceODE", "updateOdeStatus [" + sOdeStatus + "]");
        SystemProperties.set("persist.security.ucm_fbe_mode", Integer.toString(sOdeStatus));
    }

    public static int getOdeStatus() {
        if (sOdeStatus == -1) {
            updateOdeStatus();
        }
        return sOdeStatus;
    }

    public static boolean isUcmOdeEnabled() {
        return getOdeStatus() == 2;
    }

    public static boolean isUCMODEEnabledWithPropFile() {
        EFSProperties.ODEProperties loadODEConfig;
        if (getOdeStatus() == 2 && (loadODEConfig = EFSProperties.loadODEConfig()) != null && loadODEConfig.enabledUCSInODE != 0) {
            Log.d("UcmServiceODE", "UCM ODE is enabled.");
            return true;
        }
        Log.d("UcmServiceODE", "UCM ODE is not enabled");
        return false;
    }
}
