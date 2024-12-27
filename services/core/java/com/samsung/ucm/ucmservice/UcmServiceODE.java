package com.samsung.ucm.ucmservice;

import android.os.SystemProperties;
import android.util.Log;

import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UcmServiceODE {
    public static int sOdeStatus = -1;

    public static int getOdeStatus() {
        if (sOdeStatus == -1) {
            updateOdeStatus();
        }
        return sOdeStatus;
    }

    public static boolean isUCMODEEnabledWithPropFile() {
        if ((getOdeStatus() == 2 || UcmServiceUtil.readIntFromFile("/efs/sec_efs/ucm_wpc_dar") == 1)
                && EFSProperties.loadODEConfig().enabledUCSInODE != 0) {
            Log.d("UcmServiceODE", "UCM ODE is enabled.");
            return true;
        }
        Log.d("UcmServiceODE", "UCM ODE is not enabled");
        return false;
    }

    public static void updateOdeStatus() {
        sOdeStatus = UcmServiceUtil.readIntFromFile("/efs/sec_efs/ucm_ode_mode");
        AudioService$$ExternalSyntheticOutline0.m(
                new StringBuilder("updateOdeStatus ["), sOdeStatus, "]", "UcmServiceODE");
        SystemProperties.set("persist.security.ucm_fbe_mode", Integer.toString(sOdeStatus));
    }
}
